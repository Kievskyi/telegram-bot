package com.example.demo5bot.handlers.impl.getWords;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class GetAllWordsOptionHandler extends UserRequestHandler {

    private static final String command = "Показати всі слова";
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final KeyboardMarkup keyboardMarkup;

    public GetAllWordsOptionHandler(SendMessageService messageService, UserSessionService sessionService, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String downArrowEmoji = Emoji.DOWN_ARROW.getEmoji();

        sessionService.getSession(userId).setPosition(null);

        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildGetAllWordsMenu();

        messageService.sendAnswer(chatId, "Обери один з варіантів нижче " + downArrowEmoji, keyboard);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }
}