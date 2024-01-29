package org.klase.model;
import javax.persistence.*;

@Entity
@Table(name = "RadnoVremeVozaca")
public class RadnoVremeVozaca {
    @Id
    @Column(name = "VozacID")
    private Long vozacID;

    @Column(name = "RadniSatiDan")
    private Integer radniSatiDan;

    @Column(name = "RadniSatiSubota")
    private Integer radniSatiSubota;

    @Column(name = "RadniSatiNedelja")
    private Integer radniSatiNedelja;

    @OneToOne
    @JoinColumn(name = "VozacID")
    @MapsId
    private Vozac vozac;
}