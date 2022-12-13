package com.example.demo5bot;

import com.example.demo5bot.domain.UserRequest;
import com.example.demo5bot.domain.UserSession;
import com.example.demo5bot.handlers.UserRequestHandler;
import com.example.demo5bot.repository.UserRepository;
import com.example.demo5bot.repository.impl.UserRepositoryMySqlImpl;
import com.example.demo5bot.service.UserSessionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Dispatcher {

    private final List<UserRequestHandler> handlers;
    private final UserRepository repository;
    private final UserSessionService sessionService;

    public Dispatcher(List<UserRequestHandler> handlers, UserRepositoryMySqlImpl repository, UserSessionService sessionService) {
        this.handlers = handlers;
        this.repository = repository;
        this.sessionService = sessionService;
    }

    public boolean dispatch(UserRequest userRequest) {
        Long userId = userRequest.getUpdate().getMessage().getChat().getId();
        String userName = userRequest.getUpdate().getMessage().getChat().getFirstName();

        if (!repository.isUserExist(userId)) {
            repository.createNewUser(userId, userName);
        }

        if (!sessionService.isSessionExist(userId)) {
            sessionService.saveSession(userId, UserSession.builder()
                    .userId(userId)
                    .build());
        }

        for (UserRequestHandler userRequestHandler : handlers) {
            if (userRequestHandler.isApplicable(userRequest)) {
                userRequestHandler.handle(userRequest);
                return true;
            }
        }
        return false;
    }
}