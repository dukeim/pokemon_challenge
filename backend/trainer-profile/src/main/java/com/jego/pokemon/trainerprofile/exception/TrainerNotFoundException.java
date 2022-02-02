package com.jego.pokemon.trainerprofile.exception;

public class TrainerNotFoundException extends Exception{
    public TrainerNotFoundException() {
        super();
    }

    public TrainerNotFoundException(String message) {
        super(message);
    }

    public TrainerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
