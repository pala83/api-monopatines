package integrador.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CARRERA")
@Data
public class Carrera {
    @Id
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "duracion")
    private Integer duracion;
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones;
}