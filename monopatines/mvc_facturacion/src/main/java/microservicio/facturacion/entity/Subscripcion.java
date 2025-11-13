package microservicio.facturacion.entity;

import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Subscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_cuenta", nullable = false)
    private Long idCuenta;

    @Column(name = "periodo", nullable = false)
    private YearMonth periodo;
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;
    @Column(name = "monto", nullable = false)
    private Long monto;
    @Enumerated(EnumType.STRING)
    private EstadoSubscripcion estado = EstadoSubscripcion.ACTIVA;

    @Embedded
    private Vigencia vigencia;
}

