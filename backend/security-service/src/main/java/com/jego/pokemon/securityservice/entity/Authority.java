package com.jego.pokemon.securityservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_authorities",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role", "user_id"}))
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private  String role;

    @Column(name = "user_id")
    private String username;
}
