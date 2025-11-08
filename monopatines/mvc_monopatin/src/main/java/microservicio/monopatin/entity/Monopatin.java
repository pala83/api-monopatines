package microservicio.monopatin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "monopatines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigoQR;

    @Column(nullable = false)
    private double kmTotales;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMonopatin estado;

    @ManyToOne
    @JoinColumn(name = "parada_id")
    private Parada paradaActual;
}
