package com.example.demo5bot.handlers.impl.quiz;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.domain.UserSession;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.Position;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.repository.UserRepository;
import com.example.demo5bot.service.KeyboardMarkup;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.Map;

@Component
public class QuizGameHandler extends UserRequestHandler {

    private static final Position IN_QUIZ = Position.IN_QUIZ;
    private final Position STOP_QUIZ = Position.STOP_QUIZ;
    private final SendMessageService messageService;
    private final UserSessionService sessionService;
    private final UserRepository repository;
    private final KeyboardMarkup keyboardMarkup;

    public QuizGameHandler(SendMessageService messageService, UserSessionService sessionService, UserRepository repository, KeyboardMarkup keyboardMarkup) {
        this.messageService = messageService;
        this.sessionService = sessionService;
        this.repository = repository;
        this.keyboardMarkup = keyboardMarkup;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String usersInput = userRequest.getUpdate().getMessage().getText();
        ReplyKeyboardMarkup keyboard;
        String sadCatEmoji = Emoji.CRYING_CAT.getEmoji();
        String loveCatEmoji = Emoji.LOVE_CAT.getEmoji();
        String sarcasticCatEmoji = Emoji.SARCASTIC_CAT.getEmoji();

        if (usersInput.equals("Закінчити Квіз")) {

            keyboard = keyboardMarkup.buildMainMenu();
            sessionService.getSession(userId).setPosition(null);
            messageService.sendAnswer(chatId, "Що робимо далі? " + sarcasticCatEmoji, keyboard);

        } else {

            keyboard = keyboardMarkup.buildStopQuizMenu();

            if (isRightAnswerOnQuiz(usersInput, sessionService.getSession(userId))) {
                sessionService.getSession(userId).setWordsMap(repository.getQuizWords(userId));
                String nextWord = sessionService.getSession(userId).extractWordFromWordsMap(sessionService.getSession(userId).getWordsMap());
                messageService.sendAnswer(chatId, "Це правильна відповідь! " + loveCatEmoji + "\n" +
                        "Наступне слово - " + nextWord, keyboard);
            } else {
                String nextWord = sessionService.getSession(userId).extractWordFromWordsMap(sessionService.getSession(userId).getWordsMap());

                messageService.sendAnswer(chatId, "Це неправильна відповідь " + sadCatEmoji + "\n" +
                        "Наступне слово - " + nextWord, keyboard);
            }
        }
    }

    private boolean isRightAnswerOnQuiz(String userAnswer, UserSession userSession) {
        String rightAnswer = null;

        for (Map.Entry<String, String> word : userSession.getWordsMap().entrySet()) {
            rightAnswer = word.getKey();
        }

        return rightAnswer.equalsIgnoreCase(userAnswer);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        Position userPosition = userRequest.getUserSession().getPosition();
        return userPosition == IN_QUIZ || userPosition == STOP_QUIZ;
    }
}