package com.Bot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public boolean finder(String element, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(element);
        matcher.find();
        return matcher.matches();

    }
}
