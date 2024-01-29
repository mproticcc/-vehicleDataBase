package org.klase.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "DozivotneKarte")
public class DozivotnaKarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DozivotnaID")
    private Long dozivotnaID;

    @ManyToOne
    @JoinColumn(name = "PovlascenaID")
    private PovlascenaKategorija povlascenaKategorija;

    @Column(name = "UslovGodina", nullable = false)
    private Integer uslovGodina;

    @Column(name = "Cena", nullable = false)
    private BigDecimal cena;
}
