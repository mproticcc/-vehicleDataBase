package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Linije")
public class Linija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LinijaID")
    private Long linijaID;

    @Column(name = "RedniBroj", nullable = false, unique = true)
    private String redniBroj;

    @Column(name = "TipVozila", nullable = false)
    private String tipVozila;

    @Column(name = "Smena", nullable = false)
    private String smena;

    @Column(name = "BrojSmerova", nullable = false)
    private Integer brojSmerova;

}