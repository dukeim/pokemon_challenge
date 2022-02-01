package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.repository.ITrainerProfileRepository;
import com.jego.pokemon.trainerprofile.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class TrainerProfileImpl implements  ITrainerProfileService{
    @Autowired
    ITrainerProfileRepository trainerProfileRepository;


    @Override
    public TrainerProfile createTrainerProfile(String userName, TrainerProfileNewDTO trainerProfileNewDTO) {
        TrainerProfile trainerProfileDB = getTrainerProfile(userName);
        if (trainerProfileDB != null){
            return  trainerProfileDB;
        }
        trainerProfileDB = new TrainerProfile();
        trainerProfileDB.setUserName(userName);
        trainerProfileDB.setName(trainerProfileNewDTO.getName());
        trainerProfileDB.setDocType(getDocumentType(trainerProfileNewDTO.getBirthDate()));
        trainerProfileDB.setHobby(trainerProfileNewDTO.getHobby());
        trainerProfileDB.setDocNumber(trainerProfileNewDTO.getDocNumber());
        trainerProfileDB.setBirthDate(trainerProfileNewDTO.getBirthDate());
        trainerProfileDB.setPhoto(trainerProfileNewDTO.getPhoto());
        trainerProfileDB = trainerProfileRepository.save ( trainerProfileDB );
        return trainerProfileDB;
    }

    @Override
    public TrainerProfile updateTrainerProfile(String userName, TrainerProfileUpdateDTO trainerProfileUpdateDTO) {
        TrainerProfile trainerProfileDB = getTrainerProfile(userName);
        if (trainerProfileDB == null){
            return  null;
        }
        if(trainerProfileUpdateDTO.getName()!=null) trainerProfileDB.setName(trainerProfileUpdateDTO.getName());
        if(trainerProfileUpdateDTO.getBirthDate()!=null) {
            trainerProfileDB.setBirthDate(trainerProfileUpdateDTO.getBirthDate());
            trainerProfileDB.setDocType(getDocumentType(trainerProfileUpdateDTO.getBirthDate()));
        }
        if(trainerProfileUpdateDTO.getHobby()!=null) trainerProfileDB.setHobby(trainerProfileUpdateDTO.getHobby());
        if(trainerProfileUpdateDTO.getDocNumber()!=null) trainerProfileDB.setDocNumber(trainerProfileUpdateDTO.getDocNumber());
        if(trainerProfileUpdateDTO.getPhoto()!=null) trainerProfileDB.setPhoto(trainerProfileUpdateDTO.getPhoto());

        return  trainerProfileRepository.save(trainerProfileDB);
    }

    @Override
    public TrainerProfile getTrainerProfile(String userName) {
        return  trainerProfileRepository.findByUserName(userName).orElse(null);
    }

    private String getDocumentType(LocalDate birthDate){
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age<18? Constants.MINORIDAD : Constants.DUI;
    }
}
