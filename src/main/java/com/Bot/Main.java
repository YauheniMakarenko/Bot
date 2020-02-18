package com.Bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            System.out.println("Бот запущен");
            telegramBotsApi.registerBot(Bot.getInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
