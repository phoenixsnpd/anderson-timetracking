package com.example.andersontimetracking.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramChat {
    @SerializedName("id")
    private long chatId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("username")
    private String userName;

    @SerializedName("type")
    private String chatType;
    private Optional<LocalDateTime> createdAt;
}
