package Integrador;

import java.nio.file.Path;

import Integrador.dto.CarreraDTO;
import Integrador.dto.EstudianteDTO;
import Integrador.dto.InscripcionDTO;
import Integrador.utils.csv.CsvReader;
import Integrador.utils.csv.CsvCarrerasReader;
import Integrador.utils.csv.CsvEstudiantesReader;
import Integrador.utils.csv.CsvInscripcionReader;
import Integrador.utils.populated.Populated;
import Integrador.utils.populated.PopulatedCarreras;
import Integrador.utils.populated.PopulatedEstudiantes;
import Integrador.utils.populated.PopulatedInscripciones;

public class Main {
    private static final String CSV_DIR = "TP2-Integrador/src/main/resources/";
    public static void main(String[] args) throws Exception {
        CsvReader<EstudianteDTO> cer = new CsvEstudiantesReader(Path.of(CSV_DIR + "estudiantes.csv"));
        CsvReader<CarreraDTO> ccr = new CsvCarrerasReader(Path.of(CSV_DIR + "carreras.csv"));
        CsvReader<InscripcionDTO> cir = new CsvInscripcionReader(Path.of(CSV_DIR + "estudianteCarrera.csv"));

        Populated<EstudianteDTO> estudiantesData = new PopulatedEstudiantes();
        estudiantesData.poblar(cer.getData());

        Populated<CarreraDTO> carrerasData = new PopulatedCarreras();
        carrerasData.poblar(ccr.getData());

        Populated<InscripcionDTO> inscripcionesData = new PopulatedInscripciones();
        inscripcionesData.poblar(cir.getData());
    }
}
