package org.klase.run;

import org.hibernate.Session;
import org.klase.model.RadnoVremeVozaca;
import org.klase.model.utility.HibernateUtils;
import org.klase.run.abstraction.Test;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Test04 implements Test {
    @Override
    public void test() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<RadnoVremeVozaca> rootRadnoVremeVozaca = criteriaQuery.from(RadnoVremeVozaca.class);

        criteriaQuery.select(criteriaBuilder.count(rootRadnoVremeVozaca));
        criteriaQuery.where(criteriaBuilder.gt(
                criteriaBuilder.sum(
                        criteriaBuilder.sum(rootRadnoVremeVozaca.get("radniSatiDan"),
                                rootRadnoVremeVozaca.get("radniSatiSubota")),
                        rootRadnoVremeVozaca.get("radniSatiNedelja")),
                40));

        Long brojVozacaPreko40Sati = session.createQuery(criteriaQuery).getSingleResult();

        System.out.println("Broj vozača koji rade više od 40 sati nedeljno: " + brojVozacaPreko40Sati);

        session.getTransaction().commit();
    }


}
