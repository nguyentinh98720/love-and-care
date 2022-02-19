package com.tinhnv.validator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class HashPassword {
    private String password;
    private String hashEncoded;
    private String saltEncoded;

    public HashPassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HashPassword(String password) {
        super();
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashEncoded() throws IllegalAccessException {
        if(this.hashEncoded == null) {
            throw new IllegalAccessException("hashing before get value!");
        }
        return this.hashEncoded;
    }

    public String getSaltEncoded() throws IllegalAccessException {
        if(this.hashEncoded == null) {
            throw new IllegalAccessException("hashing before get value!");
        }
        return this.saltEncoded;
    }

    public boolean verify(String hashEncodedString, String saltEncodedString) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] salt = decoder.decode(saltEncodedString);
        KeySpec keySpec = new PBEKeySpec(this.password.toCharArray(), salt, 1024, 128);
        SecretKeyFactory secretKeyFactory;
        byte[] hash = new byte[0];
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        String hashEncoded = encoder.encodeToString(hash);
        return hashEncoded.equals(hashEncodedString);
    }

    public boolean hashing() {
        if(this.password == null) {
            return false;
        }
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        byte[] hash = new byte[0];
        secureRandom.nextBytes(salt);
        KeySpec keySpec = new PBEKeySpec(this.password.toCharArray(), salt, 1024, 128);
        SecretKeyFactory secretKeyFactory;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        this.hashEncoded = encoder.encodeToString(hash);
        this.saltEncoded = encoder.encodeToString(salt);
        this.password = null;
        return true;
    }
}

