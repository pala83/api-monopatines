package ej8;

import java.util.List;

import ej8.dto.DireccionDTO;
import ej8.dto.PersonaDTO;
import ej8.modelo.Persona;
import ej8.repository.DireccionRepositoryImpl;
import ej8.repository.PersonaRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        DireccionRepositoryImpl dr = new DireccionRepositoryImpl();
        PersonaRepositoryImpl pr = new PersonaRepositoryImpl();

        dr.insertarDesdeCSV("TP1-8/src/main/resources/direccion.csv");
        pr.insertarDesdeCSV("TP1-8/src/main/resources/persona.csv");

        for (Persona persona : pr.buscarTodos()) {
            System.out.println(persona.getNombre() + " vive en " + persona.getDireccion().getCiudad() + ", "
                    + persona.getDireccion().getCalle() + " " + persona.getDireccion().getNumero());
        }

        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

        // Sin usar DTO MALA PRACTICA !!!
        List<Persona> personas_por_ciudad = pr.buscarPorCiudad("Bahia Blanca");
        for (Persona p : personas_por_ciudad) {
            System.out.println(p.getNombre() + " vive en " + p.getDireccion().getCiudad() + ", "
                    + p.getDireccion().getCalle() + " " + p.getDireccion().getNumero());
        }
        System.out.println("-----------------------------------");
        System.out.println("-----------------------------------");

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
}