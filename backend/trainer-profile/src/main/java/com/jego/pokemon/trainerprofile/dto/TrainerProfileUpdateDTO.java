package com.jego.pokemon.trainerprofile.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainerProfileUpdateDTO {
    private String name;
    private byte[] photo;
    private String hobby;
    private String docNumber;
    private LocalDate birthDate;
}
