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

package de.dennisfalk.homeconnect2semp;

import de.dennisfalk.homeconnect2semp.model.homeConnect.HomeAppliance;
import de.dennisfalk.homeconnect2semp.model.semp.SEMPManager;
import de.dennisfalk.homeconnect2semp.repository.HomeApplianceRepository;
import de.dennisfalk.homeconnect2semp.repository.SEMPManagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ServerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    HomeApplianceRepository homeApplianceRepository;

    @Autowired
    SEMPManagerRepository sempManagerRepository;

    @Test
    public void testGetDescriptionXml() throws Exception {

        SEMPManager sempManager = sempManagerRepository.findByName("homeconnect");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";

        this.mockMvc.perform(get("/semp/description.xml")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(xml)))
                .andExpect(content().string(containsString(sempManager.getUuid())));

    }

    @Test
    public void testGetAllSempDevices() throws Exception {

        List<HomeAppliance> homeApplianceList = homeApplianceRepository.findAll();

            for(HomeAppliance homeAppliance : homeApplianceList){
            this.mockMvc.perform(get("/semp/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(content().string(containsString(homeAppliance.getSempID())));
            }
    }

    @Test
    public void testGetIndexPageWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetIndexPageWithAuthentication() throws Exception {
        this.mockMvc.perform(get("/")
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Haushaltsgeräte")));
    }

    @Test
    public void testGetSettingsPageWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/settings")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetSettingsPageWithAuthentication() throws Exception {
        this.mockMvc.perform(get("/settings")
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("settings"))
                .andExpect(content().string(containsString("Einstellungen")))
                .andExpect(model().attributeExists("oAuth2ProviderSaved"))
                .andExpect(model().attributeExists("isAuthorizable"))
                .andExpect(model().attributeExists("isRefreshable"))
                .andExpect(model().attributeExists("expireDate"))
                .andExpect(model().attributeExists("sempManager"));
    }

    @Test
    public void testGetHelpPageWithoutAuthentication() throws Exception {
        this.mockMvc.perform(get("/help")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetHelpPageWithAuthentication() throws Exception {
        this.mockMvc.perform(get("/help")
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hilfe")));
    }

}
