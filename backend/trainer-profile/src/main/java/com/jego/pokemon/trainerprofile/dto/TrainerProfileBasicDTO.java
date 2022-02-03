package com.jego.pokemon.trainerprofile.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainerProfileBasicDTO {
    private String name;
    private String hobby;
    private String docNumber;
    private String docType;
    private LocalDate birthDate;
    private int age;
}
