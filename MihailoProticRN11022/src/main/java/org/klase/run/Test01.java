package org.klase.run;

import org.hibernate.Session;
import org.klase.model.Vagon;
import org.klase.model.VozilaIVagoni;
import org.klase.model.Vozilo;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;

import javax.persistence.criteria.*;

public class Test01 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Vozilo> rootVozilo = criteriaQuery.from(Vozilo.class);
        Root<Vagon> rootVagon = criteriaQuery.from(Vagon.class);
        Join<Vozilo, VozilaIVagoni> joinVozilaIVagoni = rootVozilo.join("vagoni", JoinType.LEFT);
        Join<Vagon, VozilaIVagoni> joinVozilaIVagoniVagon = rootVagon.join("vozila", JoinType.LEFT);

        criteriaQuery.multiselect(
                criteriaBuilder.sum(rootVozilo.get("mestaZaSedenje").as(Integer.class)),
                criteriaBuilder.sum(rootVozilo.get("mestaZaStajanje").as(Integer.class)),
                criteriaBuilder.sum(rootVagon.get("ukupnoMesta").as(Integer.class))
        );

        Object[] result = session.createQuery(criteriaQuery).getSingleResult();
        int ukupnoMesta = (int) result[0];
        int mestaZaSedenje = (int) result[1];
        int mestaZaStajanje = (int) result[2];

        System.out.println("Ukupno mesta: " + ukupnoMesta);
        System.out.println("Mesta za sedenje: " + mestaZaSedenje);
        System.out.println("Mesta za stajanje: " + mestaZaStajanje);

        session.getTransaction().commit();
    }

}
