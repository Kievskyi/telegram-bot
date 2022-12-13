package com.example.demo5bot.handlers.impl;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.repository.UserRepository;
import com.example.demo5bot.repository.impl.UserRepositoryMySqlImpl;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class StartCommandHandler extends UserRequestHandler {

    private static final String command = "/start";
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final KeyboardMarkup keyboardMarkup;


    public StartCommandHandler(SendMessageService messageService, UserSessionService sessionService, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String userName = userRequest.getUpdate().getMessage().getChat().getFirstName();
        String loveCatEmoji = Emoji.LOVE_CAT.getEmoji();

        userRequest.getUserSession().setPosition(null);

        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildMainMenu();

        messageService.sendAnswer(chatId, "Привіт, " + userName + "!" + loveCatEmoji, keyboard);

        sessionService.saveSession(userId, userRequest.getUserSession());
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }
}