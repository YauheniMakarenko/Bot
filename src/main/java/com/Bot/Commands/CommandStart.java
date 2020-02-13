package com.Bot.Commands;

import com.Bot.Bot;
import org.telegram.telegrambots.api.objects.Update;

public class CommandStart implements ICommand {
    private Bot bot = Bot.getBot();
    @Override
    public void action(Update update) {
        bot.sendMsg(update.getMessage().getChatId(), "Привет, " + update.getMessage().getChat().getFirstName() + ". " +
                "Помимо кнопок есть еще команда погода. Для ее использования введите Weather название города");
    }
}
