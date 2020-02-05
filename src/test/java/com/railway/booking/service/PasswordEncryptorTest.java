package com.railway.booking.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordEncryptorTest {
    private PasswordEncryptor passwordEncryptor;


    @Before
    public void init() {
        passwordEncryptor = new PasswordEncryptor();
    }

    @Test
    public void encryptSimplePass() {
        String actualHash = passwordEncryptor.encrypt("PASSWORD");
        String expectedHash = passwordEncryptor.encrypt("PASSWORD");
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void encryptSimplePassWithDigits() {
        String actualHash = passwordEncryptor.encrypt("PASSWORDmulti298");
        String expectedHash = passwordEncryptor.encrypt("PASSWORDmulti298");
        assertEquals(expectedHash, actualHash);

    }

    @Test
    public void encryptLongPassWithDigits() {
        String actualHash = passwordEncryptor.encrypt("PASSWORDmulti298sdfjkehkjUsdlkjhkje9834SD");
        String expectedHash = passwordEncryptor.encrypt("PASSWORDmulti298sdfjkehkjUsdlkjhkje9834SD");
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void encryptComplexPass() {
        String actualHash = passwordEncryptor.encrypt("4738hsJfl34Sksdl3_@349");
        String expectedHash = passwordEncryptor.encrypt("4738hsJfl34Sksdl3_@349");
        assertEquals(expectedHash, actualHash);
    }
}