package org.klase.run;

import org.hibernate.Session;
import org.klase.model.DozivotnaKarta;
import org.klase.model.PutnickaKarta;
import org.klase.model.Putnik;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public class Test10 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Putnik> criteriaQuery = criteriaBuilder.createQuery(Putnik.class);
        Root<Putnik> rootPutnici = criteriaQuery.from(Putnik.class);
        Join<DozivotnaKarta, Putnik> joinDozivotneKarte = rootPutnici.join("dozivotneKarte", JoinType.INNER);
        Join<PutnickaKarta, Putnik> joinPutnickeKarte = rootPutnici.join("putnickeKarte", JoinType.LEFT);
        Predicate joinPeriodiVazenja = criteriaBuilder.and(
                criteriaBuilder.equal(joinPutnickeKarte.get("putnikID"), joinDozivotneKarte.get("povlascenaID")),
                criteriaBuilder.equal(joinPutnickeKarte.get("periodVazenjaID"), joinDozivotneKarte.get("dozivotnaID"))
        );

        Predicate condition = criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(joinDozivotneKarte.get("uslovGodina"), Year.now().getValue() - 10),
                criteriaBuilder.or(
                        criteriaBuilder.isNull(joinPutnickeKarte.get("kartaID")),
                        criteriaBuilder.lessThan(joinPutnickeKarte.get("datumIzdavanja").as(Date.class), Date.valueOf(LocalDate.now()))
                )
        );

        criteriaQuery.select(rootPutnici).distinct(true);
        criteriaQuery.where(condition);
        criteriaQuery.groupBy(rootPutnici.get("putnikID"));

        List<Putnik> dozivotneKartePutnici = session.createQuery(criteriaQuery).getResultList();

        for (Putnik putnik : dozivotneKartePutnici) {
            System.out.println("PutnikID: " + putnik.getPutnikID() + ", Ime: " + putnik.getIme() + ", Prezime: " + putnik.getPrezime());
        }

        session.getTransaction().commit();

    }
}
