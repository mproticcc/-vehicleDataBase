package org.klase.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Vozila")
public class Vozilo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VoziloID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "KategorijaID", nullable = false)
    private Kategorija kategorija;

    @ManyToOne
    @JoinColumn(name = "PotkategorijaVozilaID", nullable = false)
    private PotkategorijaVozila potkategorijaVozila;

    @Column(name = "MestaZaSedenje", nullable = false)
    private Integer mestaZaSedenje;

    @Column(name = "MestaZaStajanje", nullable = false)
    private Integer mestaZaStajanje;

    @Column(name = "DatumProizvodnje", nullable = false)
    private Date datumProizvodnje;

    @Column(name = "DatumPoslednjeEvaluacije", nullable = false)
    private Date datumPoslednjeEvaluacije;
}