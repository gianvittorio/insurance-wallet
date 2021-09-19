package com.gianvittorio.insurancewallet.aggregateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AggregateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggregateServiceApplication.class, args);
	}

}
