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
import de.dennisfalk.homeconnect2semp.model.semp.sempMessage.Device2EM;
import de.dennisfalk.homeconnect2semp.model.semp.sempMessage.EM2Device;
import de.dennisfalk.homeconnect2semp.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for processing semp requests
 */
@Slf4j
@RestController
public class SEMPManagerController {

    @Autowired
    private HomeApplianceRepository homeApplianceRepository;

    @Autowired
    private HomeConnectRepository homeConnectRepository;

    @Autowired
    private SEMPManagerRepository sempManagerRepository;

    @Autowired
    ResourceLoader resourceLoader;

    /**
     * Scheduled method to check if the home appliance is energymanager (semp) controllable
     */
    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void scheduledCheckIfHomeApplianceIsSEMPControllable() {

        List<HomeAppliance> homeApplianceList = homeApplianceRepository.findAll();

        if(homeApplianceList != null) {
            for (HomeAppliance homeAppliance : homeApplianceList) {
                homeAppliance.setEnergymanagerStartable(homeAppliance.isSempRemoteStartable());
                homeApplianceRepository.save(homeAppliance);
            }
        }
    }

    /**
     * Method for generating the description.xml of this gateway.
     * The energymanager requests this xml
     * @return String xml with data of this semp gateway
     */
    @GetMapping(value = "/semp/description.xml", produces = "application/xml")
    public String getSEMPDescription() {

        //Takes the xml template and generates an individual xml
        String xml =  sempManagerRepository.findByName("homeconnect")
                .refreshDescriptionSEMPxmlFile(resourceLoader.getResource("classpath:data/description.xml"));
        log.info("SEMP: description.xml angefordert");
        return xml;
    }

    /**
     * Method for generating a xml with all home appliances for the energymanager
     * @return XML generated from object Device2EM
     */
    @GetMapping(value = "/semp/", produces = "application/xml")
    public Device2EM getAllSEMPDevices() {

        Device2EM device2EM = sempManagerRepository.findByName("homeconnect")
                .generateSEMPdeviceInfo(homeApplianceRepository.findAll());
        log.info("SEMP: Alle Geräte angefragt");
        return device2EM;
    }

    /**
     * Method for processing energymanager messages
     * @param em2Device XML with device information
     */
    @PostMapping(value = "/semp/", consumes = "application/xml")
    public void postEMmessage(@RequestBody EM2Device em2Device) {

        sempManagerRepository.findByName("homeconnect").computeEMmessage(em2Device,homeConnectRepository.findByName("homeconnect"),homeApplianceRepository);
        log.info("SEMP: Energiemanager Nachricht empfangen. HaID: " + em2Device.getDeviceControl().get(0).getDeviceId() + " Einschalten: " + em2Device.getDeviceControl().get(0).isOn());
        }
}
