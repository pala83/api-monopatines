package practico.integrador.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAlta;
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @ManyToMany(mappedBy = "cuentas")
    private List<Usuario> usuarios;
}
