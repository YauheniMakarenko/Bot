package com.Bot.Commands;

import com.Bot.Bot;
import org.telegram.telegrambots.api.objects.Update;

public class CommandMovies implements ICommand {
    private Bot bot = Bot.getBot();

    @Override
    public void action(Update update) {
        bot.sendMsg(update.getMessage().getChatId(), bot.getInfoMovies());
    }
}
