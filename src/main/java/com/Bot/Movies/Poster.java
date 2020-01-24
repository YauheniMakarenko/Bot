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
        Elements element = elementBlock.child(0).getElementsByClass("a-event-list js-film-list-wrapper");
        for (int i = 0; i < element.size(); i++) {
            Element tmp = element.get(i).child(i);
            for (int j = 0; j < tmp.children().size(); j++) {
                String name = tmp.child(j).getElementsByClass("item-header").text();
                String time = tmp.child(j).getElementsByClass("b-shedule__list js-shedule-list").text();

                time = time.replaceAll("Сеанс уже прошел", " ");
                time = time.replaceAll("Купить билет: от \\d+ руб\\.", "");
                time = time.replaceAll("до \\d+ руб\\.", "");

                result += "Кинотеатр: " + name + ", Время: " + time +  "\n";
            }
        }
        return result;
    }

    public String getTitle(){
        return document.title();
    }
}
