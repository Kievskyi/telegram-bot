package com.example.demo5bot.repository;

import com.example.demo5bot.enums.MessageStatus;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    void saveNewWord(long userId, String englishWord, String translationWord);

    boolean deleteWord(long userId, String englishWord);

    String getAllWords(long userId, MessageStatus messageStatus);

    boolean isUserExist(long userId);

    void createNewUser(long userId, String name);

    List<Long> getAllUsers();

    Map<String, String> getQuizWords(long userId);

}