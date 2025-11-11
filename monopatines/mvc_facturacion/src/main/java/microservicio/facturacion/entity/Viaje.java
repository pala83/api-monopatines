package microservicio.facturacion.entity;

import jakarta.persistence.Entity;

@Entity
public class Viaje {
    private Long id;
    private Long idMonopatin;
    private Long idCuenta;
    private Double kmRecorridos;    
}
