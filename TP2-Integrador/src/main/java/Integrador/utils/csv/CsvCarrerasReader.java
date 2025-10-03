package Integrador.utils.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import Integrador.dto.CarreraDTO;

public class CsvCarrerasReader extends CsvReader<CarreraDTO> {

    public CsvCarrerasReader(Path filePath) {
        super(filePath);
    }

    public List<CarreraDTO> getData() throws IOException, ParseException {
        Iterable<CSVRecord> records = this.read();
        List<CarreraDTO> carreras = new LinkedList<>();
        for (CSVRecord record : records) {
            CarreraDTO c = new CarreraDTO(
                Integer.parseInt(record.get("id_carrera")),
                record.get("carrera"),
                Integer.parseInt(record.get("duracion"))
            );
            carreras.add(c);
        }
        return carreras;
    }
}
