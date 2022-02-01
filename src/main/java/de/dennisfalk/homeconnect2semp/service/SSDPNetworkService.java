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

package de.dennisfalk.homeconnect2semp.service;

import de.dennisfalk.homeconnect2semp.model.semp.SEMPManager;
import de.dennisfalk.homeconnect2semp.repository.SEMPManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

/**
 * Class for generating SSDP NOTIFY network messages.
 * The energy manager receives this message and then retrieves the description.xml from the gateway
 */
@Slf4j
@Component
public class SSDPNetworkService {

    @Autowired
    private SEMPManagerRepository sempManagerRepository;

    /**
     * Scheduled method for sending multicast SSDP NOTIFY messages
     */
    @Scheduled(fixedRate = 300000, initialDelay = 20000)
    public void multicast() {

        SEMPManager sempManager = sempManagerRepository.findByName("homeconnect");

        if (sempManager != null && sempManager.isActivateSempConnection()) {

            String rootDeviceMessage = "NOTIFY * HTTP/1.1\r\n" +
                    "HOST: 239.255.255.250:1900\r\n" +
                    "CACHE-CONTROL: max-age = 1800\r\n" +
                    "SERVER: "+ System.getProperty("os.name") +"/"+ System.getProperty("os.version") +" UPnP/1.0 HomeConnect2SEMP Gateway/1.0.0\r\n" +
                    "NTS: ssdp:alive\r\n" +
                    "LOCATION: http://" +sempManager.getLocalServerIpAddress() +":8080/semp/description.xml\r\n" +
                    "NT: upnp:rootdevice\r\n" +
                    "USN: uuid:"+ sempManager.getUuid() +"::upnp:rootdevice\r\n" +
                    "\r\n";
            String deviceUUIDMessage = "NOTIFY * HTTP/1.1\r\n" +
                    "HOST: 239.255.255.250:1900\r\n" +
                    "CACHE-CONTROL: max-age = 1800\r\n" +
                    "SERVER: "+ System.getProperty("os.name") +"/" + System.getProperty("os.version") + " UPnP/1.0 HomeConnect2SEMP Gateway/1.0.0\r\n" +
                    "NTS: ssdp:alive\r\n" +
                    "LOCATION: http://"+sempManager.getLocalServerIpAddress()+":8080/semp/description.xml\r\n" +
                    "NT: uuid:"+sempManager.getUuid()+"\r\n" +
                    "USN: uuid:"+sempManager.getUuid()+"\r\n" +
                    "\r\n";
            String sempGatewayDeviceTypeMessage = "NOTIFY * HTTP/1.1\r\n" +
                    "HOST: 239.255.255.250:1900\r\n" +
                    "CACHE-CONTROL: max-age = 1800\r\n" +
                    "SERVER: "+ System.getProperty("os.name") + "/" + System.getProperty("os.version") + " UPnP/1.0 HomeConnect2SEMP Gateway/1.0.0\r\n" +
                    "NTS: ssdp:alive\r\n" +
                    "LOCATION: http://"+sempManager.getLocalServerIpAddress()+":8080/semp/description.xml\r\n" +
                    "NT: urn:schemas-simple-energy-management-protocol:device:Gateway:1\r\n" +
                    "USN: uuid:"+sempManager.getUuid()+"::urn:schemas-simple-energy-management-protocol:device:Gateway:1\r\n" +
                    "\r\n";

            try {
                InetAddress multicastAddress = InetAddress.getByName("239.255.255.250");
                // multicast address for SSDP
                final int port = 1900; // standard port for SSDP
                MulticastSocket socket = new MulticastSocket(port);
                socket.setReuseAddress(true);
                socket.setSoTimeout(15000);
                socket.setBroadcast(true);
                socket.joinGroup(multicastAddress);

                // send NOTIFY Root Device
                byte[] txbuf = rootDeviceMessage.getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagram = new DatagramPacket(txbuf, txbuf.length, multicastAddress, port);
                socket.send(datagram);
                log.info("SSDP Notify message: root device");

                // send NOTIFY device UUID
                byte[] txbuf2 = deviceUUIDMessage.getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagram2 = new DatagramPacket(txbuf2, txbuf2.length, multicastAddress, port);
                socket.send(datagram2);
                log.info("SSDP Notify message: device uuid");

                // send NOTIFY SEMP gateway device type
                byte[] txbuf3 = sempGatewayDeviceTypeMessage.getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagram3 = new DatagramPacket(txbuf3, txbuf3.length, multicastAddress, port);
                socket.send(datagram3);
                log.info("SSDP Notify message: SEMP gateway device type");

                socket.leaveGroup(multicastAddress);
                socket.close();

            } catch (Exception e) {
                log.warn("SSDP socket timeout");
            }
        }
    }
}
