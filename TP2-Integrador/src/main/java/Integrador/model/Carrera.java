package Integrador.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carrera")
@Data
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "duracion")
    private int duracion;
}
