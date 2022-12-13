package com.example.demo5bot.handlers.impl.deleteWords;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.enums.Emoji;
import com.example.demo5bot.enums.Position;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.service.SendMessageService;
import com.example.demo5bot.service.UserSessionService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

@Component
public class DeleteWordOptionHandler extends UserRequestHandler {

    private static final String command = "Видалити слово";
    private final SendMessageService messageService;
    private final UserSessionService sessionService;

    public DeleteWordOptionHandler(SendMessageService messageService, UserSessionService sessionService) {
        this.messageService = messageService;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        Long chatId = userRequest.getUpdate().getMessage().getChatId();
        String downArrowEmoji = Emoji.DOWN_ARROW.getEmoji();

        sessionService.getSession(userId).setPosition(Position.DELETE_WORD);

        messageService.sendAnswer(chatId, "Напишіть слово на англійскій мові яке Ви хочете видалити " + downArrowEmoji);
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }
}