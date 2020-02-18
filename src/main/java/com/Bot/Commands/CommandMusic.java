package com.Bot.Commands;

import com.Bot.Bot;
import com.Bot.Keyboard;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandMusic implements ICommand {

    private Bot bot = Bot.getInstance();

    @Override
    public void action(Update update) {

        try {
            bot.execute(new SendMessage()
                    .enableMarkdown(true)
                    .setChatId(update.getMessage().getChatId())
                    .setText("Выберите пункт")
                    .setReplyMarkup(sendInlineKeyBoardMessage()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public InlineKeyboardMarkup sendInlineKeyBoardMessage(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(Arrays.asList(
                new InlineKeyboardButton().setText("TopMusic").setCallbackData("TopMusic"),
                new InlineKeyboardButton().setText("SearchMusic").setCallbackData("SearchMusic")
        ));

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup.setKeyboard(rowList);
    }
}