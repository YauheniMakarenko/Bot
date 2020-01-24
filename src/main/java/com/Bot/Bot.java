package com.Bot;

import com.Bot.Movies.Movies;
import com.Bot.Weather.Model;
import com.Bot.Weather.Weather;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private Model model = new Model();
    private Movies movies = new Movies();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final Message message = update.getMessage();
            final long chatId = update.getMessage().getChatId();
            switch (message.getText()) {
                case "/Weather":
                    sendMsg(chatId, "Введите город!");
                    /*try{
                        sendMsg(chatId, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(chatId, "Город не найден!");
                    }*/
                    break;
                case "/Music":
                    sendMsg(chatId, "чем могу помочь?");
                    break;
                case "/setting":
                    sendMsg(chatId, "что будем настраивать?");
                    break;
                case "/Movies":
                    sendMsg(chatId, getInfoMovies());

                    //sendMsg(chatId, getMovie(message));       //message цифра из списка
                    break;
                default:
                    //sendMsg(chatId, "Список команд:\n /Weather\n /Music\n /setting\n /Movies");
                    /*try{
                        sendMsg(chat_id, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(chat_id, "Город не найден!");
                    }*/
                    sendMsg(chatId, getMovie(message));
            }
        }
    }

    public synchronized void sendMsg(long chatId, String text) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getMovie(Message message){
        String infoMovie = movies.getMovie(message);
        return infoMovie;
    }

    public String getInfoMovies() {
        String info = "";
        int number = 1;
        List<String> listString = movies.getEvent();
        for (int i = 0; i < listString.size(); i++) {
            info += (number + ". " + listString.get(i) + "\n");
            number++;
        }
        info += "Наберите номер фильма который вам понравился";

        return info;
    }


    @Override
    public String getBotUsername() {
        return "@DATT_Bot";
    }

    @Override
    public String getBotToken() {
        return "899355466:AAHzvUzhXMIdb87WmVkLy1rJioFhmaBS_Ws";
    }
}
