package microservicio.facturacion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Viaje {
    @Id
    private Long id;
    private Long idMonopatin;
    private Long idCuenta;
    private Double kmRecorridos;    
}
