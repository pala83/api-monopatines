package Integrador;

import Integrador.repository.EstudianteRepository;
import Integrador.repository.EstudianteRepositoryImp;

public class Main {
    private static final EstudianteRepository er = new EstudianteRepositoryImp();
    private static final String CSV_DIR = "TP2-Integrador/src/main/resources/";
    public static void main(String[] args) {
        er.cargarEstudiantes(CSV_DIR + "estudiantes.csv");
    }
}
