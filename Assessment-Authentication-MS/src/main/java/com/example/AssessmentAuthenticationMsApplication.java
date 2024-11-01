package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableDiscoveryClient
@SpringBootApplication
public class AssessmentAuthenticationMsApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(AssessmentAuthenticationMsApplication.class, args);
		
		
	}
	
	

}
