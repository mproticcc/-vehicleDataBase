package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Vozaci")
public class Vozac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VozacID")
    private Long id;

    @Column(name = "Ime", nullable = false)
    private String ime;

    @Column(name = "Prezime", nullable = false)
    private String prezime;

    @ManyToOne
    @JoinColumn(name = "KategorijeVozacaID")
    private KategorijaVozaca kategorijaVozaca;
}