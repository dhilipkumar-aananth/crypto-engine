package com.crypto.encryption;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

/**
 * @author dhilipkumar ananth
 * @created_on 17-Feb-2025
 */
@Component
public class AES {
    private static final String SLASH = "/";
    private static final String ALGORITHM = "AES";
    private static final String MODE = "ECB";
    private static final String PADDING = "PKCS5Padding";
    private static final String SECRET_KEY = "Xrty04qw927!@#zyx*&%X)Z(";

    public static String encrypt(String data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM + SLASH + MODE + SLASH + PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        if (!isBase64(encryptedData)) {
            throw new IllegalArgumentException("Invalid Base64 encoded string");
        }
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM + SLASH + MODE + SLASH + PADDING);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    private static boolean isBase64(String str) {
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}