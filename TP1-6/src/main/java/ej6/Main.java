package ej6;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ej6.dao.Direccion;
import ej6.dao.Persona;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        
        //insertSampleData(em);
        //printPersonas(em);
        consultaBasica(em);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void printPersonas(EntityManager em) {
        System.out.println("Personas:");
        em.createQuery("FROM Persona", Persona.class)
            .getResultList()
            .forEach(System.out::println);
    }

    private static void insertSampleData(EntityManager em) {
        Direccion d = new Direccion("Tandil", "Calle Falsa 123");
        em.persist(d);
        Persona p = new Persona(1, 12345678, "Juan", "Perez", 1990, "juan.perez@example.com", d);
        Persona p2 = new Persona(2, 87654321, "Maria", "Gomez", 1995, "maria.gomez@example.com", d);
        em.persist(p);
        em.persist(p2);
    }

    private static void consultaBasica(EntityManager em) {
        @SuppressWarnings("unchecked")
        List<Persona> personas = em.createQuery("SELECT p FROM Persona p").getResultList();
        personas.forEach(p -> System.out.println(p));
    }
}