package com.example.analyze.service;

import com.example.analyze.Repo.AnswerRepo;
import com.example.analyze.Repo.QuestionRepo;
import com.example.analyze.entity.Answer;
import com.example.analyze.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalysisService {

    public static String[] noiseWords = {
            "a", "the", "of", "and", "to", "in", "of", "about",
            "is", "am", "are", "that", "this", "these","those",
            "by", "with", "were", "was", "he", "her", "they", "it",
            "or", "she", "I", "can", "could", "be", "some", "from", "if",
            "so", "do", "does", "did", "then", "into", "we", "been",
            "my", "than", "an", "have", "had", "as",  "but", "much", "more",
            "out", "must", "know", "people", "other", "good", "only", "its", "over",
            "think", "us", "him", "his", "her", "their", "back", "just", "up", "down",
            "go", "went", "take", "toke", "put", "get", "got", "by", "because","at",
            "say", "said", "any", "on", "nor", "not", "im", "don't", "can't", "couldn't",
            "should", "shouldn't", "didn't", "wasn't", "weren't", "would", "me", "there",
            "very", "ago", "year", "one", "for", "no", "day", "what", "which", "how", "where",
            "who", "whom", "left", "right", "every", "most", "like", "one", "two", "i", "make",
            "really", "made", "has", "nothing", "also", "will", "them", "took", "when", "open",
            "alone", "hey", "going", "outside", "inside", "being", "someone", "somebody", "due",
            "actually", "may", "might", "possible", "you", "too", "after", "before", "getting"
    };
    public static Set<String> noiseSet = new HashSet<>(Arrays.asList(
            noiseWords
    ));


    @Autowired
    AnswerRepo answerRepo;
    @Autowired
    QuestionRepo questionRepo;

    @Scheduled(fixedRate = 100000)
    public void simplifiedAnswer(){
        List<Answer> answers = answerRepo.findAll();

        for (Answer answer: answers) {
            HashMap<String, Integer> wordsCount = new HashMap<>();
            List<String> words = getContentArray(answer.getContent() + " " + answer.getQuestion().getName());
            for (String word : words) {
                if (word.equals("") || word.length()<=2) continue;
                if (wordsCount.containsKey(word)) {
                    int count = wordsCount.get(word) + 1;
                    wordsCount.put(word, count);
                } else {
                    wordsCount.put(word, 1);
                }
            }
            wordsCount = sortMap(wordsCount);
            List<String> keyList = new ArrayList<>(wordsCount.keySet());
            StringBuilder topTenWords = new StringBuilder().append("{");
            for (int i = 0; i < 15 && i < keyList.size(); i++) {
                topTenWords.append("\"")
                        .append(keyList.get(keyList.size() - 1 - i))
                        .append("\"")
                        .append(": ")
                        .append(wordsCount.get(keyList.get(keyList.size() - 1 - i)))
                        .append(",");
            }
            topTenWords.replace(topTenWords.length() - 1, topTenWords.length(), "}");
            answer.setTopTenWords(topTenWords.toString());
        }

        answerRepo.saveAll(answers);

    }

    public List<String> getContentArray(String content) {
        List<String> contentList = new ArrayList<>();
        String[] contentWords = content.split("\\.|,|\\s");
        for (String word: contentWords) {
            word = word.trim().replaceAll("[^abcdefghijklmnopqrstuvwxyz]", "").toLowerCase();
            if (!noiseSet.contains(word))
                contentList.add(word);
        }
        return contentList;
    }

    public static HashMap<String, Integer> sortMap(HashMap<String, Integer> map) {
         return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
    }

    public void setQuestionKeywords() {
        List<Question> questions = questionRepo.findAll();

        for (Question question: questions) {
            Set<String> questionToken = new HashSet<>();
            String[] nameTokens = question.getName().split("\\s|,|\\.");
            for (String token : nameTokens) {
                token = token.toLowerCase().replaceAll("[^abcdefghijklmnopqrstuvwxyz]", "");
                if (token.length() > 2 && !noiseSet.contains(token)) {
                    questionToken.add(token);
                }
            }
            String keyWord = String.join(",", questionToken);
            question.setKeywords(keyWord);
        }

        questionRepo.saveAll(questions);
    }
}
