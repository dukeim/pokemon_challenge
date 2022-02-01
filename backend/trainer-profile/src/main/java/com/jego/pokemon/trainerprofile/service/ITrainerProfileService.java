package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;

public interface ITrainerProfileService {
    public TrainerProfile createTrainerProfile(String userName, TrainerProfileNewDTO trainerProfileNewDTO);
    public TrainerProfile updateTrainerProfile(String userName, TrainerProfileUpdateDTO trainerProfileUpdateDTO);
    public  TrainerProfile getTrainerProfile(String userName);

}
