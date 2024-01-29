package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "KategorijeVozaca")
public class KategorijaVozaca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KategorijeVozacaID")
    private Long id;

    @Column(name = "Naziv", nullable = false)
    private String naziv;
}