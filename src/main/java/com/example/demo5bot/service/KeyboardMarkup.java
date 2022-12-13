package com.example.demo5bot.service;

import com.example.demo5bot.enums.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardMarkup {
    private final String crossEmoji = Emoji.CROSS.getEmoji();
    private final String plusEmoji = Emoji.PLUS.getEmoji();
    private final String brainEmoji = Emoji.BRAIN.getEmoji();
    private final String clipboardEmoji = Emoji.CLIPBOARD.getEmoji();
    private final String yellowCircleEmoji = Emoji.YELLOW_CIRCLE.getEmoji();
    private final String redCircleEmoji = Emoji.RED_CIRCLE.getEmoji();
    private final String rocketEmoji = Emoji.ROCKET.getEmoji();

    public ReplyKeyboardMarkup buildMainMenu() {


        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add(plusEmoji + "Добавити слово");
        row1.add("Видалити слово" + crossEmoji);
        row2.add(clipboardEmoji + "Показати всі слова");
        row2.add("Квіз" + brainEmoji);
        rowList.add(row1);
        rowList.add(row2);

        return ReplyKeyboardMarkup.builder()
                .keyboard(rowList)
                .resizeKeyboard(true)
                .build();
    }

    public ReplyKeyboardMarkup buildGetAllWordsMenu() {
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add(yellowCircleEmoji + "З перекладом");
        row.add("Без перекладу" + redCircleEmoji);
        rowList.add(row);

        return ReplyKeyboardMarkup.builder()
                .keyboard(rowList)
                .resizeKeyboard(true)
                .build();
    }

    public ReplyKeyboardMarkup buildQuizMenu() {
        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        row.add(rocketEmoji + "Розпочати Квіз");
        row.add("Закінчити Квіз" + crossEmoji);
        rowList.add(row);

        return ReplyKeyboardMarkup.builder()
                .keyboard(rowList)
                .resizeKeyboard(true)
                .build();
    }

    public ReplyKeyboardMarkup buildStopQuizMenu() {
        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        row.add("Закінчити Квіз" + crossEmoji);
        rowList.add(row);

        return ReplyKeyboardMarkup.builder()
                .keyboard(rowList)
                .resizeKeyboard(true)
                .build();
    }
}