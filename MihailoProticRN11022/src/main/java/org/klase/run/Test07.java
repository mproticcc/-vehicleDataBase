package org.klase.run;

import org.hibernate.Session;
import org.klase.model.Linija;
import org.klase.model.Smer;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;

import javax.persistence.criteria.*;
import java.util.List;

public class Test07 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Linija> rootLinije = criteriaQuery.from(Linija.class);
        Join<Smer, Linija> leftJoinSmerovi = rootLinije.join("smerovi", JoinType.LEFT);

        criteriaQuery.multiselect(
                rootLinije.get("linijaID"),
                criteriaBuilder.count(leftJoinSmerovi.get("smerID")).alias("brojSmerova")
        );

        criteriaQuery.groupBy(
                rootLinije.get("linijaID")
        );

        criteriaQuery.orderBy(
                criteriaBuilder.asc(rootLinije.get("linijaID"))
        );

        List<Object[]> result = session.createQuery(criteriaQuery).getResultList();

        for (Object[] row : result) {
            System.out.println("LinijaID: " + row[0]);
            System.out.println("Broj smerova: " + row[1]);
        }

        session.getTransaction().commit();

    }
}
