package com.gabosol777.demo.utils;

public class WordUtils {

    private static int A_LOW_CASE_CHAR_ASCII_VALUE = 97;
    private static int Z_LOW_CASE_CHAR_ASCII_VALUE = 122;

    public static int calculateTotalValue(String word) {
       return word.chars()
           .filter(c -> c >= A_LOW_CASE_CHAR_ASCII_VALUE && c <= Z_LOW_CASE_CHAR_ASCII_VALUE)
           .map(c -> c - A_LOW_CASE_CHAR_ASCII_VALUE  + 1)
           .sum();
    }
}
