package com.jego.pokemon.teammanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jego.pokemon.teammanager.dto.*;
import com.jego.pokemon.teammanager.entity.TeamMember;
import com.jego.pokemon.teammanager.service.ITeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/team_manager")
public class TeamManagerController {
    public final ITeamService teamService;
    public final RestTemplate restTemplate;
    private final Environment env;

    @PostMapping
    public ResponseEntity<?> saveTeam(@Valid @RequestBody SaveTeamDTO saveTeamDTO, BindingResult result) {

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        teamService.saveTeam(saveTeamDTO.trainerId, saveTeamDTO.pokemons);

        return  ResponseEntity.status( HttpStatus.CREATED).body(new ResponseMessage("Team saved"));
    }

    @GetMapping(value = "/team/{trainerId}")
    public ResponseEntity<?> getTeam(@PathVariable("trainerId") long trainerId){
        List<TeamMember> teamMembers = teamService.getTeam(trainerId);
        PokemonStatsDTO pokemonStatsDTO;
        PokemonResumeDTO pokemonResumeDTO;
        TeamDetailDTO teamDetailDTO;

        if(teamMembers != null && !teamMembers.isEmpty()) {
            teamDetailDTO = new TeamDetailDTO();
            teamDetailDTO.setTrainerId(trainerId);

            for (TeamMember member : teamMembers) {
                try {
                    pokemonResumeDTO = restTemplate.getForObject(
                            env.getProperty("pokeapi.url.pokemon") + member.getPokemonName(),
                            PokemonResumeDTO.class);
                }catch (HttpClientErrorException e){
                    return ResponseEntity.status( HttpStatus.NOT_FOUND)
                            .body(new ResponseMessage("Team member named '" + member.getPokemonName() + "' not found"));
                }
                teamDetailDTO.getPokemons().add(pokemonResumeDTO);
            }
            return ResponseEntity.ok(teamDetailDTO);
        }else{
            return ResponseEntity.status( HttpStatus.NOT_FOUND).body(new ResponseMessage("Team not found"));
        }
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
