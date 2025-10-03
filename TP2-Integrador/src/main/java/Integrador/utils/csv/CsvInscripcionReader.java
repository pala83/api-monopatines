package Integrador.utils.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import Integrador.dto.InscripcionDTO;

public class CsvInscripcionReader extends CsvReader<InscripcionDTO> {

    public CsvInscripcionReader(Path filePath) {
        super(filePath);
    }

    public List<InscripcionDTO> getData() throws IOException, ParseException {
        Iterable<CSVRecord> records = this.read();
        List<InscripcionDTO> inscriptos = new LinkedList<>();
        for (CSVRecord record : records) {
            Integer graduacion = Integer.parseInt(record.get("graduacion"));
            InscripcionDTO ec = new InscripcionDTO(
                Integer.parseInt(record.get("id")),
                Integer.parseInt(record.get("id_estudiante")),
                Integer.parseInt(record.get("id_carrera")),
                Integer.parseInt(record.get("inscripcion")),
                graduacion != 0 ? graduacion : null,
                Integer.parseInt(record.get("antiguedad"))
            );
            inscriptos.add(ec);
        }
        return inscriptos;
    }
}
