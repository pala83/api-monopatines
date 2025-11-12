package microservicio.usuario.utils.dataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import microservicio.usuario.entity.Cuenta;
import microservicio.usuario.repository.CuentaRepository;


public class PopulatedCuentas extends Populated<Cuenta> {
    private CuentaRepository cuentaRepository;
    
    public PopulatedCuentas(Path filePath, CuentaRepository cuentaRepository) {
        super(filePath);
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public void poblar() throws IOException, ParseException {
        List<Cuenta> cuentas = new ArrayList<>();
        Iterable<CSVRecord> records = this.read();
        for(CSVRecord record : records){
            try {
                Cuenta c = new Cuenta();
                c.setSaldo(Double.parseDouble(record.get("saldo")));
                c.setFechaCreacion(Timestamp.valueOf(record.get("fechaCreacion").trim()));
                c.setActiva(Boolean.parseBoolean(record.get("activa")));
                cuentas.add(c);
            } catch (Exception e) {
                System.err.println("[PopulatedCuentas.poblar] ERROR parsing registro de cuenta (formato esperado fechaCreacion=yyyy-MM-dd HH:mm:ss): " + record.toString() + " | Causa: " + e.getMessage());
            }
        }
        try {
            if(!cuentas.isEmpty()){
                this.cuentaRepository.saveAll(cuentas);
                System.out.println("[PopulatedCuentas.poblar] Se han poblado " + cuentas.size() + " cuentas.");
            } else {
                System.out.println("[PopulatedCuentas.poblar] No hay cuentas para poblar.");
            }
        } catch (Exception e) {
            System.err.println("[PopulatedCuentas.poblar] ERROR guardando cuentas: " + e.getMessage());
        }
    }

}
