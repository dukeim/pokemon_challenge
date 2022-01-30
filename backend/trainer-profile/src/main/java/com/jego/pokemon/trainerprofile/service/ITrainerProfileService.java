package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.entity.TrainerProfile;

public interface ITrainerProfileService {
    public TrainerProfile createTrainerProfile(TrainerProfile trainerProfile);
    public TrainerProfile updateTrainerProfile(TrainerProfile trainerProfile);
    public  TrainerProfile getTrainerProfile(Long trainerId);

}
