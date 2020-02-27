package de.failender.dgo.persistance.held.inventar.lagerort;

import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.List;

class GegenstandToLagerortRepository extends EzqlRepository<GegenstandToLagerortEntity> {

    static final GegenstandToLagerortRepository INSTANCE = new GegenstandToLagerortRepository();
    @Override
    protected EntityMapper<GegenstandToLagerortEntity> getMapper() {
        return GegenstandtoLagerortMapper.INSTANCE;
    }

    public List<GegenstandToLagerortEntity> findByLagerort(Long lagerort) {
        return findBy(GegenstandtoLagerortMapper.LAGERORT, lagerort);
    }

    public void deleteByNameAndHeldid(Long lagerort, String gegenstand) {
        deleteBy(field(GegenstandtoLagerortMapper.LAGERORT, lagerort), field(GegenstandtoLagerortMapper.NAME, gegenstand));
    }
}
