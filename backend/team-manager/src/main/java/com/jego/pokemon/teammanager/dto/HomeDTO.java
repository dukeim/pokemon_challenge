package com.jego.pokemon.teammanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeDTO {
    private String front_default;
}
