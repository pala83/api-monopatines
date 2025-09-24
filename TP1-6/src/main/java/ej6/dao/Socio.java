package ej6.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Socio {
    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Persona persona;

    @Column
    private String type;

    public Socio() {
        super();
    }

    public Socio(Persona persona, String type) {
        super();
        this.persona = persona;
        this.type = type;
    }

    public Persona getPersona() {
        return persona;
    }
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
            return "Socio [persona=" + persona + ", type=" + type + "]";
    }
}
