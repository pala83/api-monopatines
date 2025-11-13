package microservicio.mantenimiento.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ControlMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kilometraje")
    private Double kilometraje;
    @Column(name = "uso_minutos")
    private Long usoMinutos;
    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "ultimo_mantenimiento")
    private LocalDateTime ultimoMantenimiento = LocalDateTime.now();
}
