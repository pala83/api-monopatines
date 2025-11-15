package microservicio.facturacion.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.facturacion.entity.Carga;
import microservicio.facturacion.repository.CargaRepository;

public class PopulatedCargas extends Populated<Carga> {
    private CargaRepository cargaRepository;

    public PopulatedCargas(Path filePath, CargaRepository cargaRepository) {
        super(filePath);
        this.cargaRepository = cargaRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<Carga> cargas = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();

        for(CSVRecord record : records){
            try {
                Carga c = new Carga();
                c.setViajeId(Long.parseLong(record.get("viajeId")));
                c.setCuentaId(Long.parseLong(record.get("cuentaId")));
                c.setMontoTotal(Long.parseLong(record.get("montoTotal")));
                c.setCargaNormal(Long.parseLong(record.get("cargaNormal")));
                c.setCargaPausaExtendida(Long.parseLong(record.get("cargaPausaExtendida")));
                c.setDuracionMinutos(Long.parseLong(record.get("duracionMinutos")));
                c.setDuracionPausaMinutos(Long.parseLong(record.get("duracionPausaMinutos")));
                c.setKmRecorridos(Double.parseDouble(record.get("kmRecorridos")));
                c.setFechaCarga(LocalDateTime.parse(record.get("fechaCarga").trim()));

                cargas.add(c);
            } catch (Exception e) {
                System.err.println("[PopulatedCargas.poblar] ERROR parsing registro de carga: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }

        try {
            if(!cargas.isEmpty()){
                this.cargaRepository.saveAll(cargas);
                System.out.println("[PopulatedCargas.poblar] Se han poblado " + cargas.size() + " cargas.");
            } else {
                System.out.println("[PopulatedCargas.poblar] No hay cargas para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedCargas.poblar] ERROR guardando cargas: " + e.getMessage());
        }
    }
}