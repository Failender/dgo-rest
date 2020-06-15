package de.failender.dgo.rest.messaging.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import de.failender.dgo.integration.Beans;
import de.failender.dgo.integration.HeldUpdateListener;
import de.failender.dgo.persistance.gruppe.GruppeEntity;
import de.failender.dgo.persistance.gruppe.GruppeRepositoryService;
import de.failender.dgo.persistance.held.HeldEntity;
import de.failender.dgo.persistance.telegram.NotificationConfiguration;
import de.failender.dgo.persistance.telegram.NotificationConfigurationRepositoryService;
import de.failender.ezql.EzqlConnector;
import de.failender.ezql.properties.PropertyReader;


public class TelegramService {


    private static TelegramBot telegramBot;

    public static void initialize() {
        String token = PropertyReader.getProperty("dsa.gruppen.online.telegram.token");
        if (token == null) {
            return;

        }

        telegramBot = new TelegramBot(token);
        telegramBot.setUpdatesListener(updates -> {
            EzqlConnector.allocateConnection();
            for (Update update : updates) {
                try {
                    if (update.message().chat().type() == Chat.Type.group || update.message().chat().type() == Chat.Type.Private) {
                        if (update.message().text().startsWith("/start")) {
                            long chatId = update.message().chat().id();

                            try {
                                long gruppeId = Long.valueOf(update.message().text().substring("/start ".length()));
                                GruppeEntity gruppeEntity = GruppeRepositoryService.findById(gruppeId);
                                if (gruppeEntity != null) {

                                    NotificationConfiguration notificationConfiguration = new NotificationConfiguration();
                                    notificationConfiguration.setChatId(chatId);
                                    notificationConfiguration.setGroupId(gruppeId);
                                    try {
                                        NotificationConfigurationRepositoryService.persist(notificationConfiguration);
                                        telegramBot.execute(new SendMessage(chatId, "Dieser Chat wurde für Updates von der Gruppe " + gruppeEntity.getName() + "  registriert!"));
                                    } catch (RuntimeException e) {
                                        telegramBot.execute(new SendMessage(chatId, "Dieser Chat ist bereits für Updates von der Gruppe " + gruppeEntity.getName() + "  registriert!"));
                                    }
                                } else {
                                    telegramBot.execute(new SendMessage(chatId, "Es konnte keine Gruppe mit der ID " + gruppeId + " gefunden werden"));
                                }


                            } catch (NumberFormatException e) {
                                telegramBot.execute(new SendMessage(chatId, "Der Befehl wurde nicht erkannt!"));
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            EzqlConnector.releaseConnection();
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        initializeUpdateListener();

    }

    private static void initializeUpdateListener() {
        Beans.HELD_UPDATE_LISTENER.add(new HeldUpdateListener() {
            @Override
            public void updated(HeldEntity heldEntity) {
                System.out.println(heldEntity);
                for (NotificationConfiguration notificationConfiguration : NotificationConfigurationRepositoryService.findByGroup(heldEntity.getGruppe())) {
                    try {
                        telegramBot.execute(new SendMessage(notificationConfiguration.getChatId(), "Neue Version für den Helden " + heldEntity.getName()));
                    } catch (Exception e) {
                        ;
                    }
                }
            }

            @Override
            public void publicUpdated(HeldEntity heldEntity) {

            }

            @Override
            public void activeUpdated(HeldEntity heldEntity) {

            }
        });
    }
}
