package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.dto.TrainerProfileBasicDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.exception.TrainerNotFoundException;

import java.io.IOException;

public interface ITrainerProfileService {
    public TrainerProfileBasicDTO createTrainerProfile(String userName, TrainerProfileNewDTO trainerProfileNewDTO);
    public TrainerProfile updateTrainerProfile(String userName, TrainerProfileUpdateDTO trainerProfileUpdateDTO);
    public  TrainerProfileBasicDTO getTrainerProfileBasic(String userName);
    public int savePhoto(String userName, byte[] image) throws IOException;
    public byte[] getPhoto(String userName) throws TrainerNotFoundException;

}
