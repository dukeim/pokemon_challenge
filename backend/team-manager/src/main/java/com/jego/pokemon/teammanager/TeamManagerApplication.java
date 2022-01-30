package com.jego.pokemon.teammanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TeamManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamManagerApplication.class, args);
	}

}
