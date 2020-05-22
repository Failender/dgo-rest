package de.failender.dgo.persistance.telegram;

import de.failender.dgo.persistance.BaseEntity;

public class NotificationConfiguration extends BaseEntity {

    private long groupId;
    private long chatId;


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
}
