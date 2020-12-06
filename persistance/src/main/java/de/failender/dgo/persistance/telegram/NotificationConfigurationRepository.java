package de.failender.dgo.persistance.telegram;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.queries.SelectQuery;
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

    public List<NotificationConfiguration> findByGroupAndListenerType(long group, int listenerType) {
        return SelectQuery.Builder.selectAll(getMapper())
                .where(NotificationConfigurationMapper.GROUPID, group)
                .where(NotificationConfigurationMapper.LISTENER_TYPE, listenerType)
                .execute();
    }
}
