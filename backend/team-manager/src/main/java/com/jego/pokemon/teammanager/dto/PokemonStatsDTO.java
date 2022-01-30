package com.jego.pokemon.teammanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonStatsDTO {
    private List<StatsDTO> stats;
}
