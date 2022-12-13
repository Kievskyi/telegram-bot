package com.example.demo5bot.service;

import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.messagesender.MessageSender;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Service
public class SendMessageService {

    private final MessageSender messageSender;

    public SendMessageService(@Lazy MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendAnswer(Long chatId, String text) {
        messageSender.sendMessage(SendMessage.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .text(text)
                .build());
    }

    public void sendAnswer(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        messageSender.sendMessage(SendMessage.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build());
    }

    public void sendNotification(List<Long> users) {
        String catEmoji = Emoji.CAT_PRINTS.getEmoji();
        for (Long userId : users) {

            SendMessage messageBuilder = SendMessage.builder()
                    .text("Може трішки повторимо слова? " + catEmoji)
                    .chatId(userId)
                    .build();

            messageSender.sendMessage(messageBuilder);
        }
    }
}