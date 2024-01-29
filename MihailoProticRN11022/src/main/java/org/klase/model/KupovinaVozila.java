package org.klase.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "KupovinaVozila")
public class KupovinaVozila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KupovinaID")
    private Long kupovinaID;

    @ManyToOne
    @JoinColumn(name = "GarazaID")
    private Garaza garaza;

    @ManyToOne
    @JoinColumn(name = "PreduzeceID")
    private PreduzeceZaProdajuVozila preduzece;

    @Column(name = "DatumKupovine", nullable = false)
    private Date datumKupovine;

    @Column(name = "BrojVozila", nullable = false)
    private Integer brojVozila;

    @Column(name = "PotkategorijaVozila", columnDefinition = "TEXT")
    private String potkategorijaVozila;

}