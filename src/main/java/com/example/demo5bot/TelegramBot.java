package com.example.demo5bot;

import com.example.demo5bot.config.BotConfig;
import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.domain.UserSession;
import com.example.demo5bot.service.UserSessionService;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final Dispatcher dispatcher;
    private final UserSessionService sessionService;

    public TelegramBot(BotConfig config, Dispatcher dispatcher, UserSessionService sessionService) {
        this.config = config;
        this.dispatcher = dispatcher;
        this.sessionService = sessionService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage() && message.hasText()) {
            Long userId = message.getChat().getId();
            Long chatId = message.getChatId();

            deleteEmojiFromUserUpdate(update);

            UserSession session = sessionService.getSession(userId);

            UserRequest request = UserRequest.builder()
                    .userSession(session)
                    .update(update)
                    .chatId(chatId)
                    .build();

            boolean dispatched = dispatcher.dispatch(request);

            if (!dispatched) {
                log.error("Error in dispatcher method");
            }
        }
    }

    private void deleteEmojiFromUserUpdate(Update update) {
        update.getMessage().setText(EmojiParser.removeAllEmojis(update.getMessage().getText()));
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}