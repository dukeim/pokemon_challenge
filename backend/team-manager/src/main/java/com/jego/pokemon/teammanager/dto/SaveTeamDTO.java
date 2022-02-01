package com.jego.pokemon.teammanager.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SaveTeamDTO {
    public List<PokemonBasicDTO> pokemons = new ArrayList<>();;
}
