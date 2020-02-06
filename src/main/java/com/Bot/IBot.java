package com.Bot;

public interface IBot {
    void sendMsg(long chatId, String text);
    void sendAudioMessage(long chatId, String givenStringJson);
}
