package com.Bot.Commands;

import com.Bot.Bot;
import org.telegram.telegrambots.api.objects.Message;

public class CommandMovies implements ICommand {
    Bot bot = Bot.getBot();

    @Override
    public void action(Message message) {
        bot.sendMsg(message.getChatId(), bot.getInfoMovies());
    }
}
