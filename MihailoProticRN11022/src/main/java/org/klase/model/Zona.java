package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Zone")
public class Zona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZonaID")
    private Long zonaID;

    @Column(name = "Kod", nullable = false, unique = true)
    private String kod;

    @Column(name = "Naziv", nullable = false)
    private String naziv;
}