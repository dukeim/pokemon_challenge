package com.jego.pokemon.trainerprofile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.service.ITrainerProfileService;
import com.jego.pokemon.trainerprofile.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/trainer")
public class TrainerProfileController {

    public final ITrainerProfileService trainerProfileService;

    @GetMapping()
    public ResponseEntity<TrainerProfile> getTrainerProfile(@RequestHeader("USERNAME") String userName) {
        log.info("Fetching Customer with username {}", userName);
        TrainerProfile trainerProfile = trainerProfileService.getTrainerProfile(userName);
        if (  null == trainerProfile) {
            log.error("Trainer profile with username {} not found.", userName);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(trainerProfile);
    }

    @PostMapping
    public ResponseEntity<TrainerProfile> createTrainerProfile(@Valid @RequestBody TrainerProfileNewDTO trainerProfileNewDTO,
                                                               @RequestHeader("USERNAME") String userName, BindingResult result) {
        log.info("Creating trainer profile : {}", userName);
        TrainerProfile trainerProfileDB;

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        trainerProfileDB = trainerProfileService.createTrainerProfile (userName, trainerProfileNewDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(trainerProfileDB);
    }

    @PutMapping
    public ResponseEntity<?> updateTrainerProfile(@Valid @RequestBody TrainerProfileUpdateDTO trainerProfileUpdateDTO,
                                                  @RequestHeader("USERNAME") String userName, BindingResult result) {
        log.info("Updating trainer with username {}", userName);
        TrainerProfile trainerProfileDB;

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        trainerProfileDB = trainerProfileService.updateTrainerProfile(userName, trainerProfileUpdateDTO);

        if ( null == trainerProfileDB ) {
            log.error("Unable to update. Trainer profile with username {} not found.", userName);
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.status(HttpStatus.CREATED).body(trainerProfileDB);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
