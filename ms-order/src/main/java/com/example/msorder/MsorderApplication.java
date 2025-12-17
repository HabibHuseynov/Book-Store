package com.example.msorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MsorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsorderApplication.class, args);
    }

}
