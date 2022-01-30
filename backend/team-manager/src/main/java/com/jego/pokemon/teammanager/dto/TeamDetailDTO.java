package com.jego.pokemon.teammanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDetailDTO {
    private Long trainerId;
    private List<PokemonResumeDTO> pokemons = new ArrayList<>();
}
