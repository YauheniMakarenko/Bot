package com.Bot.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    private Model model = new Model();

    public String getWeather(String message) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=81624fd7cb4be183b821c1e102f117e6");

        String result = getString(url);

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        method1(object);
        method2(object);

        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Main: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }

    private String getString(URL url) throws IOException {
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        return result;
    }

    private void method1(JSONObject object){
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));
    }

    private void method2(JSONObject object){
        JSONArray getArr = object.getJSONArray("weather");
        for (int i = 0; i < getArr.length(); i++) {
            JSONObject obj = getArr.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }
    }
}