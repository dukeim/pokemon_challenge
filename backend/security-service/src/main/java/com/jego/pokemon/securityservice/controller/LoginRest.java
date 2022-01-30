package com.jego.pokemon.securityservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jego.pokemon.securityservice.auth.util.Constant;
import com.jego.pokemon.securityservice.dto.TokenDTO;
import com.jego.pokemon.securityservice.dto.UserDataDTO;
import com.jego.pokemon.securityservice.entity.User;
import com.jego.pokemon.securityservice.exception.UserAlreadyExistException;
import com.jego.pokemon.securityservice.service.ISecurityService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(value = "/security")
@RestController
public class LoginRest {

    @Autowired
    ISecurityService securityService;

    @RequestMapping(value="/login" , method = RequestMethod.POST)
    public ResponseEntity<TokenDTO> getUser(HttpServletResponse res){
        log.debug("login - inicio");

        TokenDTO tokenBean = new TokenDTO();
        String token =res.getHeader( Constant.TOKEN_HEADER_STRING );
        if (token !=null){

            tokenBean.setToken(token);
            return new ResponseEntity<TokenDTO>(tokenBean, HttpStatus.OK);
        }else{
            return new ResponseEntity<TokenDTO>(tokenBean, HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value="/signup" , method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDataDTO userDataDTO, final BindingResult bindingResult){
        log.debug("signup - inicio");

        if(bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        try {
            securityService.registerUser(userDataDTO);
            return new ResponseEntity<UserDataDTO>(userDataDTO, HttpStatus.OK);
        }catch (UserAlreadyExistException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @RequestMapping(value="/welcome" , method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }

    private String formatMessage( BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}