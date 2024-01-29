package org.klase.run;

import org.hibernate.Session;
import org.klase.model.Garaza;
import org.klase.model.PreduzeceZaProdajuVozila;
import org.klase.model.Vozilo;
import org.klase.model.VoziloUGarazi;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.*;
import java.util.List;

public class Test06 implements Test {

    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Garaza> rootGaraza = criteriaQuery.from(Garaza.class);
        Join<VoziloUGarazi, Vozilo> joinVozila = rootGaraza.join("vozilaUGarazi", JoinType.LEFT);
        Join<Vozilo, PreduzeceZaProdajuVozila> joinPreduzece = joinVozila.join("preduzeceZaProdajuVozila", JoinType.LEFT);

        criteriaQuery.multiselect(
                rootGaraza.get("garazaID").as(Integer.class),
                rootGaraza.get("adresa").as(String.class).alias("adresaGaraze"),
                rootGaraza.get("kapacitet").as(Integer.class).alias("kapacitetGaraze"),
                joinPreduzece.get("preduzeceID").as(Integer.class),
                joinPreduzece.get("naziv").as(String.class).alias("nazivProdavca"),
                joinPreduzece.get("email").as(String.class).alias("kontaktEmail"),
                joinPreduzece.get("kontaktTelefon").as(String.class).alias("kontaktTelefon"),
                criteriaBuilder.diff(
                        rootGaraza.get("kapacitet"),
                        criteriaBuilder.coalesce(criteriaBuilder.countDistinct(joinVozila.get("voziloID")), 0)
                ).as(Integer.class).alias("brojPraznihMesta")
        );

        criteriaQuery.groupBy(
                rootGaraza.get("garazaID"),
                rootGaraza.get("adresa"),
                rootGaraza.get("kapacitet"),
                joinPreduzece.get("preduzeceID"),
                joinPreduzece.get("naziv"),
                joinPreduzece.get("email"),
                joinPreduzece.get("kontaktTelefon")
        );

        criteriaQuery.having(
                criteriaBuilder.gt(
                        criteriaBuilder.diff(
                                rootGaraza.get("kapacitet"),
                                criteriaBuilder.coalesce(criteriaBuilder.countDistinct(joinVozila.get("voziloID")), 0)
                        ),
                        0
                )
        );

        criteriaQuery.orderBy(
                criteriaBuilder.asc(rootGaraza.get("garazaID")),
                criteriaBuilder.desc(
                        criteriaBuilder.diff(
                                rootGaraza.get("kapacitet"),
                                criteriaBuilder.coalesce(criteriaBuilder.countDistinct(joinVozila.get("voziloID")), 0)
                        )
                )
        );

        List<Object[]> result = session.createQuery(criteriaQuery).getResultList();

        for (Object[] row : result) {
            System.out.println("GarazaID: " + row[0]);
            System.out.println("Adresa garaze: " + row[1]);
            System.out.println("Kapacitet garaze: " + row[2]);
            System.out.println("PreduzeceID: " + row[3]);
            System.out.println("Naziv prodavca: " + row[4]);
            System.out.println("Kontakt email: " + row[5]);
            System.out.println("Kontakt telefon: " + row[6]);
            System.out.println("Broj praznih mesta: " + row[7]);
        }

        session.getTransaction().commit();

    }
}
