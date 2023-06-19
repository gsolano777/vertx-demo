package com.gabosol777.demo.service;

import com.gabosol777.demo.domain.AnalyzeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordAnalyzerTest {

    private WordAnalyzer wordAnalyzer;

    @BeforeEach
    public void setup() {
        wordAnalyzer = new WordAnalyzer();
    }
    @Test
    public void testResolveValueWithExactMatchAndSingleWord() {
        assertNull(wordAnalyzer.analyze("a").value());
        assertNotNull(wordAnalyzer.analyze("b"));

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("aa");
        assertNotNull(analyzedWord.value());
        assertEquals("b", analyzedWord.value());
    }

    @Test
    public void testResolveValueWithExactMatchAndMultipleWord() {
        wordAnalyzer.analyze("d");
        wordAnalyzer.analyze("bb");
        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("aaaa");

        assertEquals("bb", analyzedWord.value());

        analyzedWord = wordAnalyzer.analyze("aba");
        assertEquals("aaaa", analyzedWord.value());
    }

    @Test
    public void testResolveSingleClosestValueUp() {
        wordAnalyzer.analyze("c");
        wordAnalyzer.analyze("e");
        wordAnalyzer.analyze("f");

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("b");
        assertEquals("c", analyzedWord.value());
    }

    @Test
    public void testResolveSingleClosestValueDown() {
        wordAnalyzer.analyze("a");
        wordAnalyzer.analyze("e");
        wordAnalyzer.analyze("f");

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("b");
        assertEquals("a", analyzedWord.value());
    }

    @Test
    public void testResolveSingleClosestValueAreSameReturnHighest() {
        wordAnalyzer.analyze("a");
        wordAnalyzer.analyze("c");
        wordAnalyzer.analyze("d");

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("b");
        assertEquals("c", analyzedWord.value());
    }

    @Test
    public void testLexicalClosestHigher() {
        wordAnalyzer.analyze("a");
        wordAnalyzer.analyze("e");
        wordAnalyzer.analyze("d");

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("c");
        assertEquals("d", analyzedWord.lexical());
    }

    @Test
    public void testLexicalClosestLower() {
        wordAnalyzer.analyze("a");
        wordAnalyzer.analyze("e");
        wordAnalyzer.analyze("d");

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("b");
        assertEquals("a", analyzedWord.lexical());
    }

    @Test
    public void testLexicalSameValueReturnHighestAscending() {
        wordAnalyzer.analyze("a");
        wordAnalyzer.analyze("e");
        wordAnalyzer.analyze("c");

        AnalyzeResponse analyzedWord = wordAnalyzer.analyze("b");
        assertEquals("a", analyzedWord.lexical());
    }

}
