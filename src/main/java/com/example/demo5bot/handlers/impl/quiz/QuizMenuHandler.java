package com.example.demo5bot.handlers.impl.quiz;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.domain.UserSession;
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
public class QuizMenuHandler extends UserRequestHandler {

    private static final Position POSITION = Position.QUIZ_MENU;
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final UserRepository repository;
    private final KeyboardMarkup keyboardMarkup;

    public QuizMenuHandler(SendMessageService messageService, UserSessionService sessionService, UserRepositoryMySqlImpl repository, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.repository = repository;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String userChoice = userRequest.getUpdate().getMessage().getText();
        ReplyKeyboardMarkup keyboard;
        String sarcasticCatEmoji = Emoji.SARCASTIC_CAT.getEmoji();

        if (userChoice.equals("Розпочати Квіз")) {
            UserSession session = sessionService.getSession(userId);
            keyboard = keyboardMarkup.buildStopQuizMenu();
            session.setPosition(Position.IN_QUIZ);
            session.setWordsMap(repository.getQuizWords(userId));
            sessionService.saveSession(userId, session);

            messageService.sendAnswer(chatId, "Напиши переклад цього слова - " + sessionService.getSession(userId)
                    .extractWordFromWordsMap(sessionService.getSession(userId).getWordsMap()), keyboard);
        } else if (userChoice.equals("Закінчити Квіз")) {
            keyboard = keyboardMarkup.buildMainMenu();
            sessionService.getSession(userId).setPosition(null);
            messageService.sendAnswer(chatId, "Що робимо далі? " + sarcasticCatEmoji, keyboard);
        }
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        Position userPosition = userRequest.getUserSession().getPosition();
        return userPosition == POSITION || userRequest.getUpdate().getMessage().getText().equals("Закінчити Quiz");
    }
}