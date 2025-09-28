package ej1.utils;

import java.nio.file.Path;

public class CSVHelper {
    private Path path;

    public CSVHelper(String archivo) {
        this.path = Path.of("TP2-1-pro", "src", "main", "resources", archivo);
    }

    public Path getPath() {
        return path;
    }
    public void setPath(Path path) {
        this.path = path;
    }
    

}
