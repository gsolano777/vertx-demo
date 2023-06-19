package com.gabosol777.demo.service;

import com.gabosol777.demo.domain.AnalyzeResponse;
import com.gabosol777.demo.utils.WordUtils;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WordAnalyzer {
    private Map<Integer, SortedList<String>> wordsAndValuesMap = new HashMap<>();
    private SortedList<Integer> sortedTotalCharacterValues = new SortedList<>();
    private SortedList<String> lexicalSortedWords = new SortedList<>();

    private Set<String> words = new HashSet<>();

    public AnalyzeResponse analyze(String wordText) {
        String wordValue = wordText.toLowerCase();
        Integer wordKey = WordUtils.calculateTotalValue(wordValue);

        add(wordValue, wordKey);

        Optional<String> resolvedValue = resolveValue(wordValue, wordKey);
        Optional<String> resolvedLexical = resolveLexical(wordValue);

        return new AnalyzeResponse(
            resolvedValue.isPresent() ? resolvedValue.get() : null,
            resolvedLexical.isPresent() ? resolvedLexical.get() : null
        );
    }

    public void loadWord(String word) {
        Integer wordKey = WordUtils.calculateTotalValue(word);
        add(word, wordKey);
    }

    private Optional<String> resolveValue(String wordValue, Integer wordKey) {
        Optional<String> foundMatch = Optional.empty();

        if (wordsAndValuesMap.containsKey(wordKey)) { // Exact match.
            foundMatch = findWord(wordKey, wordValue);
        }
        if(foundMatch.isEmpty()) {
            foundMatch = findClosestMatch(wordValue, wordKey);
        }
        return foundMatch;
    }

    private Optional<String> resolveLexical(String word) {
        int wordIndex = lexicalSortedWords.findIndexOf(word);
        int decreasingIndex = wordIndex - 1;
        int increasingIndex = wordIndex + 1;

        Optional<String> foundMatch = Optional.empty();

        if (decreasingIndex >= 0 && increasingIndex < lexicalSortedWords.size()) {
            String wordBelow = lexicalSortedWords.get(decreasingIndex);
            String wordAbove = lexicalSortedWords.get(increasingIndex);
            int compareBelow = Math.abs(wordBelow.compareTo(word));
            int compareAbove = Math.abs(wordAbove.compareTo(word));

            if(compareAbove < compareBelow) {
                foundMatch = Optional.of(wordAbove);
            } else {
                foundMatch = Optional.of(wordBelow);
            }
        } else if (increasingIndex >= lexicalSortedWords.size() && decreasingIndex >= 0) {
            foundMatch = Optional.of(lexicalSortedWords.get(decreasingIndex));
        } else if (decreasingIndex < 0 && increasingIndex < lexicalSortedWords.size()) {
            foundMatch = Optional.of(lexicalSortedWords.get(increasingIndex));
        }
        return foundMatch;
    }

    private Optional<String> findClosestMatch(String wordValue, Integer wordKey) {
        int wordIndex = sortedTotalCharacterValues.findIndexOf(wordKey);
        int decreasingIndex = wordIndex - 1;
        int increasingIndex = wordIndex + 1;
        Optional<String> foundMatch = Optional.empty();

        if (decreasingIndex >= 0 && increasingIndex < sortedTotalCharacterValues.size()) {
            int belowValue = sortedTotalCharacterValues.get(decreasingIndex);
            int upperValue = sortedTotalCharacterValues.get(increasingIndex);
            int belowValueDiff = wordKey - belowValue;
            int upperValueDiff = upperValue - wordKey;

            if(belowValueDiff < upperValueDiff) {
                foundMatch = findWord(sortedTotalCharacterValues.get(decreasingIndex), wordValue);
            } else {
                foundMatch = findWord(sortedTotalCharacterValues.get(increasingIndex), wordValue);
            }
        } else if (increasingIndex >= sortedTotalCharacterValues.size() && decreasingIndex >= 0) {
            foundMatch = findWord(sortedTotalCharacterValues.get(decreasingIndex), wordValue);
        } else if (decreasingIndex < 0 && increasingIndex < sortedTotalCharacterValues.size()) {
            foundMatch = findWord(sortedTotalCharacterValues.get(increasingIndex), wordValue);
        }
        return foundMatch;
    }

    private Optional<String> findWord(Integer wordKey, String wordValue) {
        if(wordsAndValuesMap.get(wordKey).size() == 2) {
            if(!wordsAndValuesMap.get(wordKey).get(0).equals(wordValue)) {
                return Optional.of(wordsAndValuesMap.get(wordKey).get(0));
            }
            return Optional.of(wordsAndValuesMap.get(wordKey).get(1));
        } else {
            return wordsAndValuesMap.get(wordKey).stream()
                .filter(w -> !w.equals(wordValue))
                .findFirst();
        }
    }

    private void add(String wordText, Integer wordKey) {
        if(!words.contains(wordText)) {
            words.add(wordText);
            lexicalSortedWords.add(wordText);

            if (!wordsAndValuesMap.containsKey(wordKey)) {
                sortedTotalCharacterValues.add(wordKey);
                wordsAndValuesMap.put(wordKey, new SortedList<>());
            }
            wordsAndValuesMap.get(wordKey).add(wordText);
        }
    }
}
