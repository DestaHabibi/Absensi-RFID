/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.util;

/**
 *
 * @author lenovo
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptionUtils {
    private static final String ALGORITHM = "AES";
    private static final String KEY = System.getProperty("KEY");
    private static final byte[] SECRET_KEY = KEY.getBytes();
    
    public static String encrypt(String value) {
        try {
            SecretKeySpec spec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, spec);
            
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException |
                BadPaddingException | IllegalBlockSizeException |
                NoSuchPaddingException e) {
            System.err.println("Error saat enkripsi: " + e.getMessage());
            return null;
        }
    }
}
