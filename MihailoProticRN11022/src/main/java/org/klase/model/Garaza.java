package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Garaze")
public class Garaza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GarazaID")
    private Long garazaID;

    @Column(name = "Kapacitet", nullable = false)
    private Integer kapacitet;

    @Column(name = "Adresa", nullable = false)
    private String adresa;

    @ManyToOne
    @JoinColumn(name = "TipGarazeID")
    private TipGaraze tipGaraze;
}