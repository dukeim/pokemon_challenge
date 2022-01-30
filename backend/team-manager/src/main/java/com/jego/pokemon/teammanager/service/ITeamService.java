package com.jego.pokemon.teammanager.service;

import com.jego.pokemon.teammanager.dto.PokemonBasicDTO;
import com.jego.pokemon.teammanager.entity.TeamMember;

import java.util.List;

public interface ITeamService {
    public List<TeamMember> saveTeam(Long trainerId, List<PokemonBasicDTO> memberList);
    public  List<TeamMember> getTeam(Long trainerId);
    public TeamMember getTeamMember(Long trainerId, String pokemonName);
}
