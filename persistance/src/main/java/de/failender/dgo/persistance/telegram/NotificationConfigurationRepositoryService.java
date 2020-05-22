package de.failender.dgo.persistance.telegram;

import java.util.List;

public class NotificationConfigurationRepositoryService {

    public static List<NotificationConfiguration> findByGroup(long group) {
        return NotificationConfigurationRepository.INSTANCE.findByGroup(group);
    }

    public static void persist(NotificationConfiguration notificationConfiguration) {
        NotificationConfigurationRepository.INSTANCE.persist(notificationConfiguration);
    }
}
