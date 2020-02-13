package com.Bot;

import com.Bot.Music.Music;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Keyboard {

    private Music music = new Music();
    private int index;
    private static Keyboard instance;

    private Keyboard(){
    }

    public static Keyboard getInstance() {
        if (instance == null){
            instance = new Keyboard();
        }
        return instance;
    }

    public int getIndex() {
        return index;
    }

    public InlineKeyboardMarkup sendInlineKeyBoardMessage(int index, int bound) {
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

    public void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Movies"));
        keyboardFirstRow.add(new KeyboardButton("Music"));

        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
