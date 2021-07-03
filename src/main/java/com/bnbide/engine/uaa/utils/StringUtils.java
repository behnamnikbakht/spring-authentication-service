package com.bnbide.engine.uaa.utils;

import java.security.SecureRandom;

public class StringUtils {

    private static final SecureRandom random = new SecureRandom();

    private static final char[] numbers = "0123456789".toCharArray();

    public static String createRandomNumericString(int length) {
        StringBuilder pincode = new StringBuilder(numbers[1 + random.nextInt(9)]);
        for(int i=0; i<length; i++) {
            pincode.append(numbers[random.nextInt(10)]);
        }
        return pincode.toString();
    }

}
