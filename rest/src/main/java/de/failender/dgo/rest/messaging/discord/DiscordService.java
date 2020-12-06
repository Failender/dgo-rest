package de.failender.dgo.rest.messaging.discord;

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
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordService extends ListenerAdapter {

    public static int DISCORD_LISTENER = 1;
    private JDA jda;

    public DiscordService(String token) {
        try {
            System.out.println("Connecting to Discord..");
            jda = JDABuilder.createDefault(token)
                    .addEventListeners(this)
                    .build().awaitReady();

            initializeUpdateListener();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public static void initialize() {
        String token = PropertyReader.getProperty("dsa.gruppen.online.discord.token");
        if(token == null) {
            return;
        }

        new DiscordService(token);

    }

    private void initializeUpdateListener() {


        Beans.HELD_UPDATE_LISTENER.add(new HeldUpdateListener() {
            @Override
            public void updated(HeldEntity heldEntity) {
                for (NotificationConfiguration notificationConfiguration : NotificationConfigurationRepositoryService.findByGroupAndListenerType(1, DISCORD_LISTENER)) {
                    jda.getGuildById(notificationConfiguration.getGuildId()).getTextChannelById(notificationConfiguration.getChatId())
                            .sendMessage("Neue Version für den Helden " + heldEntity.getName()).queue();

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

    @Override
    public void onGenericGuildMessage(GenericGuildMessageEvent event) {
        super.onGenericGuildMessage(event);
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(!event.getMessage().getContentRaw().startsWith("!register")) {
            return;
        }
        String[] splits = event.getMessage().getContentRaw().split(" ");
        if(splits.length != 2)
            return;

        try {
            long groupId = Long.parseLong(splits[1]);
            EzqlConnector.allocateConnection();

            GruppeEntity gruppeEntity = GruppeRepositoryService.findById(groupId);
            if(gruppeEntity == null) {
                return;
            }
            NotificationConfiguration notificationConfiguration = new NotificationConfiguration();
            notificationConfiguration.setChatId(event.getChannel().getIdLong());
            notificationConfiguration.setGroupId(groupId);
            notificationConfiguration.setGuildId(event.getGuild().getIdLong());
            notificationConfiguration.setListenerType(DISCORD_LISTENER);
            try {
                NotificationConfigurationRepositoryService.persist(notificationConfiguration);
                event.getChannel().sendMessage("Dieser Chat wurde für Updates von der Gruppe " + gruppeEntity.getName() + "  registriert!").queue();
            } catch (RuntimeException e) {
                event.getChannel().sendMessage("Dieser Chat ist bereits für Updates von der Gruppe " + gruppeEntity.getName() + "  registriert!").queue();
            }
        } catch (NumberFormatException e) {
            return;
        }finally {
            EzqlConnector.releaseConnection();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println(event);
    }
}
