package com.Bot.Commands;

import com.Bot.Bot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class CommandMusic implements ICommand {
    Bot bot = Bot.getBot();

    @Override
    public void action(Message message) {
        try {
            bot.execute(new SendMessage()
                    .enableMarkdown(true)
                    .setChatId(message.getChatId())
                    .setText("Популярные треки на zaycev.net \n" +
                            "Выберите трек который хотите прослушать")
                    .setReplyMarkup( bot.sendInlineKeyBoardMessage(0, 10)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
