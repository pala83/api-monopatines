package microservicio.monopatin.entity;

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
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private double latitud;
    @Column(nullable = false)
    private double longitud;

    @OneToMany(mappedBy = "paradaActual", cascade = CascadeType.ALL)
    private List<Monopatin> monopatines;
}
