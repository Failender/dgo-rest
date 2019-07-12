package de.failender.dgo.persistance.meister.raumplanebene;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;

class RaumplanEbeneRepository extends EzqlRepository<RaumplanEbeneEntity> {

    public static final RaumplanEbeneRepository INSTANCE = new RaumplanEbeneRepository();

    @Override
    protected EntityMapper<RaumplanEbeneEntity> getMapper() {
        return RaumplanEbeneMapper.INSTANCE;
    }

    public List<RaumplanEbeneEntity> findByParent(long parent) {
        return findBy(RaumplanEbeneMapper.PARENT, parent);
    }

    public void deleteByParent(long id) {
        deleteBy(RaumplanEbeneMapper.PARENT, id);
    }
}
