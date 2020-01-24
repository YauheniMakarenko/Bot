package com.Bot.Movies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.api.objects.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movies {

    private Document document;
    private List<String> listUrl = new ArrayList<>();
    private Poster poster;

    public Movies() {
        connect();
    }

    private void connect() {
        try {
            document = Jsoup.connect("https://afisha.tut.by/film").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMovie(Message message){
        String messageText = message.getText();
        String url = "";
        for (int i = 0; i < listUrl.size(); i++) {
            if (messageText.equals(Integer.toString(i))){
                url = listUrl.get(i);
            }
        }
        poster = new Poster(url);
        String result = poster.getTitle() + "\n";
        result = result.replaceAll("трейлер, отзывы и расписание на AFISHA.TUT.BY", "").toUpperCase();
        result += poster.getInfoMovie();
        return result;
    }

    public List<String> getEvent() {
        List<String> listInfoMovies = new ArrayList<>();
        Element elementEventsBlock = document.getElementById("events-block");

        for (int i = 0; i < elementEventsBlock.children().size(); i++) {
            Element element = elementEventsBlock.child(i);
            if (i == 1) {
                continue;
            }
            for (int j = 0; j < element.children().size(); j++) {
                String text = element.child(j).getElementsByClass("name").text();
                String genres = element.child(j).getElementsByClass("txt").text();
                genres = genres.replaceAll("Купить билет", "");

                listInfoMovies.add(text + ". Жанры: " + genres);
                listUrl.add(element.child(j).getElementsByClass("name").attr("href"));
            }
        }
        return listInfoMovies;
    }
}
