package microservicio.monopatin.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.monopatin.entity.EstadoMonopatin;
import microservicio.monopatin.entity.Monopatin;
import microservicio.monopatin.entity.Parada;
import microservicio.monopatin.entity.Ubicacion;
import microservicio.monopatin.repository.MonopatinRepository;
import microservicio.monopatin.repository.ParadaRepository;

public class PopulatedMonopatines extends Populated<Monopatin> {
    private MonopatinRepository monopatinRepository;
    private ParadaRepository paradaRepository;

    public PopulatedMonopatines(Path filePath, MonopatinRepository monopatinRepository, ParadaRepository paradaRepository) {
        super(filePath);
        this.monopatinRepository = monopatinRepository;
        this.paradaRepository = paradaRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        List<Monopatin> monopatines = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord record : records){
            try{ 
                Monopatin m = new Monopatin();
                Parada p = this.paradaRepository.findById(Long.parseLong(record.get("parada"))).orElse(null);
                if(p == null){
                    System.err.println("[PopulatedMonopatines.poblar] Parada con id " + record.get("parada") + " no encontrada.");
                } else {
                    m.setMarca(record.get("marca"));
                    m.setCodigoQR(record.get("codigoQR"));
                    m.setKmTotales(Double.parseDouble(record.get("kmTotales")));
                    m.setEstado(EstadoMonopatin.valueOf(record.get("estado")));
                    Ubicacion u = new Ubicacion(
                        Double.parseDouble(record.get("latitud")),
                        Double.parseDouble(record.get("longitud"))
                    );
                    m.setUbicacionActual(u);
                    m.setParadaActual(p);
                    monopatines.add(m);
                }
            } catch (Exception e) {
                System.err.println("[PopulatedMonopatines.poblar] ERROR parsing registro de monopatín: " + record.toString());
            }
        }
        try {
            if(!monopatines.isEmpty()){
                this.monopatinRepository.saveAll(monopatines);
                System.out.println("[PopulatedMonopatines.poblar] Se han poblado " + monopatines.size() + " monopatines.");
            } else {
                System.out.println("[PopulatedMonopatines.poblar] No hay monopatines para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedMonopatines.poblar] ERROR al guardar monopatines: " + e.getMessage());
        }
    }
}
