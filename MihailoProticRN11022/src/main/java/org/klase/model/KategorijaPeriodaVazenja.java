package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "KategorijePeriodaVazenja")
public class KategorijaPeriodaVazenja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KategorijaID")
    private Long kategorijaID;

    @Column(name = "Naziv", nullable = false, unique = true)
    private String naziv;

    @Column(name = "Opis", nullable = false)
    private String opis;
}