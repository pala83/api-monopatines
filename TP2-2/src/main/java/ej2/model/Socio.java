package ej2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "socio")
@Data
public class Socio {
    @Id
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId // mapea (transforma) el ID de esta clase con la FK proveniente del a persona
    private Persona persona;

    @Column(name = "type", nullable = false)
    private String type;
}
