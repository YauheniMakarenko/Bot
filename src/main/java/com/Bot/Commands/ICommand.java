package com.Bot.Commands;

import org.telegram.telegrambots.api.objects.Message;

public interface ICommand {
    void action(Message message);
}
