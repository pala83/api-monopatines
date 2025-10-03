package Integrador.utils.csv;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public abstract class CsvReader<T> {
    private Path filePath;

    public CsvReader(Path filePath) {
        this.filePath = filePath;
    }

    public Iterable<CSVRecord> read() throws IOException{
        Reader in = new FileReader(this.filePath.toString());
        return CSVFormat.DEFAULT.builder()
                .setHeader()
                .get()
                .parse(in);
    }

    public abstract List<T> getData() throws IOException, ParseException ;
}
