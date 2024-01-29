package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Stajalista")
public class Stajaliste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StajalisteID")
    private Long stajalisteID;

    @Column(name = "Kod", nullable = false, unique = true)
    private String kod;

    @Column(name = "Naziv", nullable = false)
    private String naziv;

    @Column(name = "TipStajalista", nullable = false)
    private String tipStajalista;

    @ManyToOne
    @JoinColumn(name = "ZonaID")
    private Zona zona;
}