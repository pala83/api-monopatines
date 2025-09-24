package ej5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ej5.dao.Direccion;
import ej5.dao.Persona;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Direccion d = new Direccion("Tandil", "Calle Falsa 123");
        em.persist(d);
        Persona p = new Persona(1, 12345678, "Juan", "Perez", 1990, "juan.perez@example.com", d);
        Persona p2 = new Persona(2, 87654321, "Maria", "Gomez", 1995, "maria.gomez@example.com", d);
        em.persist(p);
        em.persist(p2);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}