package ru.itis.hardwarestore.utils;

import java.security.SecureRandom;

public class DiscountCardUtils {
    private static final String CHARACTERS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"; // без 0,1,O,I,l
    private static final int LENGTH = 12;
    private static final SecureRandom random = new SecureRandom();

    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
