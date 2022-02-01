package com.jego.pokemon.teammanager.service;

import com.jego.pokemon.teammanager.dto.PokemonBasicDTO;
import com.jego.pokemon.teammanager.entity.TeamMember;
import com.jego.pokemon.teammanager.repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
public class TeamService implements ITeamService{
    @Autowired
    ITeamRepository teamRepository;

    @Override
    @Transactional()
    public List<TeamMember> saveTeam(String userName, List<PokemonBasicDTO> memberList) {
        List<TeamMember> newTeam = new ArrayList<>();
        TeamMember teamMemberDB;

        teamRepository.deleteTeam(userName);
        List<TeamMember> teamDB = getTeamHistory(userName);
        if(teamDB != null){
            for (PokemonBasicDTO pokemonDTO:memberList) {
                teamMemberDB = teamDB.stream()
                        .filter(team -> team.getPokemonName().equals(pokemonDTO.getName()))
                        .findFirst().orElse(null);
                if(teamMemberDB!=null){
                    teamMemberDB.setDeleted(false);
                }else{
                    teamMemberDB = new TeamMember();
                    teamMemberDB.setPokemonName(pokemonDTO.getName());
                    teamMemberDB.setUserName(userName);
                    teamMemberDB.setDeleted(false);
                }
                newTeam.add(teamMemberDB);
            }
            newTeam = teamRepository.saveAll(newTeam);
        }else{
            for (PokemonBasicDTO pokemonDTO:memberList) {
                teamMemberDB = new TeamMember();
                teamMemberDB.setPokemonName(pokemonDTO.getName());
                teamMemberDB.setUserName(userName);
                teamMemberDB.setDeleted(false);
                newTeam.add(teamMemberDB);
            }
            newTeam = teamRepository.saveAll(newTeam);
        }
        return newTeam;
    }

    @Override
    public List<TeamMember> getTeam(String userName) {
        return teamRepository.findByUserNameAndDeletedFalse(userName).orElse(null);
    }

    @Override
    public List<TeamMember> getTeamHistory(String userName) {
        return teamRepository.findByUserName(userName).orElse(null);
    }


}
