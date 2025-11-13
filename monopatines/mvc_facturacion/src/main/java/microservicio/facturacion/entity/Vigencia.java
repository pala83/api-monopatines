package microservicio.facturacion.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vigencia {
    
    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDate inicio;
    
    @Column(name = "fecha_fin_vigencia")
    private LocalDate fin;

    public boolean esVigenteEn(LocalDate fecha) {
        if (this.fin == null) {
            return !fecha.isBefore(inicio);
        }
        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }

    public boolean esVigenteActualmente() {
        return esVigenteEn(LocalDate.now());
    }
}
