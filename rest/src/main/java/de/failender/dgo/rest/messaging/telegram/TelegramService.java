package de.failender.dgo.rest.messaging.telegram;

import com.pengrad.telegrambot.TelegramBot;
import de.failender.ezql.properties.PropertyReader;


public class TelegramService {


    private static TelegramBot telegramBot;

    public static void initialize() {
        String token = PropertyReader.getProperty("dsa.gruppen.online.telegram.token");
        if (token == null) {
            return;
        }

        telegramBot = new TelegramBot(token);
//        telegramBot.
//        telegramBot.setUpdatesListener(updates -> {
//            for (Update update : updates) {
//
//            }
//
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        });

    }
}
