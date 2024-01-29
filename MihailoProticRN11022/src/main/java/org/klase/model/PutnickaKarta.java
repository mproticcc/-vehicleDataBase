package org.klase.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PutnickeKarte")
public class PutnickaKarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KartaID")
    private Long kartaID;

    @ManyToOne
    @JoinColumn(name = "PutnikID")
    private Putnik putnik;

    @ManyToOne
    @JoinColumn(name = "PeriodVazenjaID")
    private PeriodVazenja periodVazenja;

    @Column(name = "DatumIzdavanja", nullable = false)
    private Date datumIzdavanja;
}