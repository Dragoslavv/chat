package com.example.demo.utils;

import java.util.regex.Pattern;

public class Validation {

    public static boolean patternMatches(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }
}
