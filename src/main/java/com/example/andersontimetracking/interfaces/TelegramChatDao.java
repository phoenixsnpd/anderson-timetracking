package com.example.andersontimetracking.interfaces;

import com.example.andersontimetracking.models.TelegramChat;

import java.util.List;

public interface TelegramChatDao {
    void insertChat(TelegramChat chat);
    List<TelegramChat> getAllChats();
    TelegramChat findByUsername(String username);

}
