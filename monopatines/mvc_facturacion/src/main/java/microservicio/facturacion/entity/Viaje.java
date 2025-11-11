package microservicio.facturacion.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;

@Entity
public class Viaje {
    private Long id;
    private Long idMonopatin;
    private Long idCuenta;
    private Double kmRecorridos;    
}
