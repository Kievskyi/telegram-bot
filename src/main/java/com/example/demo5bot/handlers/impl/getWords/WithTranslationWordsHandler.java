package com.example.demo5bot.handlers.impl.getWords;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.MessageStatus;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.repository.UserRepository;
import com.example.demo5bot.repository.impl.UserRepositoryMySqlImpl;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class WithTranslationWordsHandler extends UserRequestHandler {

    private static final String command = "З перекладом";
    private final SendMessageService messageService;
    private final UserRepository repository;
    private final KeyboardMarkup keyboardMarkup;

    public WithTranslationWordsHandler(SendMessageService messageService, UserRepositoryMySqlImpl repository, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.repository = repository;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildMainMenu();

        String words = repository.getAllWords(userId, MessageStatus.GET_ALL_WORDS_WITH_TRANSLATION);

        messageService.sendAnswer(chatId, words, keyboard);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }
}