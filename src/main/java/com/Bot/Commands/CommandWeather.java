package com.Bot.Commands;

import com.Bot.Bot;
import com.Bot.Weather.Weather;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.IOException;

public class CommandWeather implements ICommand {
    private Bot bot = Bot.getBot();
    private Weather weather = new Weather();

    @Override
    public void action(Update update) {

        try {
            bot.sendMsg(update.getMessage().getChatId(), weather.getWeather(update.getMessage().getText().replaceAll("Weather ", "")));
        } catch (IOException e) {
            bot.sendMsg(update.getMessage().getChatId(), "Город не найден!");
        }
    }
}
