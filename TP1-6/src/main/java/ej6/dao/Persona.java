package ej6.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Persona {
    @Id
    private int id;
    @Column(unique = true, nullable = false)
    private int dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private int nacimiento;
    @Column(nullable = false)
    private String email;
    @ManyToOne
    private Direccion direccion;

    public Persona() {
        super();
    }

    public Persona(int id, int dni, String nombre, String apellido, int nacimiento, String email, Direccion direccion) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacimiento = nacimiento;
        this.email = email;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }
    public int getDni() {
        return dni;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEmail() {
        return email;
    }
    public Direccion getDireccion() {
        return direccion;
    }
    public int getNacimiento() {
        return nacimiento;
    }

    @Override
    public String toString() {
        return "Persona [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", nacimiento=" + nacimiento
                + ", email=" + email + ", direccion=" + direccion + "]";
    }
}
