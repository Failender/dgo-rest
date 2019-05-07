package de.failender.dgo.persistance.held;

import de.failender.dgo.persistance.HibernateUtil;
import de.failender.dgo.persistance.gruppe.GruppeEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.List;

public class HeldRepository {


    public static List<HeldEntity> findByUserId(Integer id) {
        String sql = "from HeldEntity WHERE userId = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<HeldEntity> query = session.createQuery(sql, HeldEntity.class);
            query.setParameter("id", id);
            return query.getResultList();
        }
    }

    public static void persist(HeldEntity heldEntity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            heldEntity.setId((BigInteger)session.save(heldEntity));
        }
    }
}
