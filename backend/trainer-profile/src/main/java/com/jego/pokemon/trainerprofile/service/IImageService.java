package com.jego.pokemon.trainerprofile.service;

public interface IImageService {
    public byte[] compressBytes(byte[] data);
    public byte[] decompressBytes(byte[] data);
}
