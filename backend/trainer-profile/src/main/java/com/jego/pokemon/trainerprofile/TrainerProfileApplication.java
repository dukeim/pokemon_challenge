package com.jego.pokemon.trainerprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TrainerProfileApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainerProfileApplication.class, args);
	}

}
