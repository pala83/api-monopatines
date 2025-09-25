package edu.isistan;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.isistan.dao.Direccion;
import edu.isistan.dao.Persona;
import edu.isistan.dao.Socio;
import edu.isistan.dao.Turno;

public class Insert {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Direccion d = new Direccion("Tandil", "Sarmiento 772");
		Direccion d2 = new Direccion("Azul", "Belgrano 123");
		em.persist(d);
		em.persist(d2);
		Persona j = new Persona(1, "Juan", 25, d);
		Persona a = new Persona(2, "Ana", 22, d);
		Persona p3 = new Persona(3, "Pedro", 30, d2);
		em.persist(j);
		em.persist(a);
		em.persist(p3);
		Socio sj = new Socio(j, "Basico");
		Socio sa = new Socio(a, "Premium");
		em.persist(sj);
		em.persist(sa);
		Turno t1 = new Turno(new Timestamp(System.currentTimeMillis()));
		t1.getJugadores().add(j);
		t1.getJugadores().add(p3);
		em.persist(t1);
		
		Turno t2 = new Turno(new Timestamp(2025 - 1900, 11, 25, 10, 30, 0, 0));
		t2.getJugadores().add(a);
		em.persist(t2);
		
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
