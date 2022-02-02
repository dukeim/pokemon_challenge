package com.jego.pokemon.trainerprofile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileCreatedDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.exception.TrainerNotFoundException;
import com.jego.pokemon.trainerprofile.service.ITrainerProfileService;
import com.jego.pokemon.trainerprofile.util.ErrorMessage;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

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
    public ResponseEntity<TrainerProfileCreatedDTO> createTrainerProfile(@Valid @RequestBody TrainerProfileNewDTO trainerProfileNewDTO,
                                                               @RequestHeader("USERNAME") String userName, BindingResult result)
                                                                throws ResponseStatusException{
        log.info("Creating trainer profile : {}", userName);
        TrainerProfile trainerProfileDB;
        TrainerProfileCreatedDTO trainerProfileCreatedDTO;

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        trainerProfileCreatedDTO = trainerProfileService.createTrainerProfile (userName, trainerProfileNewDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(trainerProfileCreatedDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateTrainerProfile(@Valid @RequestBody TrainerProfileUpdateDTO trainerProfileUpdateDTO,
                                                  @RequestHeader("USERNAME") String userName, BindingResult result)
                                                    throws ResponseStatusException{
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

    @PostMapping("/photo/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("imageFile") MultipartFile file,
                                         @RequestHeader("USERNAME") String userName) {
        byte[] image = null;
        int result;
        try {
            image = file.getBytes();
            result = trainerProfileService.savePhoto(userName, image);
            switch (result){
                case 0:
                    return ResponseEntity.status(HttpStatus.OK).body("Image uploaded");
                case 1:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username not found");
                case 2:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image is invalid");
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error");
            }

        }catch (IOException ioe){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ioe.getMessage());
        }
    }
    @GetMapping(value = "/photo/download",
                produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getPhoto(@RequestHeader("USERNAME") String userName){
        byte[] image = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        try {
            image = trainerProfileService.getPhoto(userName);
        }catch (TrainerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(null);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);

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
