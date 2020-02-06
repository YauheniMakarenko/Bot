package com.Bot.Commands;

import com.Bot.Bot;
import org.telegram.telegrambots.api.objects.Message;

public class CommandStart implements ICommand {
    Bot bot = Bot.getBot();
    @Override
    public void action(Message message) {
        bot.sendMsg(message.getChatId(), "Список команд:\n Weather\n Music\n Movies");
    }
}
