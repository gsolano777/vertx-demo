package com.gabosol777.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortedListTest {

    private SortedList<String> stringSortedList;
    @BeforeEach
    public void setup() {
        stringSortedList = new SortedList<>();
    }
    @Test
    public void testx() {
        stringSortedList.add("apple");
        stringSortedList.add("car");
        stringSortedList.add("bee");

        stringSortedList.add("apple");
        assertEquals(3, stringSortedList.size());
        assertEquals(0, stringSortedList.findIndexOf("apple"));
        assertEquals(1, stringSortedList.findIndexOf("bee"));
        assertEquals(2, stringSortedList.findIndexOf("car"));
    }

}
