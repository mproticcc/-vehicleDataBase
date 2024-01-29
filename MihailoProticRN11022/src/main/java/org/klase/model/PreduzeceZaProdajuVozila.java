package org.klase.model;
import javax.persistence.*;

@Entity
@Table(name = "PreduzecaZaProdajuVozila")
public class PreduzeceZaProdajuVozila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PreduzeceID")
    private Long preduzeceID;

    @Column(name = "Naziv", nullable = false, unique = true)
    private String naziv;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "KontaktTelefon", nullable = false)
    private String kontaktTelefon;

    @Column(name = "VebSajt", columnDefinition = "TEXT")
    private String vebSajt;
}