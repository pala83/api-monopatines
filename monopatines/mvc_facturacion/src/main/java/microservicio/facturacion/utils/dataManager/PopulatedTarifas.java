package microservicio.facturacion.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.facturacion.entity.Tarifa;
import microservicio.facturacion.entity.Vigencia;
import microservicio.facturacion.repository.TarifaRepository;

public class PopulatedTarifas extends Populated<Tarifa> {
    private TarifaRepository tarifaRepository;

    public PopulatedTarifas(Path filePath, TarifaRepository tarifaRepository) {
        super(filePath);
        this.tarifaRepository = tarifaRepository;
    }

    @Override
    public void poblar() throws IOException {
        List<Tarifa> tarifas = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();

        for(CSVRecord record : records){
            try {
                Tarifa t = new Tarifa();
                t.setPrecioPorMinuto(Double.parseDouble(record.get("precioPorMinuto")));
                t.setPrecioPorMinutoExtendido(Double.parseDouble(record.get("precioPorMinutoExtendido")));
                t.setMensualidadPremium(Double.parseDouble(record.get("mensualidadPremium")));

                // Crear y configurar la vigencia
                Vigencia vigencia = new Vigencia();
                vigencia.setInicio(LocalDate.parse(record.get("inicio")));
                String fin = record.get("fin");
                if(fin != null && !fin.isEmpty()) {
                    vigencia.setFin(LocalDate.parse(fin));
                }
                t.setVigencia(vigencia);

                tarifas.add(t);
            } catch (Exception e) {
                System.err.println("[PopulatedTarifas.poblar] ERROR parsing registro de tarifa: " + record.toString() + " | Causa: " + e.getMessage());
            }
        }

        try {
            if(!tarifas.isEmpty()){
                this.tarifaRepository.saveAll(tarifas);
                System.out.println("[PopulatedTarifas.poblar] Se han poblado " + tarifas.size() + " tarifas.");
            } else {
                System.out.println("[PopulatedTarifas.poblar] No hay tarifas para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedTarifas.poblar] ERROR guardando tarifas: " + e.getMessage());
        }
    }
}