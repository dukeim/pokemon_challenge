package com.jego.pokemon.securityservice.auth;


import lombok.Data;

@Data
public class AccountCredentials {
    private String username;
    private String password;
}
