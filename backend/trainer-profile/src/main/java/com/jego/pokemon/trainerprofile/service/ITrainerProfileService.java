package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.dto.TrainerProfileCreatedDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.exception.TrainerNotFoundException;

import java.io.IOException;

public interface ITrainerProfileService {
    public TrainerProfileCreatedDTO createTrainerProfile(String userName, TrainerProfileNewDTO trainerProfileNewDTO);
    public TrainerProfile updateTrainerProfile(String userName, TrainerProfileUpdateDTO trainerProfileUpdateDTO);
    public  TrainerProfile getTrainerProfile(String userName);
    public int savePhoto(String userName, byte[] image) throws IOException;
    public byte[] getPhoto(String userName) throws TrainerNotFoundException;

}
