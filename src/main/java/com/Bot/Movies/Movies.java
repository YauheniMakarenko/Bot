package com.Bot.Movies;

import com.Bot.Bot;
import com.google.inject.internal.asm.$Attribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Movies {

    private List<String> listUrl;
    private Poster poster;

    public Movies() {
    }

    private Document connect() {
        Document document = null;
        try {
            document = Jsoup.connect("https://afisha.tut.by/film").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public String getMovie(Message message) {

        String messageText = message.getText();
        String url = "";
        if (listUrl == null) {
            return "Ошибка ввода";
        }
        for (int i = 0; i < listUrl.size(); i++) {
            if (messageText.equals(Integer.toString(i))) {
                url = listUrl.get(i);
            }
        }
        if (url.equals("")) {
            return "Ошибка ввода";
        }
        poster = new Poster();
        String result = poster.getTitle(url) + "\n";
        result = result.replaceAll("трейлер, отзывы и расписание на AFISHA.TUT.BY", "").toUpperCase();
        result += poster.getInfoMovie(url);

        return result;
    }

    public List<String> getEvent() {
        Document document = connect();
        List<String> listInfoMovies = new ArrayList<>();
        listUrl = new ArrayList<>();
        Element elementEventsBlock = document.getElementById("events-block");
        final int IGNORE_BLOCK = 1;
        for (int i = 0; i < elementEventsBlock.children().size(); i++) {
            Element element = elementEventsBlock.child(i);
            if (i == IGNORE_BLOCK) {
                continue;
            }
            addToList(element, listInfoMovies);
        }
        return listInfoMovies;
    }

    private void addToList(Element element, List<String> listInfoMovies) {
        for (int j = 0; j < element.children().size(); j++) {
            String text = element.child(j).getElementsByClass("name").text();
            String genres = element.child(j).getElementsByClass("txt").text();
            genres = genres.replaceAll("Купить билет", "");

            listInfoMovies.add(text + ". Жанры: " + genres);
            listUrl.add(element.child(j).getElementsByClass("name").attr("href"));
        }
    }

}