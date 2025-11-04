package practico.integrador.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "paradas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double latitud;
    private double longitud;

    @OneToMany(mappedBy = "paradaActual")
    private List<Monopatin> monopatines;
}
