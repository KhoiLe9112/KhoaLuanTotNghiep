package com.dhkh.action;

import java.util.Random;

public class RandomPasswordGenerator {
    private static final String ALPHA_NUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+-={}[]|;:,.<>?/~`";
    private static final Random random = new Random();

    public static String generateRandomPassword(int length, boolean includeLowercase, boolean includeUppercase, boolean includeNumbers, boolean includeSpecialCharacters) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be positive");
        }

        StringBuilder passwordBuilder = new StringBuilder(length);

        String characterSet = ALPHA_NUMERIC_CHARACTERS;

        if (includeLowercase) {
            characterSet += ALPHA_NUMERIC_CHARACTERS.substring(0, 26);
        }

        if (includeUppercase) {
            characterSet += ALPHA_NUMERIC_CHARACTERS.substring(26, 52);
        }

        if (includeNumbers) {
            characterSet += "0123456789";
        }

        if (includeSpecialCharacters) {
            characterSet += SPECIAL_CHARACTERS;
        }

        for (int i = 0; i < length; i++) {
            passwordBuilder.append(characterSet.charAt(random.nextInt(characterSet.length())));
        }

        return passwordBuilder.toString();
    }
}