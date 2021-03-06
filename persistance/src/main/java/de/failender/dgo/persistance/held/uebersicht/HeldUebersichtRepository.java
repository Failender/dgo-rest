package de.failender.dgo.persistance.held.uebersicht;

import de.failender.ezql.clause.BaseClause;
import de.failender.ezql.mapper.EntityMapper;
import de.failender.ezql.repository.EzqlRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HeldUebersichtRepository extends EzqlRepository<HeldUebersichtEntity> {

    public static final HeldUebersichtRepository INSTANCE = new HeldUebersichtRepository();

    @Override
    protected EntityMapper<HeldUebersichtEntity> getMapper() {
        return HeldUebersichtMapper.INSTANCE;
    }

    public Optional<HeldUebersichtEntity> findByHeldid(Long heldid) {
        return findOneBy(HeldUebersichtMapper.HELDID, heldid);
    }

    public void updateLepAndAspAndWunden(Long id, int lep, int asp, List<Integer> wunden) {
        List<BaseClause<HeldUebersichtEntity, ?>> updateClauses = new ArrayList<>();
        updateClauses.add(new BaseClause<>(HeldUebersichtMapper.ASP, asp));
        updateClauses.add(new BaseClause<>(HeldUebersichtMapper.LEP, lep));
        updateClauses.add(new BaseClause<>(HeldUebersichtMapper.WUNDEN, wunden));
        updateById(id, updateClauses);
    }
}
