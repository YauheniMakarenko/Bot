package com.Bot;

import com.Bot.Commands.RemoteControlAdmin;
import com.Bot.Movies.Movies;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.util.HashMap;
import java.util.List;


public class Bot extends TelegramLongPollingBot implements IBot {

    private final String botUserName = "@DATT_Bot";
    private final String token = "899355466:AAHzvUzhXMIdb87WmVkLy1rJioFhmaBS_Ws";

    private Regex regex = new Regex();
    private Movies movies = new Movies();
    private Keyboard keyboard = Keyboard.getInstance();
    private static Bot instance;
    private HashMap<Long, String> map = new HashMap<>();


    private Bot() {
    }

    public static Bot getInstance() {
        if (instance == null) {
            instance = new Bot();
        }
        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            workWithMessages(update);
            map.put(update.getMessage().getChatId(), update.getMessage().getText());
        } else if (update.hasCallbackQuery()) {
            workWithCallbackQuery(update);
        }
    }

    public void workWithMessages(Update update) {
        Message message = update.getMessage();
        long chatId = update.getMessage().getChatId();

        if (regex.finder(message.getText(), "Weather [A-zА-Я].+")) {

            RemoteControlAdmin.findCommand("Weather").action(update);

        } else if (message.getText().equals("Music")
                || message.getText().equals("/start")
                || message.getText().equals("Movies")) {

            RemoteControlAdmin.findCommand(message.getText()).action(update);
        } else {
            if (regex.finder(message.getText(), "\\d+")) {

                String value = map.get(chatId);
                if (value != null) {
                    if (value.equals("Movies") && !(map.isEmpty())) {
                        sendMsg(chatId, movies.getMovie(message));
                    }
                }else{
                    sendMsg(chatId, "Введите кнопку Movies");
                }

            } else if (regex.finder(message.getText(), ".+")) {
                RemoteControlAdmin.findCommand("SearchMusic").action(update);
            }
        }
    }

    public void workWithCallbackQuery(Update update) {
        String text = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (text.equals("\u27A1") || text.equals("\u2B05")
                || text.equals("TopMusic") || text.equals("SearchMusic")) {
            RemoteControlAdmin.findCommand(text).action(update);
        } else {
            sendAudioMessage(chatId, text);
        }
    }

    @Override
    public void sendMsg(long chatId, String text) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text);
        try {
            keyboard.setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAudioMessage(long chatId, String givenStringJson) {
        SendAudio sendAudio = new SendAudio()
                .setChatId(chatId)
                .setAudio("https://zaycev.net" + givenStringJson);
        try {
            sendAudio(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public String getInfoMovies() {
        String info = "";
        int number = 0;
        List<String> listString = movies.getEvent();
        for (int i = 0; i < listString.size(); i++) {
            info += (number + ". " + listString.get(i) + "\n");
            number++;
        }
        String tmp = "Выберите номер фильма который вам понравился".toUpperCase();
        info += tmp;

        return info;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
