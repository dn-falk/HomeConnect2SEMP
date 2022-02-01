package de.dennisfalk.homeconnect2semp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class
 */
@SpringBootApplication
@EnableScheduling
public class HomeConnect2SEMPApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeConnect2SEMPApplication.class, args);
        }


}
