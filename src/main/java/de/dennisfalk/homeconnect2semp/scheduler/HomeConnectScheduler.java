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

package de.dennisfalk.homeconnect2semp.scheduler;

import de.dennisfalk.homeconnect2semp.model.homeConnect.OAuth2Provider;
import de.dennisfalk.homeconnect2semp.repository.OAuth2ProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Scheduler for refreshing the access token
 */
@Slf4j
@Component
public class HomeConnectScheduler {

    @Autowired
    private OAuth2ProviderRepository oAuth2ProviderRepository;


    /**
     * Scheduler for refresh token. Every hour the access token will be refreshed
     * Value: 3600000 = 1h, Access Token 24h valid, Refresh Token 60d valid
     * @throws IOException
     */
    @Scheduled(fixedRate = 3600000)
    public void refreshHomeConnectAccessToken() throws IOException {

        OAuth2Provider oauth = oAuth2ProviderRepository.findByName("homeconnect");

        if(oauth != null && !oauth.isRefreshTokenExpired()){
           oAuth2ProviderRepository.save(oauth.refreshToken());
           log.info("Token Refresh durchgeführt.");
        } else {
            log.error("Token Refresh wurde nicht durchgeführt, da der Refresh Token abgelaufen oder nicht vorhanden ist. Bitte in den " +
                    "Einstellungen die Home Connect API neu autorisieren.");
        }

    }

}
