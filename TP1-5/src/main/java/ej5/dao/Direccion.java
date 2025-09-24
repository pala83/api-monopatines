package ej5.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String ciudad;
    @Column
    private String calle;

    public Direccion() {
        super();
    }

    public Direccion(String ciudad, String calle) {
        this.ciudad = ciudad;
        this.calle = calle;
    }

    public int getId() {
        return id;
    }
    public String getCalle() {
        return calle;
    }
    public String getCiudad() {
        return ciudad;
    }
    @Override
    public String toString() {
        return "Direccion [id=" + id + ", ciudad=" + ciudad + ", calle=" + calle + "]";
    }

}
