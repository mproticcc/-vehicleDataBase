package org.klase.model;

import javax.persistence.*;

@Entity
@Table(name = "VozilaIVagoni")
public class VozilaIVagoni {
    @EmbeddedId
    private VozilaIVagoniId id;

    @ManyToOne
    @JoinColumn(name = "VoziloID", insertable = false, updatable = false)
    private Vozilo vozilo;

    @ManyToOne
    @JoinColumn(name = "VagonID", insertable = false, updatable = false)
    private Vagon vagon;
}

