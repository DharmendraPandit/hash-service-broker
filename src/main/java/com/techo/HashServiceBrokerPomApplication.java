package com.techo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
//@SpringBootApplication
public class HashServiceBrokerPomApplication {
	
	@Bean
	public Cloud cloud() {
		return new CloudFactory().getCloud();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HashServiceBrokerPomApplication.class, args);
	}
}
