package com.example.demo5bot.domain;

import com.example.demo5bot.enums.Emoji;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Data
public class Commands {
    private List<String> commands;

    public Commands() {
        commands = new ArrayList<>() {{
            add("Добавити слово");
            add("Показати всі слова");
            add("З перекладом");
            add("Без перекладу");
            add("Видалити слово");
            add("Квіз");
            add("Розпочати Quiz");
            add("Закінчити Quiz");
        }};
    }
}