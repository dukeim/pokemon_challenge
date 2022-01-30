package com.jego.pokemon.trainerprofile.service;

import com.jego.pokemon.trainerprofile.entity.TrainerProfile;
import com.jego.pokemon.trainerprofile.repository.ITrainerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerProfileImpl implements  ITrainerProfileService{
    @Autowired
    ITrainerProfileRepository trainerProfileRepository;


    @Override
    public TrainerProfile createTrainerProfile(TrainerProfile trainerProfile) {
        TrainerProfile trainerProfileDB = getTrainerProfile(trainerProfile.getTrainerId());
        if (trainerProfileDB != null){
            return  trainerProfileDB;
        }
        trainerProfileDB = trainerProfileRepository.save ( trainerProfile );
        return trainerProfileDB;
    }

    @Override
    public TrainerProfile updateTrainerProfile(TrainerProfile trainerProfile) {
        TrainerProfile trainerProfileDB = getTrainerProfile(trainerProfile.getTrainerId());
        if (trainerProfileDB == null){
            return  null;
        }
        trainerProfileDB.setName(trainerProfile.getName());
        trainerProfileDB.setBirthDate(trainerProfile.getBirthDate());
        trainerProfileDB.setDocNumber(trainerProfile.getDocNumber());
        trainerProfileDB.setDocType(trainerProfile.getDocType());
        trainerProfileDB.setHobby(trainerProfile.getHobby());
        trainerProfileDB.setPhoto(trainerProfile.getPhoto());
        return  trainerProfileRepository.save(trainerProfileDB);
    }

    @Override
    public TrainerProfile getTrainerProfile(Long trainerId) {
        return  trainerProfileRepository.findByTrainerId(trainerId).orElse(null);
    }
}
