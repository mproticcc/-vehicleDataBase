package org.klase.run;

import org.hibernate.Session;
import org.klase.model.PotkategorijaVozila;
import org.klase.model.Vozilo;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class Test02 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<PotkategorijaVozila> rootPotkategorija = criteriaQuery.from(PotkategorijaVozila.class);
        Join<PotkategorijaVozila, Vozilo> joinVozila = rootPotkategorija.join("vozila", JoinType.LEFT);

        criteriaQuery.multiselect(
                rootPotkategorija.get("naziv").alias("PotkategorijaVozila"),
                criteriaBuilder.count(joinVozila.get("voziloId")).alias("BrojEvaluiranihVozila")
        );

        criteriaQuery.groupBy(rootPotkategorija.get("naziv"));
        criteriaQuery.where(
                criteriaBuilder.between(
                        joinVozila.get("datumPoslednjeEvaluacije").as(LocalDate.class),
                        LocalDate.parse("2023-01-01"),
                        LocalDate.parse("2023-12-31")
                )
        );

        List<Object[]> result = session.createQuery(criteriaQuery).getResultList();
        for (Object[] row : result) {
            String potkategorijaVozila = (String) row[0];
            Long brojEvaluiranihVozila = (Long) row[1];

            System.out.println("Potkategorija vozila: " + potkategorijaVozila);
            System.out.println("Broj evaluiranih vozila: " + brojEvaluiranihVozila);
        }

        session.getTransaction().commit();

    }
}
