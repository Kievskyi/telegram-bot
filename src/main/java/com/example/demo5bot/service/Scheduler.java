package com.example.demo5bot.service;

import com.example.demo5bot.repository.UserRepository;
import com.example.demo5bot.repository.impl.UserRepositoryMySqlImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class Scheduler {
    private final UserRepository userRepository;
    private final SendMessageService messageService;

    @Autowired
    public Scheduler(@Lazy SendMessageService messageService) {
        this.userRepository = new UserRepositoryMySqlImpl();
        this.messageService = messageService;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendRemind() {
        List<Long> users = userRepository.getAllUsers();
        messageService.sendNotification(users);
    }
}