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

        String givenString = update.getMessage().getText();
        givenString = givenString.replaceAll("Weather ", "");

        try {
            bot.sendMsg(update.getMessage().getChatId(), weather.getWeather(givenString));
        } catch (IOException e) {
            bot.sendMsg(update.getMessage().getChatId(), "Город не найден!");
        }
    }
}
