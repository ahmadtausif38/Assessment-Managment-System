package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class AssessmentConfigServerMsApplication {

	public static void main(String[] args) {
        SpringApplication.run(AssessmentConfigServerMsApplication.class, args);
    }


}
