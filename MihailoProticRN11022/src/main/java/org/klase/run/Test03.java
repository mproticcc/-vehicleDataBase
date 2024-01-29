package org.klase.run;

import org.hibernate.Session;
import org.klase.model.KategorijaVozaca;
import org.klase.model.Vozac;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;
import javax.persistence.criteria.*;
import java.util.List;

public class Test03 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<KategorijaVozaca> rootKategorijeVozaca = criteriaQuery.from(KategorijaVozaca.class);
        Join<KategorijaVozaca, Vozac> joinVozaci = rootKategorijeVozaca.join("vozaci", JoinType.LEFT);

        criteriaQuery.multiselect(
                rootKategorijeVozaca.get("naziv").alias("KategorijaVozaca"),
                criteriaBuilder.count(joinVozaci.get("vozacId")).alias("BrojVozaca")
        );

        criteriaQuery.groupBy(rootKategorijeVozaca.get("naziv"));

        List<Object[]> result = session.createQuery(criteriaQuery).getResultList();
        for (Object[] row : result) {
            String kategorijaVozaca = (String) row[0];
            Long brojVozaca = (Long) row[1];

            System.out.println("Kategorija vozača: " + kategorijaVozaca);
            System.out.println("Broj vozača: " + brojVozaca);
        }

        session.getTransaction().commit();

    }
}
