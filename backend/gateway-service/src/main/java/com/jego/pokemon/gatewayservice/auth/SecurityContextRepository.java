package com.jego.pokemon.gatewayservice.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String authHeader = request.getHeaders().getFirst( HttpHeaders.AUTHORIZATION);
        log.info(authHeader);
        //if (authHeader != null && authHeader.startsWith("Bearer ")) {
        if (authHeader != null ) {
            //String authToken = authHeader.substring(7);

            Authentication auth = new UsernamePasswordAuthenticationToken(authHeader, authHeader);
            return this.authenticationManager.authenticate(auth)
                    .map(  SecurityContextImpl:: new);
        } else {
            return Mono.empty();
        }
    }
}
