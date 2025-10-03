package Integrador.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    name = "INSCRIPCION",
    uniqueConstraints = @UniqueConstraint(columnNames = {"estudiante", "carrera"})
)
@Data
public class Inscripcion {
    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrera", nullable = false)
    private Carrera carrera;

    @Column(name = "inscripcion", nullable = false)
    private Integer inscripcion;

    @Column(name = "graduacion")
    private Integer graduacion;

    @Column(name = "antiguedad")
    private Integer antiguedad;
}
