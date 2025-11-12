package microservicio.monopatin.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_parada")
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Embedded
    private Ubicacion ubicacion;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @OneToMany(mappedBy = "paradaActual", cascade = CascadeType.ALL)
    private List<Monopatin> monopatines;

    public void agregarMonopatin(Monopatin m) {
        this.monopatines.add(m);
    }
}
