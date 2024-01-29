package org.klase.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CeneKarata")
public class CenaKarte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CenaID")
    private Long cenaID;

    @ManyToOne
    @JoinColumn(name = "KategorijaID")
    private KategorijaPeriodaVazenja kategorija;

    @ManyToOne
    @JoinColumn(name = "PovlascenaID")
    private PovlascenaKategorija povlascenaKategorija;

    @Column(name = "Cena", nullable = false)
    private BigDecimal cena;

}