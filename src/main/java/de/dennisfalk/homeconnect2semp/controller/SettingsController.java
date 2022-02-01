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


import de.dennisfalk.homeconnect2semp.model.User;
import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeConnect;
import de.dennisfalk.homeconnect2semp.model.homeConnect.OAuth2Provider;
import de.dennisfalk.homeconnect2semp.model.semp.SEMPManager;
import de.dennisfalk.homeconnect2semp.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;


/**
 * Controller for settings and oAuth2 authentication
 */
@Slf4j
@Controller
public class SettingsController {

    @Autowired
    private OAuth2ProviderRepository oAuth2ProviderRepository;

    @Autowired
    private HomeApplianceRepository homeApplianceRepository;

    @Autowired
    private HomeConnectRepository homeConnectRepository;

    @Autowired
    private SEMPManagerRepository sempManagerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Method for calling the home connect authorization endpoint
     * @return RedirectView to the Home Connect authorization page
     */
    @GetMapping("/oauth2/authorize")
    public RedirectView authorize() {

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");

        String authorizeURL = oauth.getAuthorizationURL() +
                "?client_id=" +
                oauth.getClientId() +
                "&response_type=" +
                oauth.getResponseType() +
                "&state=" +
                oauth.getState();
        return new RedirectView(authorizeURL);
    }

    /**
     * Method for processing the authorization answer from the home connect api
     * @param code authorization code
     * @param grantType all granted access points
     * @param state individual code string for security purpose
     * @return ModelAndView to token method
     */
    @GetMapping("/oauth2/code")
    public ModelAndView code(@RequestParam("code") String code,
                       @RequestParam(value = "grant_type", required = false) String grantType,
                       @RequestParam(value = "state", required = false) String state) {

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");

        if(oauth.getState().equals(state)) {
            oauth.setCode(code);
            oauth.setGrantType(grantType);
            oAuth2ProviderRepository.save(oauth);
            log.info("Oauth2-Code zurück gekommen und gespeichert");
        }
        return new ModelAndView("redirect:/oauth2/token");
    }

    /**
     * Method for token request. This method will be called after the authorization answer from the home connect api
     * @return ModelAndView settings
     */
    @GetMapping("/oauth2/token")
    public ModelAndView token() {

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");

        try {
            if (oauth != null) {
                oAuth2ProviderRepository.save(oauth.obtainAccessToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/settings");
    }

    /**
     * Method for refreshing the access token. The access token is 24h valid
     * @return ModelAndView settings
     */
    @GetMapping("/oauth2/refresh")
    public ModelAndView tokenRefresh(){

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");
        try {
            if (oauth != null) {
                oAuth2ProviderRepository.save(oauth.refreshToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/settings");
    }

    /**
     * Method for saving the home connect settings
     * @param oAuth2Provider object with connection settings
     * @return ModelAndView settings
     */
    @PostMapping("/oauth2/save")
    public ModelAndView save(@ModelAttribute OAuth2Provider oAuth2Provider) {

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");
        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");
        SEMPManager sempManager = sempManagerRepository.findByName("homeconnect");

        if(oauth == null){
            oauth = new OAuth2Provider("homeconnect");
        }
        oauth.setClientId(Jsoup.clean(oAuth2Provider.getClientId(),Safelist.none()));
        oauth.setClientSecret(Jsoup.clean(oAuth2Provider.getClientSecret(),Safelist.none()));
        oAuth2ProviderRepository.save(oauth);

        if(homeConnect == null){
            homeConnect = new HomeConnect("homeconnect", oauth);
        }
        homeConnect.setOAuth2Provider(oauth);
        homeConnectRepository.save(homeConnect);

        if(sempManager == null){
            sempManager = new SEMPManager("homeconnect");
        }
        sempManagerRepository.save(sempManager);

        return new ModelAndView("redirect:/settings");
    }

    /**
     * Method for saving the semp settings
     * @param sempManager object with semp settings from html form
     * @return ModelAndView settings
     */
    @PostMapping("/sempmanager/save")
    public ModelAndView save(@ModelAttribute SEMPManager sempManager) {

        SEMPManager sempManagerSaved = sempManagerRepository.findByName("homeconnect");

        if(sempManagerSaved == null){
            sempManagerSaved = new SEMPManager("homeconnect");
        }
        sempManagerSaved.setLocalServerIpAddress(Jsoup.clean(sempManager.getLocalServerIpAddress(),Safelist.none()));
        sempManagerSaved.setActivateSempConnection(sempManager.isActivateSempConnection());

        sempManagerRepository.save(sempManagerSaved);

        return new ModelAndView("redirect:/settings");
    }


    /**
     * Method for deleting all home connect and oAuth2.0 data
     * @return ModelAndView settings
     */
    @GetMapping("/oauth2/delete")
    public ModelAndView deleteOAuth2() {

        homeConnectRepository.deleteAll();
        oAuth2ProviderRepository.deleteAll();

        OAuth2Provider oAuth2Provider = new OAuth2Provider("homeconnect");
        oAuth2ProviderRepository.save(oAuth2Provider);
        homeConnectRepository.save(new HomeConnect("homeconnect",oAuth2Provider));

        homeApplianceRepository.deleteAll();

        log.info("OAuth2 und Home Connect Daten gelöscht");
        return new ModelAndView("redirect:/settings");
    }

    /**
     * Method for deleting all semp data
     * @return ModelAndView settings
     */
    @GetMapping("/sempmanager/delete")
    public ModelAndView deleteSEMPManager() {

      sempManagerRepository.deleteAll();
      sempManagerRepository.save(new SEMPManager("homeconnect"));

      log.info("SEMP Daten gelöscht");
      return new ModelAndView("redirect:/settings");
    }

    /**
     * Method for updating the user password
     * @param newPassword the new password
     * @return ModelAndView settings
     */
    @PostMapping("/user/updatePassword")
    public ModelAndView changeUserPassword(
            @RequestParam("newPassword") String newPassword){
        User user = userRepository.findByName(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null ){
            user.setPasswordHash(bCryptPasswordEncoder.encode(Jsoup.clean(newPassword, Safelist.none())));
            userRepository.save(user);
            SecurityContextHolder.clearContext();
            return new ModelAndView("redirect:/login?logout");
        }
        return new ModelAndView("redirect:/settings");
    }
}
