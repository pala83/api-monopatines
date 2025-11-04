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

    private Long idCuenta;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String medio; // Ej: MercadoPago, tarjeta, etc.
}
