package com.Bot.Commands;

import org.telegram.telegrambots.api.objects.Update;

public interface ICommand {
    void action(Update update);
}
