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
    public List<TeamMember> saveTeam(Long trainerId, List<PokemonBasicDTO> memberList) {
        teamRepository.deleteTeam(trainerId);
        List<TeamMember> newTeam = new ArrayList<>();
        List<TeamMember> teamDB = getTeam(trainerId);

        TeamMember teamMemberDB;
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
                    teamMemberDB.setTrainerId(trainerId);
                    teamMemberDB.setDeleted(false);
                }
                newTeam.add(teamMemberDB);
            }
            newTeam = teamRepository.saveAll(newTeam);
        }else{
            for (PokemonBasicDTO pokemonDTO:memberList) {
                teamMemberDB = new TeamMember();
                teamMemberDB.setPokemonName(pokemonDTO.getName());
                teamMemberDB.setTrainerId(trainerId);
                teamMemberDB.setDeleted(false);
                newTeam.add(teamMemberDB);
            }
            newTeam = teamRepository.saveAll(newTeam);
        }
        return newTeam;
    }

    @Override
    public List<TeamMember> getTeam(Long trainerId) {
        return teamRepository.findByTrainerIdAndDeletedFalse(trainerId).orElse(null);
    }

    @Override
    public TeamMember getTeamMember(Long trainerId, String pokemonName) {
        TeamMember memberTeam = teamRepository
                .findByTrainerIdAndPokemonNameAndDeletedFalse(trainerId, pokemonName).orElse(null);

        return memberTeam;
    }
}
