package com.jego.pokemon.gatewayservice.auth;

import com.jego.pokemon.gatewayservice.auth.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Component
public class AuthenticationManager  implements ReactiveAuthenticationManager {
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String username = jwtUtil.getUsernameFromToken(authentication.getCredentials().toString());

        if (username != null ) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.emptyList()
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
