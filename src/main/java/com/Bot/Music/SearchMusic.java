package com.Bot.Music;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SearchMusic {

    public SearchMusic() {
    }

    public String getAudio(String givenString) {
        Document document = null;
        try {
            document = Jsoup.connect("https://zaycev.net/search.html?query_search=" + givenString).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonUrl;

        Elements elements = document.getElementsByClass("musicset-track-list__items");

        Elements elements1 = elements.first().child(0).getElementsByClass("musicset-track__download-link track-geo__button");

        jsonUrl = elements1.attr("href");

        return jsonUrl;
    }

    public static void main(String[] args) {
        System.out.println(new SearchMusic().getAudio("нурминский - купить бы джип"));
    }

}