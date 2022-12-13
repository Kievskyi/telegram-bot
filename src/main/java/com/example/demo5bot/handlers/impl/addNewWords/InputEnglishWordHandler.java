package com.example.demo5bot.handlers.impl.addNewWords;

import com.example.demo5bot.domain.Commands;
import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.Position;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;

@Component
public class InputEnglishWordHandler extends UserRequestHandler {

    private static final Position POSITION = Position.ENTER_WORD;
    private final SendMessageService messageService;
    private final UserSessionService sessionService;

    public InputEnglishWordHandler(SendMessageService messageService, UserSessionService sessionService) {
        this.messageService = messageService;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String wordInEnglish = userRequest.getUpdate().getMessage().getText();
        String downArrowEmoji = Emoji.DOWN_ARROW.getEmoji();

        sessionService.getSession(userId).setWord(wordInEnglish);

        messageService.sendAnswer(chatId, "А тепер напиши переклад цього слова  " + "<b>" + wordInEnglish + "</b> " + downArrowEmoji);

        sessionService.getSession(userId).setPosition(Position.ENTER_TRANSLATION);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        Position userPosition = userRequest.getUserSession().getPosition();
        Commands commands = new Commands();
        return userPosition == POSITION && !isCommand(userRequest.getUpdate(), commands.getCommands());
    }
}