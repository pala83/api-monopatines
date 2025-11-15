package microservicio.mantenimiento.utils.dataManager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.ParseException;

public abstract class Populated<T> {
    private Path filePath;
    public Populated(Path filePath) {
        this.filePath = filePath;
    }

    public Iterable<CSVRecord> read() throws IOException {
        ClassPathResource resource = new ClassPathResource(this.filePath.toString());
        InputStream is = resource.getInputStream();
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        CSVParser parser = null;
        try {
            parser = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .get()
                    .parse(reader);
            var records = parser.getRecords();
            return records;
        }
        catch (IOException e){
            System.err.println("[Populated.read] ERROR leyendo/parsing CSV '" + this.filePath.toString() + "': " + e.getMessage());
            throw e;
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    public abstract void poblar() throws IOException, ParseException;

}
