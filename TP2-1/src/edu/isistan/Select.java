package edu.isistan;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.isistan.dao.Persona;
import edu.isistan.dao.Socio;
import edu.isistan.dao.Turno;

public class Select {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Example");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		@SuppressWarnings("unchecked")
		List<Object[]> res = em.createQuery("SELECT p.nombre, p.edad FROM Persona p").getResultList();
		
		//res.forEach(d -> System.out.println(Arrays.toString(d)));
		
		System.out.println("*****************TODOS*******************************");
		
		for (Object[] d: res) {
			System.out.println(Arrays.toString(d));
		}
		System.out.println("*******************POR TURNO*****************************");
		// Recuperar todas las personas asignadas a un turno
		@SuppressWarnings("unchecked")
		List<Turno> turnos = em.createQuery("SELECT DISTINCT t FROM Turno t JOIN FETCH t.jugadores").getResultList();
		
		// Imprimir los turnos y las personas asociadas
		for (Turno turno : turnos) {
			System.out.println("Turno: " + turno.getId() + ", Fecha: " + turno.getFecha());
			for (Persona jugador : turno.getJugadores()) {
				System.out.println("\tJugador: " + jugador.getNombre() + ", Edad: " + jugador.getEdad());
			}
		}

		int turnoId = 3;
		System.out.println("*******************TURNO Y SOCIOS*****************************");
		List<Turno> turnosSocios = em.createQuery("SELECT DISTINCT t FROM Turno t JOIN t.jugadores j LEFT JOIN j.socio WHERE t.id = :turnoId", Turno.class).setParameter("turnoId", turnoId).getResultList();
		for (Turno turno : turnosSocios) {
			System.out.println("Turno: " + turno.getId() + ", Fecha: " + turno.getFecha());
			for (Persona jugador : turno.getJugadores()) {
				if (jugador.getSocio() != null) {
					System.out.println("\tJugador: " + jugador.getNombre() + ", Edad: " + jugador.getEdad() + ", Es Socio.");
				} else {
					System.out.println("\tJugador: " + jugador.getNombre() + ", Edad: " + jugador.getEdad() + ", No es socio");
				}
			}
		}

		String ciudad = "Tandil";
		System.out.println("*******************PERSONAS POR CIUDAD*****************************");
		List<Persona> personasPorCiudad = em.createQuery("SELECT DISTINCT p FROM Persona p JOIN p.domicilio d WHERE d.ciudad = :ciudad", Persona.class)
				.setParameter("ciudad", ciudad)
				.getResultList();
		
		if (personasPorCiudad.isEmpty()) {
			System.out.println("No hay personas en la ciudad de " + ciudad);
		}else {
			for (Persona persona : personasPorCiudad) {
				System.out.println(persona.getNombre() + " " + persona.getEdad() + " años, Ciudad: " + ciudad);
			}
		}

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
