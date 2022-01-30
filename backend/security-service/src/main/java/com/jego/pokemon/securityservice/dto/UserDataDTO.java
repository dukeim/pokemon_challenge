package com.jego.pokemon.securityservice.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDataDTO {
    @NotEmpty(message = "Username can not be empty")
    private String username;
    @NotEmpty(message = "Password can not be empty")
    private String password;
    @NotEmpty(message = "Email can not be empty")
    private String email;

}
