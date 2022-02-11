package edu.epam.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyReader {

    public static Locale en_US = new Locale("en", "US");
    public static Locale ru_RU = new Locale("ru", "RU");

    public static ResourceBundle rb = ResourceBundle.getBundle("MessageBundle", en_US);

    public static String getValue(String key) {
        String val = null;

        try {
            val = rb.getString(key);
        } catch (Exception e) {
            val = key;
        }
        return val;
    }

    public static String getValue(String key, String param) {
        String msg = getValue(key);
        msg = msg.replace("{0}", param);
        return msg;
    }

    public static String getValue(String key, String[] params) {
        String msg = getValue(key);
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    public static void changeLanguage(Locale language){
        if (language.equals(rb.getLocale()))
            return;
        rb = ResourceBundle.getBundle("MessageBundle", language);
    }

    public static void main(String[] args) {
        System.out.println(rb.getLocale());
        if (rb.getLocale().equals(PropertyReader.en_US))
            System.out.println("comparison works");
        changeLanguage(ru_RU);

    }

}