package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;
import java.util.Optional;

class LagerortRepository extends EzqlRepository<LagerortEntity> {

    public static final LagerortRepository INSTANCE = new LagerortRepository();
    @Override
    protected EntityMapper<LagerortEntity> getMapper() {
        return LagerortMapper.INSTANCE;
    }

    public List<LagerortEntity> findByHeldId(Long heldid) {
        return findBy(LagerortMapper.HELDID, heldid);
    }

    public Optional<LagerortEntity> findOneByNameAndHeldId(String name, Long heldid) {
        return findOneBy(field(LagerortMapper.NAME, name), field(LagerortMapper.HELDID, heldid));
    }

}
