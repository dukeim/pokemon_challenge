package com.jego.pokemon.securityservice.service;

import com.jego.pokemon.securityservice.dto.UserDataDTO;
import com.jego.pokemon.securityservice.entity.Authority;
import com.jego.pokemon.securityservice.entity.User;
import com.jego.pokemon.securityservice.exception.UserAlreadyExistException;
import com.jego.pokemon.securityservice.repository.UserRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElse(null);
        List<GrantedAuthority> authorities = buildAuthorities(user.getAuthorities());
        return buildUser(user,authorities);
    }

    private org.springframework.security.core.userdetails.User buildUser(User user , List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildAuthorities(List<Authority> authorities){
        Set<GrantedAuthority> auths = new HashSet<>();
        for(Authority authority: authorities){
            auths.add(new SimpleGrantedAuthority(authority.getRole()));
        }
        return new ArrayList<>(auths);
    }

}
