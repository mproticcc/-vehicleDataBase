package org.klase.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "RedVoznje")
public class RedVoznje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RedVoznjeID")
    private Long redVoznjeID;

    @ManyToOne
    @JoinColumn(name = "SmerID")
    private Smer smer;

    @Column(name = "VremePolaska", nullable = false)
    private Time vremePolaska;
}