package com.example.andersontimetracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramChatDTO {
    private int updateId;
    private long chatId;
    private String userName;
}