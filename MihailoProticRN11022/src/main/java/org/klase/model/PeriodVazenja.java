package org.klase.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PeriodiVazenja")
public class PeriodVazenja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PeriodID")
    private Long periodID;

    @ManyToOne
    @JoinColumn(name = "KategorijaID")
    private KategorijaPeriodaVazenja kategorija;

    @Column(name = "Cena", nullable = false)
    private BigDecimal cena;
}
