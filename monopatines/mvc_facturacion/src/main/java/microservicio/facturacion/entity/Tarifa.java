package microservicio.facturacion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarifas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "precio_por_minuto", nullable = false)
    private Double precioPorMinuto;

    @Column(name = "precio_pausa_por_minuto", nullable = false)
    private Double precioPausaPorMinuto;

    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDateTime fechaInicioVigencia;
}
