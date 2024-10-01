package com.sw.swapiclientsdk.common;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int KEY_LENGTH = 32;

    public static String generateKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(KEY_LENGTH);
        for (int i = 0; i < KEY_LENGTH; i++) {
            int rndCharAt = random.nextInt(CHAR_SET.length());
            char rndChar = CHAR_SET.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public static String generateBase64Key() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[KEY_LENGTH];
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static void main(String[] args) {
        String accessKey = generateKey();
        String secretKey = generateBase64Key();

        System.out.println("AccessKey: " + accessKey);
        System.out.println("SecretKey: " + secretKey);
    }
}
