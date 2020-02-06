package com.Bot.Commands;

import java.util.Arrays;

public enum RemoteControlAdmin {

    MUSIC("Music", new CommandMusic()),
    MOVIES("Movies", new CommandMovies()),
    WEATHER("Weather", new CommandWeather()),
    START("/start", new CommandStart());

    private String code;
    private ICommand command;

    RemoteControlAdmin(String code, ICommand command) {
        this.code = code;
        this.command = command;
    }


    public static ICommand findCommand(String numberCommand) {
        return Arrays.stream(RemoteControlAdmin.values())
                .filter(command -> command.code.equals(numberCommand))
                .findAny()
                .get()
                .command;
    }
}
