package com.example.demo5bot.service;

import com.example.demo5bot.domain.UserSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserSessionService {

    private final Map<Long, UserSession> userSessionMap = new HashMap<>();

    public UserSession getSession(Long userId) {
        return userSessionMap.getOrDefault(userId, UserSession
                .builder()
                .userId(userId)
                .build());
    }

    public UserSession saveSession(Long userId, UserSession userSession) {
        return userSessionMap.put(userId, userSession);
    }

    public boolean isSessionExist(Long userId) {
        return userSessionMap.get(userId) != null;
    }
}