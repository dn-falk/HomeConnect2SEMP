/*
 * MIT License
 *
 * Copyright (c) 2021 Dennis Nikolas Falk <mail@dennisfalk.de>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.dennisfalk.homeconnect2semp.controller;

import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeAppliance;
import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeAppliances;
import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeConnect;
import de.dennisfalk.homeconnect2semp.model.homeConnect.OAuth2Provider;
import de.dennisfalk.homeconnect2semp.model.semp.SEMPManager;
import de.dennisfalk.homeconnect2semp.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * Controller for processing Home Connect requests
 */
@Slf4j
@Controller
public class HomeConnectController {

    @Autowired
    private OAuth2ProviderRepository oAuth2ProviderRepository;

    @Autowired
    private HomeConnectRepository homeConnectRepository;

    @Autowired
    private HomeApplianceRepository homeApplianceRepository;

    @Autowired
    private SEMPManagerRepository sempManagerRepository;

    /**
     * Method for showing and selecting new home appliances
     * @param model HomeAppliances - Object with a list of all available Home Appliances
     * @return addHomeAppliances view
     */
    @GetMapping("/homeconnect/addHomeAppliances")
    public String addHomeAppliances(Model model) {

        OAuth2Provider oAuth2Provider = oAuth2ProviderRepository.findByName("homeconnect");
        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");
        HomeAppliances homeAppliances = new HomeAppliances();

        if(oAuth2Provider != null){
            homeAppliances.setHomeAppliances(homeConnect.getAllHomeAppliances().getHomeAppliances());
        }
        model.addAttribute("homeAppliances", homeAppliances);
        return "addHomeAppliances";
    }

    /**
     * Method for saving the settings of a home appliance
     * @param homeAppliance model of the home appliance from the html form
     * @param haId home appliance id from the home appliance
     * @return ModelAndView index page
     */
    @PostMapping("/homeconnect/save/{haId}")
    public ModelAndView saveHomeAppliance(@ModelAttribute HomeAppliance homeAppliance, @PathVariable String haId){

        HomeAppliance homeApplianceFromDatabase = homeApplianceRepository.findByHaId(haId);

        if(homeApplianceFromDatabase != null){
            homeApplianceFromDatabase.setConnectionPowerInWatt(homeAppliance.getConnectionPowerInWatt());
            homeApplianceFromDatabase.setAverageOperatingPowerPerMinuteInWatt(homeAppliance.getAverageOperatingPowerPerMinuteInWatt());
            homeApplianceRepository.save(homeApplianceFromDatabase);
        }

        return new ModelAndView("redirect:/");
    }

    /**
     * Method to add a new home appliance to the system
     * @param haId home appliance id
     * @return ModelAndView index
     */
    @GetMapping("/homeconnect/homeappliance/add/{haId}")
    public ModelAndView addHomeAppliance(@PathVariable String haId) {

        OAuth2Provider oAuth2Provider = oAuth2ProviderRepository.findByName("homeconnect");
        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");
        SEMPManager sempManager = sempManagerRepository.findByName("homeconnect");

        if(oAuth2Provider != null){
            HomeAppliance homeAppliance = homeConnect.getHomeApplianceByHaId(haId);

            //Calculate the SEMP ID from the Home Connect home appliance ID
            String calculatedDeviceID;
            StringBuilder deviceID = new StringBuilder();
            if(homeAppliance.getHaId().length() < 12){
                deviceID = new StringBuilder(homeAppliance.getHaId());
                while(deviceID.length() < 12){
                    deviceID.append("F");
                }
            } else if (homeAppliance.getHaId().length() == 12){
                deviceID.append(homeAppliance.getHaId());
            } else if (homeAppliance.getHaId().length() > 12){
                String substring = homeAppliance.getHaId().substring(homeAppliance.getHaId().length()-12);
                deviceID.append(substring);
            }
            calculatedDeviceID = sempManager.getSempDeviceIDVendor() + "-" + deviceID + "-00";

            homeAppliance.setSempID(calculatedDeviceID);
            homeApplianceRepository.save(homeAppliance);
        }

        return new ModelAndView("redirect:/");
    }

    /**
     * Method for deleting a specific home appliance
     * @param haId home appliance id
     * @return ModelAndView index
     */
    @GetMapping("/homeconnect/homeappliance/delete/{haId}")
    public ModelAndView deleteHomeAppliance(@PathVariable String haId){

        homeApplianceRepository.deleteByHaId(haId);
        return new ModelAndView("redirect:/");
    }

    /**
     * Method for refreshing all home appliance details from the Home Connect api
     * @return ModelAndView index
     */
    @GetMapping("/homeconnect/homeappliance/refresh")
    public ModelAndView refreshHomeAppliance() {

        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");
        homeConnect.refreshHomeAppliances(homeApplianceRepository);

        return new ModelAndView("redirect:/");
    }

    /**
     * Method for SSE (server sent events). To avoid the Home Connect API rate limit, all status updates will be sent
     * over this SSE channel. This reduce the amount of api calls. This method will be automatic executed at system start.
     * If the connection get lost or the bearer token is invalid, the WebClient flux stream tries 3 reconnections.
     * If there is still an error, the WebClient shutdown and recalls this method.
     *
     * Every received event message will then be processed in the computeStatusEvent method.
     */
    @Autowired
    public void consumeServerSentEvent() {

                String accessToken;

                if(oAuth2ProviderRepository.findByName("homeconnect") != null){
                    accessToken = oAuth2ProviderRepository.findByName("homeconnect").getAccessToken();
                } else {
                    accessToken = "";
                }

                WebClient client = WebClient.builder()
                        .baseUrl("https://api.home-connect.com")
                        .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                        .build();

                ParameterizedTypeReference<ServerSentEvent<String>> type
                        = new ParameterizedTypeReference<>() {
                };

                Flux<ServerSentEvent<String>> eventStream = client.get()
                        .uri("/api/homeappliances/events")
                        .retrieve()
                        .bodyToFlux(type)
                        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(60)));

                eventStream.subscribe(
                        content -> computeStatusEvent(content.event() != null ? content.event() : "", content.id(), content.data()),
                        error -> {
                            log.error("Error receiving SSE: " + error.toString());
                            refreshHomeAppliance();
                            consumeServerSentEvent();
                        },
                        () -> {
                            log.info("SSE Completed!!!");
                            refreshHomeAppliance();
                            consumeServerSentEvent();
                        });
    }

    /**
     * Method for processing the event message
     * @param event String with the event topic
     * @param haId String with the home appliance id
     * @param json String with the json status data
     */
    public void computeStatusEvent(String event, String haId, String json) {

        HomeAppliance homeAppliance = homeApplianceRepository.findByHaId(haId);
        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");

        switch (event) {
            case "STATUS":
            case "EVENT":
            case "NOTIFY":

                log.info("SSE Event: " + event + " " + haId + " " + json);
                JSONObject jsonObject = new JSONObject(json);
                JSONArray itemArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < itemArray.length(); i++) {
                    JSONObject itemObject = itemArray.getJSONObject(i);

                    switch (itemObject.get("key").toString()) {
                        case "BSH.Common.Option.StartInRelative":
                            homeAppliance.setBshCommonOptionStartInRelative(Integer.parseInt(itemObject.get("value").toString()));
                            break;
                        case "BSH.Common.Option.RemainingProgramTime":
                            homeAppliance.setBshCommonOptionRemainingProgramTime(Integer.parseInt(itemObject.get("value").toString()));
                            break;
                        case "BSH.Common.Status.RemoteControlActive":
                            homeAppliance.setBshCommonStatusRemoteControlActive(Boolean.parseBoolean(itemObject.get("value").toString()));
                            break;
                        case "BSH.Common.Status.RemoteControlStartAllowed":
                            homeAppliance.setBshCommonStatusRemoteControlStartAllowed(Boolean.parseBoolean(itemObject.get("value").toString()));
                            break;
                        case "BSH.Common.Status.OperationState":
                            homeAppliance.setBshCommonStatusOperationState(itemObject.get("value").toString());
                            if(itemObject.get("value").toString().equals("BSH.Common.EnumType.OperationState.Aborting")
                                    || itemObject.get("value").toString().equals("BSH.Common.EnumType.OperationState.Ready")
                                    || itemObject.get("value").toString().equals("BSH.Common.EnumType.OperationState.Inactive")){
                                homeAppliance.setBshCommonOptionRemainingProgramTime(0);
                            } else if (itemObject.get("value").toString().equals("BSH.Common.EnumType.OperationState.DelayedStart")){
                                homeAppliance.setBshCommonOptionRemainingProgramTime(homeConnect.getHomeApplianceActiveProgrammOptionRemainingProgramTime(haId));
                            }
                            break;
                    }
                    homeAppliance.setEnergymanagerStartable(homeAppliance.isSempRemoteStartable());
                    homeApplianceRepository.save(homeAppliance);
                }

                break;
            case "CONNECTED":
                log.info("SSE Event: " + event + " " + haId + " " + json);
                homeAppliance.setConnected(true);
                homeApplianceRepository.save(homeAppliance);
                break;

            case "DISCONNECTED":
                log.info("SSE Event: " + event + " " + haId + " " + json);
                homeAppliance.setConnected(false);
                homeApplianceRepository.save(homeAppliance);
                break;

            case "KEEP-ALIVE":
                log.info("SSE Event: " + event );
                break;
        }
    }
}

