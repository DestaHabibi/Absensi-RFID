/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author MyBook Hype AMD
 */
public class I18nService {
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    public interface I18nChangeListener {
        void onLanguageChanged();
    }

    private static final List<I18nChangeListener> listeners = new ArrayList<>();

    static {
        setLocale(Locale.of("id"));
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
        notifyListeners();
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!";
        }
    }
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static synchronized void registerListener(I18nChangeListener l) {
        if (!listeners.contains(l)) listeners.add(l);
    }

    public static synchronized void unregisterListener(I18nChangeListener l) {
        listeners.remove(l);
    }

    private static void notifyListeners() {
        for (I18nChangeListener l : listeners) {
            if (l != null) l.onLanguageChanged();
        }
    }
}
