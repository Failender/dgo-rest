package de.failender.dgo.persistance.telegram;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;

class NotificationConfigurationRepository extends EzqlRepository<NotificationConfiguration> {
    public static final NotificationConfigurationRepository INSTANCE = new NotificationConfigurationRepository();

    @Override
    protected EntityMapper<NotificationConfiguration> getMapper() {
        return NotificationConfigurationMapper.INSTANCE;
    }

    public List<NotificationConfiguration> findByGroup(long group) {
        return findBy(NotificationConfigurationMapper.GROUPID, group);
    }

}
