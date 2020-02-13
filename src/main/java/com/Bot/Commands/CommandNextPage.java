package com.Bot.Commands;

import com.Bot.Bot;
import com.Bot.Keyboard;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class CommandNextPage implements ICommand {

    private Bot bot = Bot.getBot();
    private Keyboard keyboard = Keyboard.getInstance();

    @Override
    public void action(Update update) {
        try {
            bot.execute(new EditMessageReplyMarkup()
                    .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                    .setChatId(update.getCallbackQuery().getMessage().getChatId())
                    .setReplyMarkup(keyboard.sendInlineKeyBoardMessage(keyboard.getIndex() + 10, keyboard.getIndex() + 20)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
