package microservicio.viaje.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_viaje")
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Column(name = "id_monopatin", nullable = false)
    private Long idMonopatin;
    @Column(name = "id_cuenta", nullable = false)
    private Long idCuenta;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitud", column = @Column(name = "inicio_latitud", nullable = false)),
        @AttributeOverride(name = "longitud", column = @Column(name = "inicio_longitud", nullable = false))
    })
    private Ubicacion ubicacionInicio;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitud", column = @Column(name = "fin_latitud")),
        @AttributeOverride(name = "longitud", column = @Column(name = "fin_longitud"))
    })
    private Ubicacion ubicacionFin;

    @Column(name = "distancia_recorrida")
    private double distanciaRecorrida = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoViaje estado = EstadoViaje.EN_CURSO;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    private List<Pausa> pausas;

    public void agregarPausa(Pausa p) {
        this.pausas.add(p);
    }
}
