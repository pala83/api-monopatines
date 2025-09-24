package org.db.entities;

import java.sql.Date;

public class Persona {
    private Integer id;
    private Integer dni;
    private String nombre;
    private String apellido;
    private String email;
    private Date fechaNacimiento;

    public Persona(Integer id, Integer dni, String nombre, String apellido, String email, Date fechaNacimiento) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getId() {
        return id;
    }
    public Integer getDni() {
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
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    @Override
    public String toString() {
        return String.format("| %-5s | %-10s | %-15s | %-15s | %-35s | %-15s |", id, dni, nombre, apellido, email, fechaNacimiento);
    }
}
