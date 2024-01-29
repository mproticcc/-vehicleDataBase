package org.klase.run;

import org.hibernate.Session;
import org.klase.model.Garaza;
import org.klase.model.PotkategorijaVozila;
import org.klase.model.Vozilo;
import org.klase.model.VoziloUGarazi;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;
import javax.persistence.criteria.*;
import java.util.List;

public class Test05 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Garaza> rootGaraze = criteriaQuery.from(Garaza.class);
        Join<VoziloUGarazi, Vozilo> joinVozila = rootGaraze.join("vozilaUGarazi", JoinType.INNER);
        Join<Vozilo, PotkategorijaVozila> joinPotkategorija = joinVozila.join("potkategorijaVozila");

        criteriaQuery.multiselect(
                rootGaraze.get("garazaID"),
                rootGaraze.get("adresa").alias("adresaGaraze"),
                rootGaraze.get("kapacitet").alias("kapacitetGaraze"),
                criteriaBuilder.countDistinct(
                        criteriaBuilder.selectCase()
                                .when(criteriaBuilder.equal(joinPotkategorija.get("potkategorijaVozilaID"), 1),
                                        joinVozila.get("voziloID"))
                ).as(Integer.class).alias("brojKlasicnihTramvaja"),
                criteriaBuilder.countDistinct(
                        criteriaBuilder.selectCase()
                                .when(criteriaBuilder.equal(joinPotkategorija.get("potkategorijaVozilaID"), 2),
                                        joinVozila.get("voziloID"))
                ).as(Integer.class).alias("brojVagona"),
                criteriaBuilder.countDistinct(joinVozila.get("voziloID")).as(Integer.class).alias("ukupnoTramvajaIVagona"),
                criteriaBuilder.greaterThanOrEqualTo(
                        criteriaBuilder.countDistinct(
                                criteriaBuilder.selectCase()
                                        .when(criteriaBuilder.equal(joinPotkategorija.get("potkategorijaVozilaID"), 2),
                                                joinVozila.get("voziloID"))
                        ).as(Integer.class),
                        criteriaBuilder.countDistinct(
                                criteriaBuilder.selectCase()
                                        .when(criteriaBuilder.equal(joinPotkategorija.get("potkategorijaVozilaID"), 1),
                                                joinVozila.get("voziloID"))
                        ).as(Integer.class)
                ).as(Integer.class).alias("brojVagonaVeceIliJednakoTramvajima")
        );

        criteriaQuery.groupBy(
                rootGaraze.get("garazaID"),
                rootGaraze.get("adresa"),
                rootGaraze.get("kapacitet")
        );

        List<Object[]> result = session.createQuery(criteriaQuery).getResultList();

        for (Object[] row : result) {
            System.out.println("GarazaID: " + row[0]);
            System.out.println("Adresa garaze: " + row[1]);
            System.out.println("Kapacitet garaze: " + row[2]);
            System.out.println("Broj klasicnih tramvaja: " + row[3]);
            System.out.println("Broj vagona: " + row[4]);
            System.out.println("Ukupno tramvaja i vagona: " + row[5]);
            System.out.println("Broj vagona vece ili jednako tramvajima: " + row[6]);
        }

        session.getTransaction().commit();
    }

}