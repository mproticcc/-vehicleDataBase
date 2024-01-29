package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Kategorije")
public class Kategorija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KategorijaID")
    private Long id;

    @Column(name = "Naziv", nullable = false)
    private String naziv;

}
