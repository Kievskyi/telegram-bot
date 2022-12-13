package com.example.demo5bot.handlers.impl.addNewWords;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.Position;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;

@Component
public class AddNewWordOptionHandler extends UserRequestHandler {

    private static final String command = "Добавити слово";
    private final SendMessageService messageService;
    private final UserSessionService sessionService;

    public AddNewWordOptionHandler(SendMessageService messageService, UserSessionService sessionService) {
        this.messageService = messageService;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String downArrowEmoji = Emoji.DOWN_ARROW.getEmoji();

        sessionService.getSession(userId).setPosition(Position.ENTER_WORD);

        messageService.sendAnswer(chatId, "Напиши слово на англійській мові " + downArrowEmoji);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }
}