package com.example.demo5bot.domain;

import com.example.demo5bot.enums.Answer;
import com.example.demo5bot.enums.Position;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Builder
public class UserSession {
    private Long userId;
    private String word;
    private Map<String, String> wordsMap;
    private String translation;
    private Position position;
    private Answer answer;

    public String extractWordFromWordsMap(Map<String, String> mapWithWords) {
        String word = null;

        for (Map.Entry<String, String> map : mapWithWords.entrySet()) {
            word = "<b>" + map.getValue() + "</b>";
        }
        return word;
    }
}