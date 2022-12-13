package com.example.demo5bot.handlers.impl.deleteWords;

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
public class DeleteInputWordHandler extends UserRequestHandler {

    private static final Position POSITION = Position.DELETE_WORD;
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final UserRepository repository;
    private final KeyboardMarkup keyboardMarkup;

    public DeleteInputWordHandler(SendMessageService messageService, UserSessionService sessionService, UserRepositoryMySqlImpl repository, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.repository = repository;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String word = userRequest.getUpdate().getMessage().getText();
        ReplyKeyboardMarkup keyboard = keyboardMarkup.buildMainMenu();
        String sadCatEmoji = Emoji.CRYING_CAT.getEmoji();
        String sarcasticCatEmoji = Emoji.SARCASTIC_CAT.getEmoji();

        boolean isDeleted = repository.deleteWord(userId, word);

        if (isDeleted) {
            messageService.sendAnswer(chatId, "Я видалив це слово " + sarcasticCatEmoji, keyboard);
        } else {
            messageService.sendAnswer(chatId, "Я не знайшов такого слова " + sadCatEmoji, keyboard);
        }

        sessionService.getSession(userId).setPosition(null);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        Position userPosition = userRequest.getUserSession().getPosition();
        Commands commands = new Commands();
        return userPosition == POSITION && !isCommand(userRequest.getUpdate(), commands.getCommands());
    }
}