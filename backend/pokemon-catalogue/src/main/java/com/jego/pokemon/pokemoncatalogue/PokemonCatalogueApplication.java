package com.jego.pokemon.pokemoncatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PokemonCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokemonCatalogueApplication.class, args);
	}

}
