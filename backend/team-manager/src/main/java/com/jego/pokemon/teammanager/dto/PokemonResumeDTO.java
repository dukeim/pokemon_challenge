package com.jego.pokemon.teammanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResumeDTO {
    private String name;
    private List<StatsDTO> stats;
    private List<TypesDTO> types;
}
