package ej6.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Timestamp fecha;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Persona> jugadores;

    public Turno() {
        super();
    }

    public Turno(Timestamp fecha) {
        super();
        this.fecha = fecha;
        this.jugadores = new ArrayList<>();
    }

    public Timestamp getFecha() {
        return fecha;
    }
    public int getId() {
        return id;
    }
    public List<Persona> getJugadores() {
        return List.copyOf(jugadores);
    }

    @Override
    public String toString() {
        return "Turno [id=" + id + ", fecha=" + fecha + ", jugadores=" + jugadores + "]";
    }

}
