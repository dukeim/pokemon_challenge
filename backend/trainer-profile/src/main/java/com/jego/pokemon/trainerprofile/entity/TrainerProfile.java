package com.jego.pokemon.trainerprofile.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Data
@Entity
@Table(name="tbl_trainer_profile")
public class TrainerProfile implements Serializable {
    @Id
    @Column(name="username")
    private String userName;

    @NotEmpty(message = "The name can't be empty")
    @Column(name="name", nullable=false)
    private String name;

    @Lob
    @Column(name="photo")
    private byte[] photo;

    @Column(name="hobby")
    private String hobby;

    @Column(name="doc_number")
    private String docNumber;

    @NotEmpty(message = "The document type can't be empty")
    @Column(name="doc_type", nullable=false)
    private String docType;

    @NotNull(message = "The birthdate type can't be empty")
    @Column(name="birthdate", nullable=false)
    private LocalDate birthDate;

}
