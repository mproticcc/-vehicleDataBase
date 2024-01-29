package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Smerovi")
public class Smer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SmerID")
    private Long smerID;

    @ManyToOne
    @JoinColumn(name = "LinijaID")
    private Linija linija;

    @Column(name = "BrojPolazaka", nullable = false)
    private Integer brojPolazaka;
}