package com.jego.pokemon.pokemoncatalogue.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerationDTO {
    private PokemonSpeciesDTO[] pokemon_species;

}
