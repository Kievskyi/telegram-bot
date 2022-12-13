package com.example.demo5bot.handlers;

import com.example.demo5bot.domain.UserRequest;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public abstract class UserRequestHandler {

    public abstract void handle(UserRequest userRequest);

    public abstract boolean isApplicable(UserRequest userRequest);

    public boolean isCommand(Update update, String command) {
        return  update.getMessage().getText().equals(command);
    }

    public boolean isCommand(Update update, List<String> commands) {
        return update.hasMessage() && update.getMessage().isCommand()
                || commands.contains(update.getMessage().getText());
    }
}