package com.jego.pokemon.pokemoncatalogue.controller;

import com.jego.pokemon.pokemoncatalogue.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalogue")
public class CatalogueController {
    public final RestTemplate restTemplate;

    private final Environment env;

    @GetMapping
    public ResponseEntity<?> getCatalogue() {
        PokemonImageDTO pokemonImageDTO;
        GenerationDTO generationDTO;
        PokemonDTO pokemonDTO;
        List<PokemonDTO> pokemonLst = new ArrayList<>();

        try{
            generationDTO = restTemplate.getForObject(env.getProperty("pokeapi.url.generation"), GenerationDTO.class);
        }catch (HttpClientErrorException e){
            return ResponseEntity.status( HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Generation not found"));
        }

        for (PokemonSpeciesDTO ps:generationDTO.getPokemon_species()) {
            try{
                pokemonImageDTO = restTemplate.getForObject(env.getProperty("pokeapi.url.pokemon") + ps.getName(), PokemonImageDTO.class);
            } catch (HttpClientErrorException e){
                return ResponseEntity.status( HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Pokemon named'"+ ps.getName() + "' not found"));
            }

            pokemonDTO = new PokemonDTO();
            pokemonDTO.setName(ps.getName());
            pokemonDTO.setImage_url(pokemonImageDTO.getSprites().getOther().getHome().getFront_default());
            pokemonLst.add(pokemonDTO);
        }
        return  ResponseEntity.ok(pokemonLst);
    }

}
