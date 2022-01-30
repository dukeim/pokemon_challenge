package com.jego.pokemon.teammanager.repository;

import com.jego.pokemon.teammanager.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITeamRepository extends JpaRepository<TeamMember, Long> {
    public Optional<List<TeamMember>> findByTrainerIdAndDeletedFalse(Long trainerId);
    public Boolean existsByTrainerIdAndPokemonNameAndDeletedFalse(Long trainerId, String pokemonName);
    public Boolean existsByTrainerIdAndPokemonName(Long trainerId, String pokemonName);
    public Optional<TeamMember> findByTrainerIdAndPokemonNameAndDeletedFalse(Long trainerId, String pokemonName);

    @Modifying
    @Query(value = "update TeamMember tm set tm.deleted = true where tm.trainerId = :trainerId and tm.deleted = false")
    void deleteTeam(Long trainerId);
}
