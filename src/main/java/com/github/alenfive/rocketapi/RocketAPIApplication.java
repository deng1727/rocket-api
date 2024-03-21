package com.github.alenfive.rocketapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;

@SpringBootApplication(exclude = MongoDataAutoConfiguration.class)
public class RocketAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketAPIApplication.class, args);
    }
}
