package org.klase.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class PotkategorijaTerminusaId implements Serializable {
    @Column(name = "TerminusID")
    private Long terminusID;
}