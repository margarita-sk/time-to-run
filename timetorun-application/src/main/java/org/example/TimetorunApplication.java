package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "org.example")
public class TimetorunApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimetorunApplication.class, args);
    }

}