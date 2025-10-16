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
@Table(name = "ESTUDIANTE")
@Data
public class Estudiante {
    @Id
    @Column(name = "dni")
    private Integer dni;
    
    @Column(name = "lu", nullable = false, unique = true)
    private Integer lu;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "genero")
    private String genero;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "ciudad")
    private String ciudad;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones;
}
