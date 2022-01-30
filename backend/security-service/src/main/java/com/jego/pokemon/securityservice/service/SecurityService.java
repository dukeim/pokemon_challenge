package com.jego.pokemon.securityservice.service;

import com.jego.pokemon.securityservice.dto.UserDataDTO;
import com.jego.pokemon.securityservice.entity.Authority;
import com.jego.pokemon.securityservice.entity.User;
import com.jego.pokemon.securityservice.exception.UserAlreadyExistException;
import com.jego.pokemon.securityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SecurityService implements  ISecurityService{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void registerUser(UserDataDTO userDataDTO) throws UserAlreadyExistException{
        User user = userRepository
                .findByUsernameOrEmail(userDataDTO.getUsername(), userDataDTO.getEmail());
        if(user != null){
            throw new UserAlreadyExistException("Username or email already exists");
        }

        Authority authority = new Authority();
        authority.setUsername(userDataDTO.getUsername());
        authority.setRole("ROLE_USER");

        User userDB = new User();
        userDB.setUsername(userDataDTO.getUsername());
        userDB.setEmail(userDataDTO.getEmail());
        userDB.setPassword(passwordEncoder.encode(userDataDTO.getPassword()));
        userDB.setEnabled(true);
        userDB.setAuthorities(List.of(authority));
        userDB.setCreateAt(new Date());
        userDB.setStatus("CREATED");
        userRepository.save(userDB);
    }
}
