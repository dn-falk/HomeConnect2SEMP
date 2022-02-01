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

import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeAppliances;
import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeConnect;
import de.dennisfalk.homeconnect2semp.model.homeConnect.OAuth2Provider;
import de.dennisfalk.homeconnect2semp.model.semp.SEMPManager;
import de.dennisfalk.homeconnect2semp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

/**
 * Main controller for processing the standard endpoint requests
 */
@Controller
public class MainController {

    @Autowired
    private OAuth2ProviderRepository oAuth2ProviderRepository;

    @Autowired
    private HomeApplianceRepository homeApplianceRepository;

    @Autowired
    private HomeConnectRepository homeConnectRepository;

    @Autowired
    private SEMPManagerRepository sempManagerRepository;

    /**
     * Method for the index page
     * @param model Model for the html template data
     * @return String with html file name
     */
    @GetMapping("/")
    public String index(Model model) {

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");
        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");

        //Check if Home Connect ist configured and show alert if not
        if(oauth == null || oauth.isExpired() ){
            model.addAttribute("isConfigured", false);

        } else {
            if(!oauth.isExpired()){
                model.addAttribute("isConfigured", true);
            }
        }

        //Load all Home Appliances
        HomeAppliances homeAppliances = new HomeAppliances();
        homeAppliances.setHomeAppliances(homeApplianceRepository.findAll());
        model.addAttribute("homeAppliances",homeAppliances);
        model.addAttribute("homeConnect", Objects.requireNonNullElseGet(homeConnect, () -> new HomeConnect("homeconnect", oauth)));

        return "index";
    }

    /**
     * Method for showing the help page
     * @return String with html file name
     */
    @GetMapping("/help")
    public String helpPage() {
        return "help";
    }

    /**
     * Method for showing the settings page
     * @param model Model for the html template data
     * @return String with html file name
     */
    @GetMapping("/settings")
    public String settingsPage(Model model) {

        model.addAttribute("oAuth2Provider", new OAuth2Provider("homeconnect"));

        OAuth2Provider oauth2 = oAuth2ProviderRepository.findByName("homeconnect");
        SEMPManager sempManager = sempManagerRepository.findByName("homeconnect");

        if(oauth2 != null) {
            model.addAttribute("oAuth2ProviderSaved", oauth2);
            if(oauth2.isAuthorizable()) {
                model.addAttribute("isAuthorizable", true);
            } else {
                model.addAttribute("isAuthorizable", false);
            }
            model.addAttribute("isExpired", oauth2.isExpired());
            if(oauth2.isExpired()){
                model.addAttribute("expireDate", "");
            } else {
                model.addAttribute("expireDate", oauth2.getExpireDateReadable());
            }
            if(oauth2.isRefreshable()){
                model.addAttribute("isRefreshable", true);
            } else {
                model.addAttribute("isRefreshable", false);
            }

        } else {
            model.addAttribute("oAuth2ProviderSaved", new OAuth2Provider("homeconnect"));
            model.addAttribute("isAuthorizable", false);
            model.addAttribute("isRefreshable", false);
            model.addAttribute("expireDate", "kein Access Token vorhanden");
        }
        model.addAttribute("sempManager", Objects.requireNonNullElseGet(sempManager, () -> new SEMPManager("homeconnect")));
        return "settings";
    }
}