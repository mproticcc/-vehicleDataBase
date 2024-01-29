package org.klase.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VozilaIVagoniId implements Serializable {

    @Column(name = "VoziloID")
    private Long voziloID;

    @Column(name = "VagonID")
    private Long vagonID;

}