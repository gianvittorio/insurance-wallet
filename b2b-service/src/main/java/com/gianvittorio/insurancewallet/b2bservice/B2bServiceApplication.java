package com.gianvittorio.insurancewallet.b2bservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class B2bServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(B2bServiceApplication.class, args);
	}

}
