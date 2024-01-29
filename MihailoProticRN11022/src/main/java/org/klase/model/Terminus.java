package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Terminusi")
public class Terminus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TerminusID")
    private Long terminusID;

    @ManyToOne
    @JoinColumn(name = "StajalisteID")
    private Stajaliste stajaliste;

    @ManyToOne
    @JoinColumn(name = "SmerID")
    private Smer smer;
}