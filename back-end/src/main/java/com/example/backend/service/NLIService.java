package com.example.backend.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class NLIService {
    public static String[] noiseWords = {
            "a", "the", "of", "and", "to", "in", "of", "about",
            "is", "am", "are", "that", "this", "these","those",
            "by", "with", "were", "was", "he", "her", "they", "it",
            "or", "she", "I", "can", "could", "be", "some", "from", "if",
            "so", "do", "does", "did", "then", "into", "we", "been",
            "my", "than", "an", "have", "had", "as",  "but", "much", "more"
    };
    public static Set<String> noiseSet = new HashSet<>(Arrays.asList(
            noiseWords
    ));

    public Set<String> getSearchTokens(String searchString) {
        String[] words = searchString.split("\\s");
        Set<String> tokens = new HashSet<>();
        for (String word: words) {
            if (!noiseSet.contains(word))
                tokens.add(word);
        }
        return tokens;
    }
    public String getRegex(Set<String> tokens) {
        StringBuilder sql = new StringBuilder();
        StringBuilder reg = new StringBuilder();
        for (String token: tokens) {
           reg.append("(").append(token).append(")|");
        }
        reg.replace(reg.length()-1,reg.length(), "");

        return reg.toString();
    }
}
