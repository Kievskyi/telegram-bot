package com.example.demo5bot.handlers.impl;

import com.example.demo5bot.domain.Commands;
import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class UnexpectedCommandHandler extends UserRequestHandler {

    private final SendMessageService messageService;
    private final KeyboardMarkup keyboardMarkup;
    private final UserSessionService sessionService;

    public UnexpectedCommandHandler(SendMessageService messageService, KeyboardMarkup keyboardMarkup, UserSessionService sessionService) {
        this.messageService = messageService;
        this.keyboardMarkup = keyboardMarkup;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildMainMenu();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String sadCatEmoji = Emoji.CRYING_CAT.getEmoji();

        messageService.sendAnswer(chatId, "Нажаль, я не знаю такої команди " + sadCatEmoji, keyboard);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        Commands commands = new Commands();

        return !isCommand(userRequest.getUpdate(), commands.getCommands()) && userRequest.getUserSession().getPosition() == null;
    }
}