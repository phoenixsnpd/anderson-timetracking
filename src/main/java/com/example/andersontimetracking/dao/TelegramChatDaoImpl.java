package com.example.andersontimetracking.dao;

import com.example.andersontimetracking.interfaces.Connector;
import com.example.andersontimetracking.interfaces.TelegramChatDao;
import com.example.andersontimetracking.models.TelegramChat;
import com.example.andersontimetracking.util.ServiceLocator;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelegramChatDaoImpl implements TelegramChatDao {
    private final Connector connector;
    private static final String INSERT_CHAT = "INSERT INTO telegram_chat (chat_id, firstname, username, chat_type) VALUES (?, ?, ?, ?)\n" +
                                              "ON CONFLICT (chat_id) DO NOTHING;";
    private static final String GET_ALL_CHATS = "SELECT * FROM telegram_chat";
    private static final String FIND_BY_USERNAME = "SELECT * FROM telegram_chat WHERE username = ?";

    public TelegramChatDaoImpl() {
        connector = ServiceLocator.getServiceImpl(Connector.class);
    }

    @Override
    @SneakyThrows
    public void insertChat(TelegramChat chat) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHAT)) {

            preparedStatement.setLong(1, chat.getChatId());
            preparedStatement.setString(2, chat.getFirstName());
            preparedStatement.setString(3, chat.getUserName());
            preparedStatement.setString(4, chat.getChatType());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public List<TelegramChat> getAllChats() {
        List<TelegramChat> chats = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CHATS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             while (resultSet.next()){
                 long chatId = resultSet.getLong("chat_id");
                 String firstName = resultSet.getString("firstname");

                 String userName = resultSet.getString("username");
                 String chatType = resultSet.getString("chat_type");
                 LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                 chats.add(new TelegramChat(chatId,firstName, userName, chatType, Optional.ofNullable(createdAt)));


             }

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return  chats;
    }

    @SneakyThrows
    @Override
    public TelegramChat findByUsername(String username) {
        TelegramChat chat = null;

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERNAME)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long chatId = resultSet.getLong("chat_id");
                    String firstName = resultSet.getString("firstname");
                    String userName = resultSet.getString("username");
                    String chatType = resultSet.getString("chat_type");
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

                    chat = new TelegramChat(chatId,firstName, userName, chatType, Optional.ofNullable(createdAt));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return chat;
    }

}
