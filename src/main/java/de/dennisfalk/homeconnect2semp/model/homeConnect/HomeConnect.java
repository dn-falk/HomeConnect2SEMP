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

package de.dennisfalk.homeconnect2semp.model.homeConnect;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.dennisfalk.homeconnect2semp.repository.HomeApplianceRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class for all Home Connect tasks
 */
@Getter
@Setter
@ToString
@Slf4j
@Entity
public class HomeConnect {

    /**
     * Empty constructor
     */
    public HomeConnect() {

    }

    /**
     * Constructor with arguments
     * @param name name of the entity
     * @param oAuth2Provider Access provider for the home connect api
     */
    public HomeConnect(String name, OAuth2Provider oAuth2Provider) {
        this.name = name;
        this.apiURL = "https://api.home-connect.com";
        this.pathHomeAppliances = "/api/homeappliances";
        this.oAuth2Provider = oAuth2Provider;
    }

    /*
    Name of this HomeConnect Objekt
     */
    @Id
    @Column(nullable = false, unique = true)
    String name;

    /*
       Home Connect API URl
    */
    @Column(nullable = false)
    String apiURL;

    /*
    Path for getting a list of all home appliances
    default: /api/homeappliances
     */
    @Column(nullable = false)
    String pathHomeAppliances;

    @OneToOne(cascade = {CascadeType.ALL})
    OAuth2Provider oAuth2Provider;

    /**
     * Request all home appliances from the Home Connect API
     *
     * @return HomeAppliances object (Object with a list of all Home Appliances)
     *
     */
    public HomeAppliances getAllHomeAppliances() {

            try {
                String response = apiGetRequest("");
                Gson gson = new Gson();
                JSONObject jsonObject = new JSONObject(response);
                JSONObject homeAppliancesJSON = jsonObject.getJSONObject("data");
                return gson.fromJson(homeAppliancesJSON.toString(), HomeAppliances.class);
            } catch (JSONException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        return null;
    }

    /**
     * Get a specific home appliance
     * @param haId home appliance id
     * @return HomeAppliance object
     */
    public HomeAppliance getHomeApplianceByHaId(String haId){

        try {
            String response = apiGetRequest("/" + haId);
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(response);

            JSONObject homeAppliancesJSON = jsonObject.getJSONObject("data");
            return gson.fromJson(homeAppliancesJSON.toString(), HomeAppliance.class);
        } catch (JSONException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the active programm of a specific hoem appliance
     * @param haId home applinace id
     * @return ArrayList with HashMaps - programm options
     */
    public ArrayList<HashMap<String,String>> getHomeApplianceActiveProgramByHaId(String haId) {

        String response;
        ArrayList<HashMap<String, String>> statusList = null;
        HashMap<String, String> hashmap;

        try {
            response = apiGetRequest("/" + haId + "/programs/active");

            if (response != null) {
                statusList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                JSONArray jsonStatusArray = jsonObjectData.getJSONArray("options");
                for (int i = 0; i < jsonStatusArray.length(); i++) {
                    hashmap = new HashMap<>();
                    JSONObject jsonOb = jsonStatusArray.getJSONObject(i);
                    hashmap.put("key", jsonOb.get("key").toString());
                    hashmap.put("name", jsonOb.get("name").toString());
                    hashmap.put("value", jsonOb.get("value").toString());

                    statusList.add(hashmap);
                }
            }
            return statusList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * API put method
     * @param apiRequestPath request path
     * @param json String with the response
     */
    public void apiPutRequest(String apiRequestPath, String json) {

        try {
            URL urlPut = new URL(this.getApiURL() + this.getPathHomeAppliances() + apiRequestPath);
            HttpURLConnection connection = (HttpURLConnection) urlPut.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Accept", "application/vnd.bsh.sdk.v1+json");
            connection.setRequestProperty("Accept-Language", "de-DE");
            connection.setRequestProperty("Authorization", "Bearer " + oAuth2Provider.getAccessToken());
            connection.setRequestProperty("Content-Type", "application/vnd.bsh.sdk.v1+json");

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(json);
            out.close();

            connection.getResponseCode();
            log.debug("Home Connect Put Request: "+connection.getResponseCode() + " " + connection.getResponseMessage());
            connection.getInputStream();
            connection.disconnect();

        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * API get request
     * @param apiRequestPath String with API Path
     * @return String with JSON response
     *
     */
    public String apiGetRequest(String apiRequestPath) {

    //API Get Request
        try {
            URL url = new URL(this.getApiURL() + this.getPathHomeAppliances() + apiRequestPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Language", "de-DE");
            con.setRequestProperty("Accept", "application/vnd.bsh.sdk.v1+json");
            con.setRequestProperty("Authorization", "Bearer " + oAuth2Provider.getAccessToken());
            log.debug("Home Connect Get Request: " +con.getResponseCode() + " " + con.getResponseMessage() + " " + "Retry after: "+ con.getHeaderField("Retry-After"));

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            con.disconnect();
                return response.toString();
            }

        } catch (IOException e) {
            log.error(e.toString());
        }

        return null;
    }

    /**
     * Get the status of a specific home appliance
     * @param haId home appliance id
     * @return ArrayList with HashMaps
     */
    public ArrayList<HashMap<String,String>> getHomeApplianceStatusAsList(String haId){

        try {
            String response = apiGetRequest("/" + haId + "/status");
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObjectData = jsonObject.getJSONObject("data");
            JSONArray jsonStatusArray = jsonObjectData.getJSONArray("status");
            ArrayList<HashMap<String, String>> statusList = new ArrayList<>();

            for (int i = 0; i < jsonStatusArray.length(); i++) {

                HashMap<String, String> hashmap = new HashMap<>();

                JSONObject jsonOb = jsonStatusArray.getJSONObject(i);
                hashmap.put("key", jsonOb.get("key").toString());
                hashmap.put("name", jsonOb.get("name").toString());
                hashmap.put("value", jsonOb.get("value").toString());
                try {
                    hashmap.put("displayvalue", jsonOb.get("displayvalue").toString());
                } catch (JSONException e) {
                    log.debug("Displayvalue nicht gefunden " + e);
                }
                statusList.add(hashmap);
            }

            return statusList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the remaining programm time of a specific home appliance
     * @param haId home appliance id
     * @return integer with remaining time in seconds
     */
    public int getHomeApplianceActiveProgrammOptionRemainingProgramTime(String haId){

               for (HashMap<String, String> activeProgramOption : this.getHomeApplianceActiveProgramByHaId(haId)) {
                   if (activeProgramOption.get("key").equals("BSH.Common.Option.RemainingProgramTime")) {
                      return Integer.parseInt(activeProgramOption.get("value"));
                    }
                }
               return 0;
    }

    /**
     * Method for refreshing all home appliances
     * @param homeApplianceRepository a repository with all home appliances
     */
    public void refreshHomeAppliances(HomeApplianceRepository homeApplianceRepository){

        HomeAppliance newHomeAppliance;

        for(HomeAppliance homeAppliance : homeApplianceRepository.findAll()){
            newHomeAppliance = this.getHomeApplianceByHaId(homeAppliance.getHaId());
            homeAppliance.setConnected(newHomeAppliance.isConnected());

            for(HashMap<String,String> status : this.getHomeApplianceStatusAsList(homeAppliance.getHaId())){
                switch (status.get("key")) {
                    case "BSH.Common.Status.RemoteControlStartAllowed":
                        homeAppliance.setBshCommonStatusRemoteControlStartAllowed(Boolean.parseBoolean(status.get("value")));
                        break;
                    case "BSH.Common.Status.RemoteControlActive":
                        homeAppliance.setBshCommonStatusRemoteControlActive(Boolean.parseBoolean(status.get("value")));
                        break;
                    case "BSH.Common.Status.OperationState":
                        homeAppliance.setBshCommonStatusOperationState(status.get("value"));
                        break;
                }
            }

            if(homeAppliance.getOperationStateReadable().equals("Run") || homeAppliance.getOperationStateReadable().equals("DelayedStart")) {
                for (HashMap<String, String> activeProgramOption : this.getHomeApplianceActiveProgramByHaId(homeAppliance.getHaId())) {
                    if (activeProgramOption.get("key").equals("BSH.Common.Option.StartInRelative")) {
                        homeAppliance.setBshCommonOptionStartInRelative(Integer.parseInt(activeProgramOption.get("value")));
                    }
                    if (activeProgramOption.get("key").equals("BSH.Common.Option.RemainingProgramTime")) {
                        homeAppliance.setBshCommonOptionRemainingProgramTime(Integer.parseInt(activeProgramOption.get("value")));
                    }
                }
            }else{
                homeAppliance.setBshCommonOptionStartInRelative(0);
                homeAppliance.setBshCommonOptionRemainingProgramTime(0);
            }
            homeApplianceRepository.save(homeAppliance);
        }
    }

    /**
     * Method to turn a specific home appliance on
     * @param haId home appliance id
     */
    public void turnHomeApplianceOn(String haId){

        String json = "{\"data\":{\"key\":\"BSH.Common.Option.StartInRelative\",\"value\":0,\"unit\":\"seconds\"}}";

        String url = "/" + haId + "/programs/active/options/BSH.Common.Option.StartInRelative";

        apiPutRequest(url, json);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HomeConnect that = (HomeConnect) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return 1917002225;
    }
}
