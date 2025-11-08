package microservicio.monopatin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Monopatin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_monopatin")
    private Long id;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "codigo_qr", nullable = false)
    private String codigoQR;

    @Column(name = "km_totales", nullable = false)
    private double kmTotales = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMonopatin estado;

    @Embedded
    private Ubicacion ubicacionActual;

    @ManyToOne
    @JoinColumn(name = "parada_id")
    private Parada paradaActual;
}
