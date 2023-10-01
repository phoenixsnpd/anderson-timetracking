package com.example.andersontimetracking.services;

import com.example.andersontimetracking.dto.TelegramChatDTO;
import com.example.andersontimetracking.interfaces.TelegramChatDao;
import com.example.andersontimetracking.models.TelegramChat;
import com.example.andersontimetracking.util.LocalDateTimeDeserializer;
import com.example.andersontimetracking.util.ServiceLocator;
import com.google.gson.*;
import lombok.SneakyThrows;
import okhttp3.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class CustomTelegramBot {

    private final ReportProcessor reportProcessor;

    private final String SEND_DOCUMENT = "/sendDocument" ;
    private final Map<String, String> updatesMap = new HashMap<>();
    private  List<TelegramChat> chats;
    private final String TOKEN = "6527957018:AAHUKDeZ9-4EBjBLwdzhtlGJn468ZDcHQ4Y";
    private final String URL_API = "https://api.telegram.org/bot";
    private final String SEND_MESSAGE = "/sendMessage";
    private final String GET_UPDATE = "/getUpdates";
    private final OkHttpClient httpClient;
    private final TelegramChatDao telegramChatDao;

//    "ok":true,"result":[{"update_id":168501281,
//            "message":{"message_id":4,"from":{"id":5502129188,"is_bot":false,"first_name":"None","username":"riversin","language_code":"en"},
//        "chat":{"id":5502129188,"first_name":"None","username":"riversin","type":"private"},
//        "date":1696020919,"text":"/start","entities":[{"offset":0,"length":6,"type":"bot_command"}]}}]}


    public CustomTelegramBot() {   //String token contrutctor maybe ?
        //TOKEN = token;
        httpClient = new OkHttpClient();
        telegramChatDao = ServiceLocator.getServiceImpl(TelegramChatDao.class);
        chats = telegramChatDao.getAllChats();
        reportProcessor = new ReportProcessor();
    }

    @SneakyThrows
    public void getUpdates(int offset) {
        Request request = new Request.Builder()
                .url(URL_API + TOKEN + GET_UPDATE + "?offset=" + offset)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
        }
    }
    @SneakyThrows
    public void sendPDF(String chatId, byte[] pdfData) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("chat_id", chatId)
                .addFormDataPart("document", "Report.pdf",
                        RequestBody.create(MediaType.parse("application/pdf"), pdfData))
                .build();

        Request request = new Request.Builder()
                .url(URL_API + TOKEN + SEND_DOCUMENT)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }

    @SneakyThrows
    public void sendMessage(String chatId, String text) {
        RequestBody formBody = new FormBody.Builder()
                .add("chat_id", chatId)
                .add("text", text)
                .build();

        Request request = new Request.Builder()
                .url(URL_API + TOKEN + SEND_MESSAGE)
                .post(formBody)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            System.out.println(response.body().string());

        }
    }

    public void sendMessageToAll() {

        // Send EveryOne In Chat
        // Need to be in Scheduler
        for (TelegramChat chat : chats) {
            String chatId = String.valueOf(chat.getChatId());
            sendMessage(chatId, "Your Chat id is" + chatId + "And username is " + chat.getUserName());
        }
    }

    public void sendPdfToAll(){
        byte[] pdfDoc = getPdfAsByteArray();
        for (TelegramChat chat : chats) {
            sendPDF(String.valueOf(chat.getChatId()),pdfDoc);
        }
    }

    @SneakyThrows
    public void startLongPolling() {
        int[] lastUpdateIdWrapper = {0};  // Для хранения ID последнего обновления

        while (true) {
            Request request = new Request.Builder()
                    .url(URL_API + TOKEN + GET_UPDATE + "?offset=" + (lastUpdateIdWrapper[0] + 1))
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                assert response.body() != null;
                String responseBody = response.body().string();

                List<TelegramChatDTO> replyChats = findLastUpdates(responseBody, lastUpdateIdWrapper);

                for (TelegramChatDTO chat : replyChats) {
                    sendMessage(String.valueOf(chat.getChatId()), String.format("Hello Mr. %s, I Am just a simple bot, I can't process your request... But I will send you " +
                                                                                "Report from Orange team in PDF format at 23:00 UTC+3. Thanks for your patience.", chat.getUserName()));
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    private byte[] getPdfAsByteArray(){
        PDDocument doc = reportProcessor.generateReport();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        doc.save(baos);
        doc.close();
        return baos.toByteArray();
    }


    private List<TelegramChatDTO> findLastUpdates(String jsonString, int[] lastUpdateIdWrapper) {
        List<TelegramChatDTO> needReply = new ArrayList<>();
        processChats(jsonString, lastUpdateIdWrapper, needReply);
        return needReply;
    }

    private void processChats(String jsonString, int[] lastUpdateIdWrapper, List<TelegramChatDTO> needReply) {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray results = jsonObject.getAsJsonArray("result");
        //Find if this chat is new and add it to DB and InMemory List
        findNewChats(results, lastUpdateIdWrapper);
        //This Plase important
        for (JsonElement result : results) {
            JsonObject resultObject = result.getAsJsonObject();
            String updateId = resultObject.get("update_id").getAsString();

            if (Integer.parseInt(updateId) > lastUpdateIdWrapper[0]) {
                lastUpdateIdWrapper[0] = Integer.parseInt(updateId);
            }
            String chatId = resultObject
                    .getAsJsonObject("message")
                    .getAsJsonObject("chat")
                    .get("id").getAsString();

            String userName = resultObject
                    .getAsJsonObject("message")
                    .getAsJsonObject("chat")
                    .get("username").getAsString();

            TelegramChatDTO chatDTO = new TelegramChatDTO(
                    Integer.parseInt(updateId),
                    Long.parseLong(chatId),
                    userName);
            String currentUpdateId = updatesMap.get(chatId);

            if (currentUpdateId != null) {
                if (!currentUpdateId.equals(updateId)) {
                    needReply.add(chatDTO);
                }
            } else {
                // Если chatId еще не было в мапе, добавляем
                needReply.add(chatDTO);
            }
            // Обновляем или добавляем пару chatId, updateId в мап
            updatesMap.put(chatId, updateId);
        }
    }

    private void parseChatString(String chatString) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        TelegramChat chat = gson.fromJson(chatString, TelegramChat.class);
        if (chats.stream().anyMatch(c -> c.getChatId() == chat.getChatId())) {
            return;
        }
        chats.add(chat);
        telegramChatDao.insertChat(chat);
    }

    private void findNewChats(JsonArray results, int[] lastUpdateIdWrapper) {

        for (JsonElement result : results) {
            JsonObject resultObject = result.getAsJsonObject();
            String updateId = resultObject.get("update_id").getAsString();

            if (Integer.parseInt(updateId) > lastUpdateIdWrapper[0]) {
                lastUpdateIdWrapper[0] = Integer.parseInt(updateId);
            }
            String chat = resultObject
                    .getAsJsonObject("message")
                    .getAsJsonObject("chat")
                    .toString();

            parseChatString(chat);
        }

    }
}
