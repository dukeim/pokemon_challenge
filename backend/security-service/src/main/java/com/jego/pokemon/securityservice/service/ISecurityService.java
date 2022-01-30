package com.jego.pokemon.securityservice.service;

import com.jego.pokemon.securityservice.dto.UserDataDTO;
import com.jego.pokemon.securityservice.exception.UserAlreadyExistException;

public interface ISecurityService {
    public void registerUser(UserDataDTO userDataDTO) throws UserAlreadyExistException;
}
