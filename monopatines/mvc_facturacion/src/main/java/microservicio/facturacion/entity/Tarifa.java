package microservicio.facturacion.entity;

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
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "precio_por_minuto")
    private Double precioPorMinuto;
    @Column(name = "precio_por_minuto_extendido")
    private Double precioPorMinutoExtendido;
    @Column(name = "mensualidad_premium")
    private Double mensualidadPremium;

}
