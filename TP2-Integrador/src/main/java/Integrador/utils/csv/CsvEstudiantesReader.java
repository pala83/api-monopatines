package Integrador.utils.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import Integrador.dto.EstudianteDTO;

public class CsvEstudiantesReader extends CsvReader<EstudianteDTO> {

    public CsvEstudiantesReader(Path filePath) {
        super(filePath);
    }

    public List<EstudianteDTO> getData() throws IOException, ParseException {
        Iterable<CSVRecord> records = this.read();
        List<EstudianteDTO> estudiantes = new LinkedList<>();
        for (CSVRecord record : records) {
            EstudianteDTO e = new EstudianteDTO(
                Integer.parseInt(record.get("DNI")),
                record.get("nombre"),
                record.get("apellido"),
                Integer.parseInt(record.get("edad")),
                record.get("genero"),
                record.get("ciudad"),
                Integer.parseInt(record.get("LU"))
            );
            estudiantes.add(e);
        }
        return estudiantes;
    }
}
