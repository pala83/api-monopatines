package microservicio.facturacion.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
