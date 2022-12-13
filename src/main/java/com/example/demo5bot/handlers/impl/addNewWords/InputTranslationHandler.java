package com.example.demo5bot.handlers.impl.addNewWords;

import com.example.demo5bot.domain.Commands;
import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.Position;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.repository.UserRepository;
import com.example.demo5bot.repository.impl.UserRepositoryMySqlImpl;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class InputTranslationHandler extends UserRequestHandler {

    private static final Position POSITION = Position.ENTER_TRANSLATION;
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final KeyboardMarkup keyboardMarkup;
    private final UserRepository repository;

    public InputTranslationHandler(SendMessageService messageService, UserSessionService sessionService, UserRepositoryMySqlImpl repository, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.repository = repository;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String englishWord = sessionService.getSession(userId).getWord();
        String translationWord = userRequest.getUpdate().getMessage().getText();
        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildMainMenu();
        String catEmoji = Emoji.SARCASTIC_CAT.getEmoji();

        repository.saveNewWord(userId, englishWord, translationWord);

        messageService.sendAnswer(chatId, "Я зберіг це слово " + catEmoji, keyboard);

        sessionService.getSession(userId).setPosition(null);
        sessionService.getSession(userId).setWord(null);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        Position userPosition = userRequest.getUserSession().getPosition();
        Commands commands = new Commands();
        return userPosition == POSITION && !isCommand(userRequest.getUpdate(), commands.getCommands());
    }
}