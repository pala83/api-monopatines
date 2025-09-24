package ej6;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ej6.dao.Direccion;
import ej6.dao.Persona;
import ej6.dao.Socio;
import ej6.dao.Turno;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();
        runMenu(em);
        emf.close();
    }

    private static void runMenu(EntityManager em) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("===== MENU =====");
            System.out.println("1. Insertar datos de ejemplo");
            System.out.println("2. Listar todas las personas");
            System.out.println("3. Buscar personas por ciudad");
            System.out.println("4. Buscar personas por apellido");
            System.out.println("5. Buscar turnos por jugador");
            System.out.println("6. Contar cantidad de personas con un nombre");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    insertSampleData(em);
                    break;
                case "2":
                    printPersonas(em);
                    break;
                case "3":
                    System.out.print("Ciudad: ");
                    String ciudad = scanner.nextLine();
                    buscarPorCiudad(em, ciudad);
                    break;
                case "4":
                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine();
                    buscarPorApellido(em, apellido);
                    break;
                case "5":
                    System.out.print("ID del jugador: ");
                    int personaId = Integer.parseInt(scanner.nextLine());
                    buscarTurnosPorJugador(em, personaId);
                    break;
                case "6":
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    contarCantidadPersonaConNombre(em, nombre);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private static void insertSampleData(EntityManager em) {
        em.getTransaction().begin();

        Direccion d = new Direccion("Tandil", "Calle Falsa 123");
        em.persist(d);
        Persona p = new Persona(1, 12345678, "Juan", "Perez", 1990, "juan.perez@example.com", d);
        Persona p2 = new Persona(2, 87654321, "Maria", "Gomez", 1995, "maria.gomez@example.com", d);
        em.persist(p);
        em.persist(p2);
        Socio sp = new Socio(p, "Basico");
		Socio sp2 = new Socio(p2, "Premium");
		em.persist(sp);
		em.persist(sp2);
		Turno t = new Turno(new Timestamp(System.currentTimeMillis()));
		t.getJugadores().add(p);
        System.out.println("Datos insertados.");

        em.getTransaction().commit();
        em.close();
    }

    private static void printPersonas(EntityManager em) {
        em.getTransaction().begin();

        System.out.println("Personas:");
        // em.createQuery("FROM Persona", Persona.class)
        //     .getResultList()
        //     .forEach(System.out::println);

        @SuppressWarnings("unchecked")
        List<Persona> personas = em.createQuery("SELECT p FROM Persona p").getResultList();
        personas.forEach(p -> System.out.println(p));

        em.getTransaction().commit();
        em.close();
    }

    private static void buscarPorCiudad(EntityManager em, String ciudad) {
        em.getTransaction().begin();

        List<Persona> personas = em.createQuery(
                "SELECT p FROM Persona p WHERE p.direccion.ciudad = :ciudad", Persona.class)
            .setParameter("ciudad", ciudad)
            .getResultList();
        if (personas.isEmpty()) {
            System.out.println("Sin resultados.");
        } else {
            personas.forEach(p -> System.out.println(p));
        }

        em.getTransaction().commit();
        em.close();
    }

    private static void buscarPorApellido(EntityManager em, String apellido) {
        em.getTransaction().begin();

        List<Persona> personas = em.createQuery(
                "SELECT p FROM Persona p WHERE p.apellido = :apellido", Persona.class)
            .setParameter("apellido", apellido)
            .getResultList();
        if (personas.isEmpty()) {
            System.out.println("Sin resultados.");
        } else {
            personas.forEach(p -> System.out.println(p));
        }

        em.getTransaction().commit();
        em.close();
    }

    private static void buscarTurnosPorJugador(EntityManager em, int personaId) {
        em.getTransaction().begin();

        List<Turno> turnos = em.createQuery(
                "SELECT t FROM Turno t JOIN t.jugadores p WHERE p.id = :personaId", Turno.class)
            .setParameter("personaId", personaId)
            .getResultList();
        if (turnos.isEmpty()) {
            System.out.println("Sin resultados.");
        } else {
            turnos.forEach(t -> System.out.println(t));
        }

        em.getTransaction().commit();
        em.close();
    }

    private static void contarCantidadPersonaConNombre(EntityManager em, String nombre) {
        em.getTransaction().begin();

        List<Object[]> results = em.createQuery(
                "SELECT p.nombre, COUNT(p) FROM Persona p WHERE p.nombre = :nombre", Object[].class)
            .setParameter("nombre", nombre)
            .getResultList();

        results.forEach(p -> System.out.println(Arrays.toString(p)));
        em.getTransaction().commit();
        em.close();
    }
}