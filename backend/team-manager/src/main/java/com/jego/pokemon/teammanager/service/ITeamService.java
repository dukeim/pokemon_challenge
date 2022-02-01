package com.jego.pokemon.teammanager.service;

import com.jego.pokemon.teammanager.dto.PokemonBasicDTO;
import com.jego.pokemon.teammanager.entity.TeamMember;

import java.util.List;

public interface ITeamService {
    public List<TeamMember> saveTeam(String userName, List<PokemonBasicDTO> memberList);
    public List<TeamMember> getTeam(String userName);
    public List<TeamMember> getTeamHistory(String userName);

}
