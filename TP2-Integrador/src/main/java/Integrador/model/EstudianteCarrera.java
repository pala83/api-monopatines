package Integrador.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "estudianteCarrera")
@Data
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dni") // Mapea la relacion ManyToMany con Turno asignado al atributo "jugadores"
    private List<Estudiante> estudiantes;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "id") // Mapea la relacion ManyToMany con Turno asignado al atributo "jugadores"
    private List<Carrera> carreras;

    @Column(name = "inscripcion")
    private int inscripcion;

    @Column(name = "gruadacion")
    private int gruadacion;

    @Column(name = "antiguedad")
    private int antiguedad;
}
