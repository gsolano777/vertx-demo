package com.gabosol777.demo.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordUtilsTest {

    @Test
    public void testCalculateTotalValue() {
        assertEquals(1, WordUtils.calculateTotalValue("a"));
        assertEquals(2, WordUtils.calculateTotalValue("b"));
        assertEquals(3, WordUtils.calculateTotalValue("ab"));

        assertEquals(52, WordUtils.calculateTotalValue("hello"));
        assertEquals(72, WordUtils.calculateTotalValue("world"));
        assertEquals(68, WordUtils.calculateTotalValue("house"));
    }

}
