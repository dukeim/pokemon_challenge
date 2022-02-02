package com.jego.pokemon.gatewayservice.config;


import com.jego.pokemon.gatewayservice.auth.AuthenticationManager;
import com.jego.pokemon.gatewayservice.auth.SecurityContextRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.security.Principal;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig  {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> {
                    return Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode( HttpStatus.UNAUTHORIZED);
                    });
                }).accessDeniedHandler((swe, e) -> {
                    return Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    });
                }).and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers( HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/security/login").permitAll()
                .pathMatchers("/security/signup").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty("Default User")
                .map(userName -> {
                    //adds header to proxied request
                    exchange.getRequest().mutate().header("USERNAME", userName).build();
                    return exchange;
                })
                .flatMap(chain::filter);

    }

}