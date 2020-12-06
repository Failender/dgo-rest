package de.failender.dgo.persistance.telegram;

import de.failender.dgo.persistance.BaseEntity;

public class NotificationConfiguration extends BaseEntity {

    private long groupId;
    private long chatId;
    private long guildId;
    private int listenerType;


    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getListenerType() {
        return listenerType;
    }

    public void setListenerType(int listenerType) {
        this.listenerType = listenerType;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }
}
