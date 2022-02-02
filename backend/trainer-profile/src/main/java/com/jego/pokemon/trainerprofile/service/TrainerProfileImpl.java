package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.dto.TrainerProfileCreatedDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileNewDTO;
import com.jego.pokemon.trainerprofile.dto.TrainerProfileUpdateDTO;
import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.exception.TrainerNotFoundException;
import com.jego.pokemon.trainerprofile.repository.ITrainerProfileRepository;
import com.jego.pokemon.trainerprofile.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Service
public class TrainerProfileImpl implements  ITrainerProfileService{
    @Autowired
    private ITrainerProfileRepository trainerProfileRepository;
    @Autowired
    private IImageService imageService;

    @Override
    public TrainerProfileCreatedDTO createTrainerProfile(String userName, TrainerProfileNewDTO trainerProfileNewDTO) {
        TrainerProfile trainerProfileDB = getTrainerProfile(userName);
        TrainerProfileCreatedDTO trainerProfileCreatedDTO;

        if (trainerProfileDB != null){
            return  mapTrainerProfileDBToTrainerProfileCreatedDTO(trainerProfileDB);
        }
        trainerProfileDB = new TrainerProfile();
        trainerProfileDB.setUserName(userName);
        trainerProfileDB.setName(trainerProfileNewDTO.getName());
        trainerProfileDB.setDocType(getDocumentType(trainerProfileNewDTO.getBirthDate()));
        trainerProfileDB.setHobby(trainerProfileNewDTO.getHobby());
        trainerProfileDB.setDocNumber(trainerProfileNewDTO.getDocNumber());
        trainerProfileDB.setBirthDate(trainerProfileNewDTO.getBirthDate());
        trainerProfileDB.setPhoto(null);
        trainerProfileDB = trainerProfileRepository.save ( trainerProfileDB );

        return mapTrainerProfileDBToTrainerProfileCreatedDTO(trainerProfileDB);
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

        return  trainerProfileRepository.save(trainerProfileDB);
    }

    @Override
    public TrainerProfile getTrainerProfile(String userName) {
        return  trainerProfileRepository.findByUserName(userName).orElse(null);
    }

    @Override
    public int savePhoto(String userName, byte[] image) throws IOException{
        TrainerProfile trainerProfileDB;
        byte[] compressedImage = null;

        compressedImage = imageService.compressBytes(image);
        if (compressedImage != null) {
            trainerProfileDB = getTrainerProfile(userName);
            if (trainerProfileDB != null) {
                trainerProfileDB.setPhoto(compressedImage);
                trainerProfileRepository.save(trainerProfileDB);
                return 0;
            } else {
                return 1;
            }
        }else{
            return 2;
        }
    }

    public byte[] getPhoto(String userName) throws TrainerNotFoundException{
        TrainerProfile trainerProfileDB;
        trainerProfileDB = getTrainerProfile(userName);

        if(trainerProfileDB!=null){
            return imageService.decompressBytes(trainerProfileDB.getPhoto());
        }else{
            throw new TrainerNotFoundException("Trainer not found");
        }

    }
    private String getDocumentType(LocalDate birthDate){
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age<18? Constants.MINORIDAD : Constants.DUI;
    }

    private TrainerProfileCreatedDTO mapTrainerProfileDBToTrainerProfileCreatedDTO(TrainerProfile trainerProfileDB){
        TrainerProfileCreatedDTO trainerProfileCreatedDTO = new TrainerProfileCreatedDTO();
        trainerProfileCreatedDTO.setName(trainerProfileDB.getName());
        trainerProfileCreatedDTO.setBirthDate(trainerProfileDB.getBirthDate());
        trainerProfileCreatedDTO.setDocNumber(trainerProfileDB.getDocNumber());
        trainerProfileCreatedDTO.setDocType(trainerProfileDB.getDocType());
        trainerProfileCreatedDTO.setHobby(trainerProfileDB.getHobby());
        return trainerProfileCreatedDTO;
    }
}
