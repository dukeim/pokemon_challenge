package com.jego.pokemon.teammanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Entity
@Table(name="tbl_team_members")
public class TeamMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotEmpty(message = "The name can't be empty")
    @Column(name="pokemon_name", nullable=false)
    private String pokemonName;

    @NotNull(message = "The name can't be empty")
    @Column(name="trainer_id", nullable=false)
    private Long trainerId;

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

}
