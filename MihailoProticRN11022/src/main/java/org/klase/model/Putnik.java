package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "Putnici")
public class Putnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PutnikID")
    private Long putnikID;

    @Column(name = "Ime", nullable = false)
    private String ime;

    @Column(name = "Prezime", nullable = false)
    private String prezime;

    public Long getPutnikID() {
        return putnikID;
    }

    public void setPutnikID(Long putnikID) {
        this.putnikID = putnikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
