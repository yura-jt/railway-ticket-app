package com.railway.booking.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncryptor {
    private static final Logger LOGGER = LogManager.getLogger(PasswordEncryptor.class);

    private static final byte[] SALT = new byte[]{-28, 92, -41, -15, 75, -12, 49, -82, -46, -84, 92, -27, 45, -23, -16, 53};

    private static final int ITERATION_COUNT = 1000;
    private static final int KEY_LENGTH = 64 * 8;
    private static final int HASH_SIZE = 16;


    public String encrypt(String password) {
        char[] passwordChars = password.toCharArray();
        byte[] hash = new byte[0];
        try {
            PBEKeySpec spec = new PBEKeySpec(passwordChars, SALT, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.warn(e);
        }
        return toHex(hash);
    }

    private byte[] getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
        }
        byte[] salt = new byte[HASH_SIZE];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}