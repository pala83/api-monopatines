package ej1;

import java.util.List;

import ej1.model.Persona;
import ej1.repository.DireccionRepositoryImpl;
import ej1.repository.PersonaRepositoryImpl;
import ej1.repository.SocioRepositoryImpl;
import ej1.repository.TurnoRepositoryImpl;

public class Main {

    private static final DireccionRepositoryImpl dr = new DireccionRepositoryImpl();
    private static final PersonaRepositoryImpl pr = new PersonaRepositoryImpl();
    private static final SocioRepositoryImpl sr = new SocioRepositoryImpl();
    private static final TurnoRepositoryImpl tr = new TurnoRepositoryImpl();
    private static final String CSV_DIR = "TP2-1-pro/src/main/resources/";
    public static void main(String[] args) {
        dr.insertarDesdeCSV(CSV_DIR + "direcciones.csv");
        pr.insertarDesdeCSV(CSV_DIR + "personas.csv");
        sr.insertarDesdeCSV(CSV_DIR + "socios.csv");
        tr.insertarDesdeCSV(CSV_DIR + "turnos.csv");

        System.out.println("-----------------------------------");  
        System.out.println("-----------------------------------");
        for (Persona persona : pr.buscarTodos()) {
            System.out.println(persona.getNombre() + " vive en " + persona.getDireccion().getCiudad() + ", "
                    + persona.getDireccion().getCalle() + " " + persona.getDireccion().getCalle());
        }

        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

        // Sin usar DTO MALA PRACTICA !!!
        List<Persona> personas_por_ciudad = pr.buscarPorCiudad("Bahia Blanca");
        for (Persona p : personas_por_ciudad) {
            System.out.println(p.getNombre() + " vive en " + p.getDireccion().getCiudad() + ", "
                    + p.getDireccion().getCalle() + " " + p.getDireccion().getCalle());
        }
        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");
    }

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
}