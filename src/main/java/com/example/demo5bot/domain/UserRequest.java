package com.example.demo5bot.domain;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Builder
public class UserRequest {
    private Update update;
    private Long chatId;
    private UserSession userSession;
}