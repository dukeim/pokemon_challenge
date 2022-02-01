package com.jego.pokemon.trainerprofile.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class TrainerProfileNewDTO {
    @NotEmpty(message = "The ame can't be empty")
    private String name;
    private byte[] photo;
    private String hobby;
    private String docNumber;
    private LocalDate birthDate;

}
