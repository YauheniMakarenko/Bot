package com.Bot.Commands;

import com.Bot.Bot;
import com.Bot.Music.SearchMusic;
import org.telegram.telegrambots.api.objects.Update;

public class CommandSearchMusic implements ICommand {

    private Bot bot = Bot.getInstance();
    private SearchMusic searchMusic;

    @Override
    public void action(Update update) {

        if (update.getCallbackQuery() != null) {
            bot.sendMsg(update.getCallbackQuery().getMessage().getChatId(), "Введите музыку например: нурминский - купить бы джип");
        }else {
            String givenString = update.getMessage().getText();

            searchMusic = new SearchMusic();
            String jsonUrl = searchMusic.getAudio(givenString);
            bot.sendAudioMessage(update.getMessage().getChatId(), jsonUrl);
        }

    }
}