package de.failender.dgo.persistance.telegram;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.mapper.FieldMapper;
import de.failender.ezql.mapper.IntFieldMapper;
import de.failender.ezql.mapper.LongFieldMapper;

import java.util.Arrays;
import java.util.List;

public class NotificationConfigurationMapper extends EntityMapper<NotificationConfiguration> {


    public static final LongFieldMapper<NotificationConfiguration> ID = new LongFieldMapper<>("ID", NotificationConfiguration::setId, NotificationConfiguration::getId);
    public static final LongFieldMapper<NotificationConfiguration> GROUPID = new LongFieldMapper<>("GRUPPE_ID", NotificationConfiguration::setGroupId, NotificationConfiguration::getGroupId);
    public static final LongFieldMapper<NotificationConfiguration> GUILD_ID = new LongFieldMapper<>("GUILD_ID", NotificationConfiguration::setGuildId, NotificationConfiguration::getGuildId);
    public static final IntFieldMapper<NotificationConfiguration> LISTENER_TYPE = new IntFieldMapper<>("LISTENER_TYPE", NotificationConfiguration::setListenerType, NotificationConfiguration::getListenerType);
    public static final LongFieldMapper<NotificationConfiguration> CHATID = new LongFieldMapper<>("CHAT_ID", NotificationConfiguration::setChatId, NotificationConfiguration::getChatId);
    public static final NotificationConfigurationMapper INSTANCE = new NotificationConfigurationMapper();
    private static final List<FieldMapper<NotificationConfiguration, ?>> FIELDS = Arrays.asList(ID, GROUPID, CHATID, GUILD_ID, LISTENER_TYPE);

    @Override
    public String table() {
        return "TELEGRAM_NOTIFICATIONS";
    }

    @Override
    public NotificationConfiguration create() {
        return new NotificationConfiguration();
    }

    @Override
    public List<FieldMapper<NotificationConfiguration, ?>> fieldMappers() {
        return FIELDS;
    }

    @Override
    public LongFieldMapper<NotificationConfiguration> idField() {
        return ID;
    }
}
