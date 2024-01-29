package org.klase.model.utility;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.klase.model.*;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    private static SessionFactory sessionAnnotationFactory;

    private static SessionFactory sessionJavaConfigFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(CenaKarte.class);
            configuration.addAnnotatedClass(DozivotnaKarta.class);
            configuration.addAnnotatedClass(Garaza.class);
            configuration.addAnnotatedClass(Kategorija.class);
            configuration.addAnnotatedClass(KategorijaPeriodaVazenja.class);
            configuration.addAnnotatedClass(KupovinaVozila.class);
            configuration.addAnnotatedClass(Linija.class);
            configuration.addAnnotatedClass(PeriodVazenja.class);
            configuration.addAnnotatedClass(PotkategorijaTerminusa.class);
            configuration.addAnnotatedClass(PotkategorijaTerminusaId.class);
            configuration.addAnnotatedClass(PotkategorijaVozila.class);
            configuration.addAnnotatedClass(PovlascenaKategorija.class);
            configuration.addAnnotatedClass(PreduzeceZaProdajuVozila.class);
            configuration.addAnnotatedClass(PutnickaKarta.class);
            configuration.addAnnotatedClass(Putnik.class);
            configuration.addAnnotatedClass(RadnoVremeVozaca.class);
            configuration.addAnnotatedClass(RedVoznje.class);
            configuration.addAnnotatedClass(Smer.class);
            configuration.addAnnotatedClass(Stajaliste.class);
            configuration.addAnnotatedClass(Terminus.class);
            configuration.addAnnotatedClass(TipGaraze.class);
            configuration.addAnnotatedClass(Vozac.class);
            configuration.addAnnotatedClass(Vozilo.class);
            configuration.addAnnotatedClass(VoziloUGarazi.class);
            configuration.addAnnotatedClass(Zona.class);
            configuration.addAnnotatedClass(Vagon.class);
            configuration.addAnnotatedClass(VozilaIVagoni.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory
                    = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate-annotation.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionJavaConfigFactory() {
        try {
            Configuration configuration = getConfiguration();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        Properties props = new Properties();
        props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        props.put("hibernate.connection.url", "jdbc:mysql://localhost/TestDB");
        props.put("hibernate.current_session_context_class", "thread");
        configuration.setProperties(props);

        configuration.addAnnotatedClass(CenaKarte.class);
        configuration.addAnnotatedClass(DozivotnaKarta.class);
        configuration.addAnnotatedClass(Garaza.class);
        configuration.addAnnotatedClass(Kategorija.class);
        configuration.addAnnotatedClass(KategorijaPeriodaVazenja.class);
        configuration.addAnnotatedClass(KupovinaVozila.class);
        configuration.addAnnotatedClass(Linija.class);
        configuration.addAnnotatedClass(PeriodVazenja.class);
        configuration.addAnnotatedClass(PotkategorijaTerminusa.class);
        configuration.addAnnotatedClass(PotkategorijaTerminusaId.class);
        configuration.addAnnotatedClass(PotkategorijaVozila.class);
        configuration.addAnnotatedClass(PovlascenaKategorija.class);
        configuration.addAnnotatedClass(PreduzeceZaProdajuVozila.class);
        configuration.addAnnotatedClass(PutnickaKarta.class);
        configuration.addAnnotatedClass(Putnik.class);
        configuration.addAnnotatedClass(RadnoVremeVozaca.class);
        configuration.addAnnotatedClass(RedVoznje.class);
        configuration.addAnnotatedClass(Smer.class);
        configuration.addAnnotatedClass(Stajaliste.class);
        configuration.addAnnotatedClass(Terminus.class);
        configuration.addAnnotatedClass(TipGaraze.class);
        configuration.addAnnotatedClass(Vozac.class);
        configuration.addAnnotatedClass(Vozilo.class);
        configuration.addAnnotatedClass(VoziloUGarazi.class);
        configuration.addAnnotatedClass(Zona.class);
        configuration.addAnnotatedClass(Vagon.class);
        configuration.addAnnotatedClass(VozilaIVagoni.class);
        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null)
            sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

    public static SessionFactory getSessionAnnotationFactory() {
        if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }

    public static SessionFactory getSessionJavaConfigFactory() {
        if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }

    private HibernateUtils() {

    }
}
