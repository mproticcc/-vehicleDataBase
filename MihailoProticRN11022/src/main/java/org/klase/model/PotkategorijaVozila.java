package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "PotkategorijaVozila")
public class PotkategorijaVozila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PotkategorijaVozilaID")
    private Long id;

    @Column(name = "Naziv", nullable = false)
    private String naziv;
}
