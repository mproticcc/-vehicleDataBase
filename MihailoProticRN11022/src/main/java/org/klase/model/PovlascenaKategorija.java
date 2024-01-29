package org.klase.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PovlasceneKategorije")
public class PovlascenaKategorija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PovlascenaID")
    private Long povlascenaID;

    @Column(name = "Naziv", nullable = false, unique = true)
    private String naziv;

    @Column(name = "Popust", nullable = false)
    private BigDecimal popust;
}
