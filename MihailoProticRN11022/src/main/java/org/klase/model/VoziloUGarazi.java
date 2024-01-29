package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "VozilaUGarazi")
public class VoziloUGarazi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VoziloUGaraziID")
    private Long voziloUGaraziID;

    @ManyToOne
    @JoinColumn(name = "VoziloID")
    private Vozilo vozilo;

    @ManyToOne
    @JoinColumn(name = "GarazaID")
    private Garaza garaza;

    @Column(name = "OznakaGaraze")
    private String oznakaGaraze;

    @Column(name = "MestoUGarazi")
    private Integer mestoUGarazi;
}
