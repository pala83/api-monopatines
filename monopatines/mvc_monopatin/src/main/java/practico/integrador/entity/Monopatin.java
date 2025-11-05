package practico.integrador.entity;

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

    private String codigoQR;
    private double kmTotales;

    @Enumerated(EnumType.STRING)
    private EstadoMonopatin estado;

    @ManyToOne
    @JoinColumn(name = "parada_id")
    private Parada paradaActual;
}
