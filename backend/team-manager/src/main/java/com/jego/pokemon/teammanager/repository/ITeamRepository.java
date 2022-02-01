package com.jego.pokemon.teammanager.repository;

import com.jego.pokemon.teammanager.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITeamRepository extends JpaRepository<TeamMember, Long> {
    public Optional<List<TeamMember>> findByUserNameAndDeletedFalse(String userName);
    public Optional<List<TeamMember>> findByUserName(String userName);


    @Modifying
    @Query(value = "update TeamMember tm set tm.deleted = true where tm.userName = :userName and tm.deleted = false")
    void deleteTeam(String userName);
}
