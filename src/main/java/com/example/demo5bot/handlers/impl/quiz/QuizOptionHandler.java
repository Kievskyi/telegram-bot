package com.example.demo5bot.handlers.impl.quiz;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.Position;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class QuizOptionHandler extends UserRequestHandler {

    private static final String command = "Квіз";
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final KeyboardMarkup keyboardMarkup;

    public QuizOptionHandler(SendMessageService messageService, UserSessionService sessionService, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildQuizMenu();
        String loveCatEmoji = Emoji.LOVE_CAT.getEmoji();

        messageService.sendAnswer(chatId, "Стартуємо? " + loveCatEmoji, keyboard);

        sessionService.getSession(userId).setPosition(Position.QUIZ_MENU);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command) && userRequest.getUserSession().getPosition() == null;
    }
}