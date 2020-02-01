package com.Bot;

import com.Bot.Movies.Movies;
import com.Bot.Music.Music;
import com.Bot.Weather.Model;
import com.Bot.Weather.Weather;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private Model model = new Model();
    private Movies movies = new Movies();
    private Music music = new Music();
    private int index = 10;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = update.getMessage().getChatId();
            switch (message.getText()) {
                case "/Weather":
                    sendMsg(chatId, "Введите город!");

                    try {
                        sendMsg(chatId, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(chatId, "Город не найден!");
                    }

                    break;
                case "/Music":
                    try {
                        execute(sendInlineKeyBoardMessage(chatId, "Популярные треки на zaycev.net \n" +
                                "Выберите трек который хотите прослушать"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/Movies":
                    sendMsg(chatId, getInfoMovies());

                    // sendMsg(chatId, getMovie(message));       //message цифра из списка
                    break;
                case "/Start":
                    sendMsg(chatId, "Список команд:\n /Weather\n /Music\n /Movies");
                    break;
                default:


                    /*try{
                        sendMsg(chatId, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(chatId, "Город не найден!");
                    }*/


                    sendMsg(chatId, movies.getMovie(message));
            }

        } else if (update.hasCallbackQuery()) {
            String text = update.getCallbackQuery().getData();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            if (text.equals("Следующие")) {
                try {
                    execute(sendInlineKeyBoardMessage(chat_id, "Следующие"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (text.equals("Предыдущие")) {
                try {
                    execute(sendInlineKeyBoardMessage(chat_id, "Предыдущие"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                sendAudioMessage(chat_id, text);
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

    public synchronized void sendAudioMessage(long chatId, String givenStringJson) {
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
        info += "Вы номер фильма который вам понравился";

        return info;
    }

    public synchronized SendMessage sendInlineKeyBoardMessage(long chatId, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<String> listMusic = music.getMusic();
        List<String> listJsonMusic = music.getJSONAudio();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        if (!text.equals("Следующие") && !text.equals("Предыдущие")) {
            for (int i = 0; i < 10; i++) {
                List<InlineKeyboardButton> list = new ArrayList<>();
                list.add(new InlineKeyboardButton().setText(listMusic.get(i)).setCallbackData(listJsonMusic.get(i)));
                rowList.add(list);
            }
        } else if (text.equals("Следующие")) {
            for (int i = index; i < index + 10; i++) {
                List<InlineKeyboardButton> list = new ArrayList<>();
                list.add(new InlineKeyboardButton().setText(listMusic.get(i)).setCallbackData(listJsonMusic.get(i)));
                rowList.add(list);
            }
            index += 10;
        } else if (text.equals("Предыдущие")) {
            for (int i = index - 20; i < index - 10; i++) {
                List<InlineKeyboardButton> list = new ArrayList<>();
                list.add(new InlineKeyboardButton().setText(listMusic.get(i)).setCallbackData(listJsonMusic.get(i)));
                rowList.add(list);
            }
            index -= 10;
        }


        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        keyboardButtonsRow.add(new InlineKeyboardButton().setText("Предыдущие").setCallbackData("Предыдущие"));
        keyboardButtonsRow.add(new InlineKeyboardButton().setText("Следующие").setCallbackData("Следующие"));


        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text)
                .setReplyMarkup(inlineKeyboardMarkup);
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
