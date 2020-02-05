package com.gamechangesolutions.assignment.utils;

import org.jsoup.Jsoup;

public class Utils {
    /**
     * @param str input string
     * @return removes HTML tags and converts to plain string
     */
    public static String removeHtmlTags(String str) {
        return Jsoup.parse(str).text()
                .replace("*", "\n\t*")
                .replace("###", "\n\n###")
                ;
    }
}
