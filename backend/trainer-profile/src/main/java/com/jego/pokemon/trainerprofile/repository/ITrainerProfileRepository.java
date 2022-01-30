package com.jego.pokemon.trainerprofile.repository;

import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITrainerProfileRepository extends JpaRepository<TrainerProfile,Long> {
    public Optional<TrainerProfile> findByTrainerId(Long trainerId);
}
