package ej2;

import ej2.dto.PersonaDTO;
import ej2.dto.PersonaSocioDTO;
import ej2.repository.DireccionRepositoryImpl;
import ej2.repository.PersonaRepositoryImpl;
import ej2.repository.SocioRepositoryImpl;
import ej2.repository.TurnoRepositoryImpl;

public class Main {

    private static final DireccionRepositoryImpl dr = new DireccionRepositoryImpl();
    private static final PersonaRepositoryImpl pr = new PersonaRepositoryImpl();
    private static final SocioRepositoryImpl sr = new SocioRepositoryImpl();
    private static final TurnoRepositoryImpl tr = new TurnoRepositoryImpl();
    private static final String CSV_DIR = "TP2-2/src/main/resources/";
    public static void main(String[] args) {
        dr.insertarDesdeCSV(CSV_DIR + "direcciones.csv");
        pr.insertarDesdeCSV(CSV_DIR + "personas.csv");
        sr.insertarDesdeCSV(CSV_DIR + "socios.csv");
        tr.insertarDesdeCSV(CSV_DIR + "turnos.csv");

        // a) recuperar todas las personas asignadas a un turno.
        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");
        System.out.println("Jugadores del Turno de ID 1");
        String header = String.format("| %-30s | %-10s | %-15s | %-15s |", "Nombre", "Edad", "Ciudad", "Calle");
        System.out.println(header);
        System.out.println("------------------------------------------------------------------------------------");
        for(PersonaDTO p : tr.selectJugadoresPorId(1)){ // Reemplazar X por el ID del turno deseado
            System.out.println(String.format("| %-30s | %-10d | %-15s | %-15s |",
                p.getNombre(), p.getEdad(), p.getCiudad(), p.getCalle()) );
        }

        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

        // b) recuperar todas las personas asignadas a un turno, y marcar cuales de ellas son socios.
        System.out.println("Jugadores del Turno de ID 3 (con socio=*)");
        String header2 = String.format("| %-30s | %-10s | %-15s | %-15s | %-10s |", "Nombre", "Edad", "Ciudad", "Calle", "Es socio");
        System.out.println(header2);
        System.out.println("--------------------------------------------------------------------------------------------------------");
        for(PersonaSocioDTO p : tr.selectJugadoresConSocioPorId(3)){
            System.out.println(String.format("| %-30s | %-10d | %-15s | %-15s | %-10s |",
                p.getNombre(), p.getEdad(), p.getCiudad(), p.getCalle(), p.isSocio() ? "*" : ""));
        };

        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

        // c) recuperar todas las personas que viven en una ciudad predeterminada.
        String ciudadObjetivo = "Bahia Blanca"; // cambiar aquí la ciudad deseada
        System.out.println("Personas que viven en " + ciudadObjetivo);
        String header3 = String.format("| %-30s | %-10s | %-15s | %-15s |", "Nombre", "Edad", "Ciudad", "Calle");
        System.out.println(header3);
        System.out.println("--------------------------------------------------------------");
        for (PersonaDTO p : pr.personasPorCiudadDTO(ciudadObjetivo)) {
            System.out.println(String.format("| %-30s | %-10d | %-15s | %-15s |",
                p.getNombre(), p.getEdad(), p.getCiudad(), p.getCalle()));
        }
    }
}
    // Sin usar DTO MALA PRACTICA !!!
    // List<Persona> personas_por_ciudad = pr.buscarPorCiudad("Bahia Blanca");
    // for (Persona p : personas_por_ciudad) {
    //     System.out.println(p.getNombre() + " vive en " + p.getDireccion().getCiudad() + ", "
    //         + p.getDireccion().getCalle());
    // }
    //     System.out.println("-----------------------------------");
    //     System.out.println("-----------------------------------");
    // }

    /*

    public static void main(String[] args) {

        // Usando DTO como debe ser !!!
    List<PersonaDTO> personas_por_ciudadDTO = pr.buscarPorCiudad2("Bahia Blanca");
        for (PersonaDTO p : personas_por_ciudadDTO) {
            System.out.println(p);
        }

        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

        // Una consulta para utilizar DireccionDTO
        String nombre = "Pedro Pons";
        List<DireccionDTO> direccion = dr.direccion_de_persona(nombre);
        System.out.println("La direccion de " + nombre + " es " + direccion);
    }

    

     */