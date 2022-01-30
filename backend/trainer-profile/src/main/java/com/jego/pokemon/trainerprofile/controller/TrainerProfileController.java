package com.jego.pokemon.trainerprofile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.service.ITrainerProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/trainers")
public class TrainerProfileController {

    public final ITrainerProfileService trainerProfileService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TrainerProfile> getTrainerProfile(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        TrainerProfile trainerProfile = trainerProfileService.getTrainerProfile(id);
        if (  null == trainerProfile) {
            log.error("Trainer profile with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(trainerProfile);
    }

    @PostMapping
    public ResponseEntity<TrainerProfile> createTrainerProfile(@Valid @RequestBody TrainerProfile trainerProfile, BindingResult result) {
        log.info("Creating Trainer Profile : {}", trainerProfile);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        TrainerProfile trainerProfileDB = trainerProfileService.createTrainerProfile (trainerProfile);

        return  ResponseEntity.status( HttpStatus.CREATED).body(trainerProfileDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTrainerProfile(@Valid @PathVariable("id") long id, @RequestBody TrainerProfile trainerProfile, BindingResult result) {
        log.info("Updating Customer with id {}", id);

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        TrainerProfile currentTrainerProfile = trainerProfileService.getTrainerProfile(id);

        if ( null == currentTrainerProfile ) {
            log.error("Unable to update. Trainer profile with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        trainerProfile.setTrainerId(id);
        trainerProfile = trainerProfileService.updateTrainerProfile(trainerProfile);
        return  ResponseEntity.ok(trainerProfile);
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
