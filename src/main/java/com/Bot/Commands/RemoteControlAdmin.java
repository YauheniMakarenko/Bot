package com.Bot.Commands;

import java.util.Arrays;

public enum RemoteControlAdmin {

    MUSIC("Music", new CommandMusic()),
    TOP_MUSIC("TopMusic", new CommandMusicTop()),
    SEARCH_MUSIC("SearchMusic", new CommandSearchMusic()),
    MOVIES("Movies", new CommandMovies()),
    WEATHER("Weather", new CommandWeather()),
    START("/start", new CommandStart()),
    NEXT("\u27A1", new CommandNextPage()),
    PREVIOUS("\u2B05", new CommandPreviousPage());

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
