package com.jego.pokemon.teammanager.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PokemonBasicDTO {
    @NotEmpty(message = "The 'Median Salary Value' must not be empty")
    private String name;
}
