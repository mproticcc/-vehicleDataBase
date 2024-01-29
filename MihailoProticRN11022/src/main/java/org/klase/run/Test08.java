package org.klase.run;

import org.hibernate.Session;
import org.klase.model.Linija;
import org.klase.model.Smer;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class Test08 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Linija> rootLinije = criteriaQuery.from(Linija.class);
        Join<Smer, Linija> joinSmerovi = rootLinije.join("smerovi");

        criteriaQuery.multiselect(
                rootLinije.get("linijaID"),
                rootLinije.get("redniBroj").alias("redniBrojLinije"),
                criteriaBuilder.max(joinSmerovi.get("brojPolazaka")).alias("najvisePolazaka"),
                criteriaBuilder.min(joinSmerovi.get("brojPolazaka")).alias("najmanjePolazaka")
        );

        criteriaQuery.groupBy(
                rootLinije.get("linijaID"),
                rootLinije.get("redniBroj")
        );

        criteriaQuery.orderBy(
                criteriaBuilder.asc(rootLinije.get("linijaID"))
        );

        List<Object[]> result = session.createQuery(criteriaQuery).getResultList();

        for (Object[] row : result) {
            System.out.println("LinijaID: " + row[0]);
            System.out.println("Redni broj linije: " + row[1]);
            System.out.println("Najviše polazaka: " + row[2]);
            System.out.println("Najmanje polazaka: " + row[3]);
        }

        session.getTransaction().commit();

    }
}
