package com.Bot.Movies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;

public class Poster {

    public Poster() {
    }

    private Document connect(String givenString){
        Document document = null;
        try {
            document = Jsoup.connect(givenString).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public String getInfoMovie(String urlConnect) {
        Document document = connect(urlConnect);
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

    public String getTitle(String urlConnect) {
        Document document = connect(urlConnect);
        return document.title();
    }
}