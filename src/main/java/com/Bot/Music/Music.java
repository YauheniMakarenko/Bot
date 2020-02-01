package com.Bot.Music;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Music {
    private Document document;

    public Music() {
        connect();
    }

    private void connect() {
        try {
            document = Jsoup.connect("https://zaycev.net").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMusic() {
        Elements elements = document.getElementsByClass("musicset-track-list__items");
        List<String> list = new ArrayList<>();

        for (int i = 0; i < elements.first().children().size(); i++) {
            list.add(elements.first().child(i).text());
        }
        return list;
    }

    public List<String> getJSONAudio() {
        Elements elements = document.getElementsByClass("musicset-track-list__items");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < elements.first().children().size(); i++) {

            Elements elementsUrl = elements.first().child(i).getElementsByClass("musicset-track__download-link track-geo__button");
            String stringUrl = elementsUrl.attr("href");
            list.add(stringUrl);

        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(new Music().getJSONAudio());
    }

}
