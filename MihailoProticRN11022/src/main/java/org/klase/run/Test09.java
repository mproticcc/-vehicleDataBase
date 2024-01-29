package org.klase.run;

import org.hibernate.Session;
import org.klase.model.PeriodVazenja;
import org.klase.model.PovlascenaKategorija;
import org.klase.model.PutnickaKarta;
import org.klase.model.Putnik;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.time.LocalDate;


public class Test09 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Putnik> rootPutnici = criteriaQuery.from(Putnik.class);
        Join<PutnickaKarta, Putnik> joinPutnickeKarte = rootPutnici.join("putnickeKarte");
        Join<PeriodVazenja, PutnickaKarta> joinPeriodiVazenja = joinPutnickeKarte.join("periodiVazenja");
        Join<PovlascenaKategorija, PeriodVazenja> joinPovlasceneKategorije = joinPeriodiVazenja.join("povlasceneKategorije");

        Predicate condition = criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(joinPutnickeKarte.get("datumIzdavanja").as(Date.class), Date.valueOf(LocalDate.now().minusYears(1))),
                criteriaBuilder.equal(joinPovlasceneKategorije.get("naziv"), "DojiLja")
        );

        criteriaQuery.select(criteriaBuilder.countDistinct(rootPutnici.get("putnikID")));
        criteriaQuery.where(condition);

        Long brojDojilja = session.createQuery(criteriaQuery).getSingleResult();

        System.out.println("Broj dojilja: " + brojDojilja);

        session.getTransaction().commit();

    }
}
