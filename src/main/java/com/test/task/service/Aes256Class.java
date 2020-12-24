package com.test.task.service;

import com.test.task.exception.EncryptionException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

@Service
public class Aes256Class {
    private final SecretKey secretKey;
    public Aes256Class() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            this.secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("No Such Algorithm");
        }
    }
    public byte[] getAesOutput(byte[] rawMessage, int cipherMode){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, this.secretKey);
            return cipher.doFinal(rawMessage);
        } catch (Exception e){
            throw new EncryptionException("Encryption/Decryption failed");
        }
    }
}
