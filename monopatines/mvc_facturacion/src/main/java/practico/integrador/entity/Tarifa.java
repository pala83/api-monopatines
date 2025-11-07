package practico.integrador.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarifas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double precioPorMinuto;
    @Column(nullable = false)
    private Double precioPausaPorMinuto;
    @Column(nullable = false)
    private LocalDate fechaInicioVigencia;
}
