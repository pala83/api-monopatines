package microservicio.usuario.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Data
@NoArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cuenta")
    private Long id;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    @Column(name = "activa")
    private boolean activa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoCuenta tipo = TipoCuenta.BASICA;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cuentas")
    @JsonBackReference
    private List<Usuario> usuarios;


}
