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

package de.dennisfalk.homeconnect2semp.listener;

import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeConnect;
import de.dennisfalk.homeconnect2semp.model.homeConnect.OAuth2Provider;
import de.dennisfalk.homeconnect2semp.model.semp.SEMPManager;
import de.dennisfalk.homeconnect2semp.model.User;
import de.dennisfalk.homeconnect2semp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Class for listening the application start
 */
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuth2ProviderRepository oAuth2ProviderRepository;

    @Autowired
    private HomeConnectRepository homeConnectRepository;

    @Autowired
    private SEMPManagerRepository sempManagerRepository;

    @Autowired
    private HomeApplianceRepository homeApplianceRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Set standard repository entities if emtpy
     * @param contextRefreshedEvent contex refresh event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        User user = userRepository.findByName("user");
        OAuth2Provider oAuth2 = oAuth2ProviderRepository.findByName("homeconnect");
        HomeConnect homeConnect = homeConnectRepository.findByName("homeconnect");
        SEMPManager sempManager = sempManagerRepository.findByName("homeconnect");

        if(user == null){
            user = new User("user",bCryptPasswordEncoder.encode("password"));
            userRepository.save(user);
        }

        if(oAuth2 == null){
            oAuth2 = new OAuth2Provider("homeconnect");
            oAuth2ProviderRepository.save(oAuth2);
        }

        if(homeConnect == null){
            homeConnect = new HomeConnect("homeconnect", oAuth2);
            homeConnectRepository.save(homeConnect);
        }

        if(sempManager == null){
            sempManager = new SEMPManager("homeconnect");
            sempManagerRepository.save(sempManager);
        }

        if(!oAuth2.isExpired()) {
            homeConnect.refreshHomeAppliances(homeApplianceRepository);
        }
    }
}
