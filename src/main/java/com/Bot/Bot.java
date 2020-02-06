package com.Bot;

import com.Bot.Commands.RemoteControlAdmin;
import com.Bot.Movies.Movies;
import com.Bot.Music.Music;
import com.Bot.Weather.Model;
import com.Bot.Weather.Weather;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Bot extends TelegramLongPollingBot implements IBot {

    private final String botUserName = "@DATT_Bot";
    private final String token = "899355466:AAHzvUzhXMIdb87WmVkLy1rJioFhmaBS_Ws";

    private Model model = new Model();
    private Movies movies = new Movies();
    private Music music = new Music();
    private int index;
    private static Bot instance;

    private Bot() {
    }

    public static Bot getBot() {
        if (instance == null) {
            instance = new Bot();
        }
        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            workWithMessages(update);
        } else if (update.hasCallbackQuery()) {
            workWithCallbackQuery(update);
        }
    }

    public void workWithMessages(Update update) {
        Message message = update.getMessage();
        long chatId = update.getMessage().getChatId();

        if (message.getText().equals("Weather")
                || message.getText().equals("Music")
                || message.getText().equals("/start")
                || message.getText().equals("Movies")) {

            RemoteControlAdmin.findCommand(message.getText()).action(message);
        } else {
            if (finder(message.getText(), "\\d+")) {

                sendMsg(chatId, movies.getMovie(message));

            } else if (finder(message.getText(), "[A-zА-я]+")) {
                try {
                    sendMsg(chatId, Weather.getWeather(message.getText(), model));
                } catch (IOException e) {
                    sendMsg(chatId, "Город не найден!");
                }
            }
        }
    }

    public void workWithCallbackQuery(Update update) {
        String text = update.getCallbackQuery().getData();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        if (text.equals("\u27A1")) {
            try {
                execute(new EditMessageReplyMarkup()
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .setChatId(chat_id)
                        .setReplyMarkup(sendInlineKeyBoardMessage(index + 10, index + 20)));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (text.equals("\u2B05")) {
            try {
                execute(new EditMessageReplyMarkup()
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .setChatId(chat_id)
                        .setReplyMarkup(sendInlineKeyBoardMessage(index - 10, index)));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendAudioMessage(chat_id, text);
        }
    }

    @Override
    public synchronized void sendMsg(long chatId, String text) {
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    private boolean finder(String element, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(element);
        matcher.find();
        return matcher.matches();

    }

    public String getInfoMovies() {
        String info = "";
        int number = 0;
        List<String> listString = movies.getEvent();
        for (int i = 0; i < listString.size(); i++) {
            info += (number + ". " + listString.get(i) + "\n");
            number++;
        }
        info += "Выберите номер фильма который вам понравился";

        return info;
    }

    public synchronized InlineKeyboardMarkup sendInlineKeyBoardMessage(int index, int bound) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<String> listMusic = music.getMusic();
        List<String> listJsonMusic = music.getJSONAudio();

        if (index == listJsonMusic.size() && index == listMusic.size()) {
            index = 0;
            bound = 10;
        }

        if (index < 0) {
            index = 50;
            bound = 60;
        }

        this.index = index;
        for (; index < bound; index++) {
            rowList.add(Arrays.asList(
                    new InlineKeyboardButton().setText(listMusic.get(index)).setCallbackData(listJsonMusic.get(index))));
        }

        rowList.add(Arrays.asList(
                new InlineKeyboardButton().setText("\u2B05").setCallbackData("\u2B05"),
                new InlineKeyboardButton().setText("\u27A1").setCallbackData("\u27A1")
        ));
        
        return inlineKeyboardMarkup.setKeyboard(rowList);
    }

    public synchronized void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Weather"));
        keyboardFirstRow.add(new KeyboardButton("Movies"));
        keyboardFirstRow.add(new KeyboardButton("Music"));

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
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
