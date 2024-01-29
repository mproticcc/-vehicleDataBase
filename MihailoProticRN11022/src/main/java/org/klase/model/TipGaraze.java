package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "TipoviGaraza")
public class TipGaraze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TipGarazeID")
    private Long tipGarazeID;

    @Column(name = "Naziv", nullable = false, unique = true)
    private String naziv;
}