package com.jego.pokemon.trainerprofile.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
@Service
public class ImageService implements IImageService{
    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        log.info("Original Image Byte Size - {}",data.length);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("Error closing stream - " + e.getMessage());
        }
        log.info("Compressed Image Byte Size - {}", outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public byte[] decompressBytes(byte[] data) {

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
            log.error("Error closing stream - " + ioe.getMessage());
        } catch (DataFormatException e) {
            log.error("Error inflating data - " + e.getMessage());
        }
        return outputStream.toByteArray();
    }
}
