package com.jego.pokemon.teammanager.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "The username can't be empty")
    @Column(name="username", nullable=false)
    private String userName;

    @Column(name="deleted", nullable=false)
    private boolean deleted = false;

}
