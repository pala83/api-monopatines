package practico.integrador.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long idCuenta;
    @Column(nullable = false)
    private Double monto;
    @Column(nullable = false)
    private LocalDateTime fechaPago;
    @Column(nullable = false)
    private String medio; // Ej: MercadoPago, tarjeta, etc.
}
