package com.Bot.Commands;

import com.Bot.Bot;
import com.Bot.Keyboard;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class CommandMusicTop implements ICommand {

    private Bot bot = Bot.getInstance();
    private Keyboard keyboard = Keyboard.getInstance();

    @Override
    public void action(Update update) {
        try {
            bot.execute(new SendMessage()
                    .enableMarkdown(true)
                    .setChatId(update.getCallbackQuery().getMessage().getChatId())
                    .setText("Популярные треки на zaycev.net \n" +
                            "Выберите трек который хотите прослушать")
                    .setReplyMarkup(keyboard.sendInlineKeyBoardMessage(0, 10)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}