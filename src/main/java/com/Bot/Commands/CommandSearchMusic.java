package com.Bot.Commands;

import com.Bot.Bot;
import com.Bot.Music.SearchMusic;
import org.telegram.telegrambots.api.objects.Update;

public class CommandSearchMusic implements ICommand {

    private Bot bot = Bot.getBot();
    private SearchMusic searchMusic;

    @Override
    public void action(Update update) {

        if (update.getCallbackQuery() != null) {
            bot.sendMsg(update.getCallbackQuery().getMessage().getChatId(), "Введите музыку например: Music нурминский - купить бы джип");
        }else {
            searchMusic = new SearchMusic(update.getMessage().getText().replaceAll("Music ", ""));
            String s = searchMusic.getAudio();
            bot.sendAudioMessage(update.getMessage().getChatId(), s);
        }

    }
}
