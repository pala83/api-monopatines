package microservicio.facturacion.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.facturacion.entity.Subscripcion;
import microservicio.facturacion.entity.EstadoSubscripcion;
import microservicio.facturacion.entity.Vigencia;
import microservicio.facturacion.repository.SubscripcionRepository;

public class PopulatedSubscripciones extends Populated<Subscripcion> {
    private SubscripcionRepository subscripcionRepository;

    public PopulatedSubscripciones(Path filePath, SubscripcionRepository subscripcionRepository) {
        super(filePath);
        this.subscripcionRepository = subscripcionRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<Subscripcion> subscripciones = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();

        for(CSVRecord record : records){
            try {
                Subscripcion s = new Subscripcion();
                s.setIdCuenta(Long.parseLong(record.get("idCuenta")));
                s.setPeriodo(YearMonth.parse(record.get("periodo")));
                s.setFechaPago(LocalDate.parse(record.get("fechaPago")));
                s.setMonto(Long.parseLong(record.get("monto")));
                s.setEstado(EstadoSubscripcion.valueOf(record.get("estado")));

                // Crear y configurar la vigencia
                Vigencia vigencia = new Vigencia();
                vigencia.setInicio(LocalDate.parse(record.get("inicio")));
                String fin = record.get("fin");
                if(fin != null && !fin.isEmpty()) {
                    vigencia.setFin(LocalDate.parse(fin));
                }
                s.setVigencia(vigencia);

                subscripciones.add(s);
            } catch (Exception e) {
                System.err.println("[PopulatedSubscripciones.poblar] ERROR parsing registro de subscripción: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }

        try {
            if(!subscripciones.isEmpty()){
                this.subscripcionRepository.saveAll(subscripciones);
                System.out.println("[PopulatedSubscripciones.poblar] Se han poblado " + subscripciones.size() + " subscripciones.");
            } else {
                System.out.println("[PopulatedSubscripciones.poblar] No hay subscripciones para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedSubscripciones.poblar] ERROR guardando subscripciones: " + e.getMessage());
        }
    }
}