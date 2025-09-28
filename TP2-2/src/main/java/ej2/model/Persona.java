package ej2.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "persona")
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "anios")
    private Integer edad;

    @ManyToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "jugadores") // Mapea la relacion ManyToMany con Turno asignado al atributo "jugadores"
    private List<Turno> turnos;
}
