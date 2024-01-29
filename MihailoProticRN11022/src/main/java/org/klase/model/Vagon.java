package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Vagon")
public class Vagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VagonID")
    private Long vagonID;

    @Column(name = "MestaZaSedenje", nullable = false)
    private Integer mestaZaSedenje;

    @Column(name = "MestaZaStajanje", nullable = false)
    private Integer mestaZaStajanje;

}