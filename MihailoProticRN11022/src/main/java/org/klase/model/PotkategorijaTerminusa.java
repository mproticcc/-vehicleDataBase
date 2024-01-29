package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "PotkategorijeTerminusa")
public class PotkategorijaTerminusa {
    @EmbeddedId
    private PotkategorijaTerminusaId id;

    @ManyToOne
    @JoinColumn(name = "TerminusID")
    @MapsId("terminusID")
    private Terminus terminus;

    @Column(name = "PotkategorijaVozila", nullable = false)
    private String potkategorijaVozila;
}