package com.Bot.Movies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Poster {
    private Document document;

    public Poster(String string) {
        try {
            document = Jsoup.connect(string).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInfoMovie() {
        Element elementBlock = document.getElementById("schedule-table");
        String result = "";
        final int FIRST_BLOCK = 0;
        Element element = elementBlock.child(FIRST_BLOCK).getElementsByClass("a-event-list js-film-list-wrapper").first().child(FIRST_BLOCK);
        for (int j = 0; j < element.children().size(); j++) {
            String name = element.child(j).getElementsByClass("item-header").text();
            String time = element.child(j).getElementsByClass("b-shedule__list js-shedule-list").text();

            time = time.replaceAll("Сеанс уже прошел", " ");
            time = time.replaceAll("Купить билет.++", "");
            time = time.replaceAll("Купить билет", "");

            result += "Кинотеатр: " + name + ", Время: " + time + "\n";
        }
        return result;
    }

    public String getTitle() {
        return document.title();
    }
}
