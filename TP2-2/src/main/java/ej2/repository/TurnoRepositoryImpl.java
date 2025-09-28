package ej2.repository;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import ej2.dto.PersonaDTO;
import ej2.dto.PersonaSocioDTO;
import ej2.dto.TurnoDTO;
import ej2.factory.JPAUtil;
import ej2.model.Persona;
import ej2.model.Turno;
import jakarta.persistence.EntityManager;

public class TurnoRepositoryImpl implements TurnoRepository {

    @Override
    public void insertarDesdeCSV(String pathCSV) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(pathCSV))) {
            String[] linea;
            reader.readNext(); // encabezado
            em.getTransaction().begin();
            while ((linea = reader.readNext()) != null) {
                // Formato: id,fecha,jugadores  (fecha: yyyy-MM-dd HH:mm:ss) jugadores: id1|id2|...
                Turno turno = new Turno();
                Timestamp ts = Timestamp.valueOf(linea[1]);
                turno.setFecha(ts);

                List<Persona> jugadores = new ArrayList<>();
                if (linea.length > 2 && linea[2] != null && !linea[2].isBlank()) {
                    String[] ids = linea[2].split("\\|");
                    for (String idStr : ids) {
                        try {
                            Persona p = em.find(Persona.class, Integer.valueOf(idStr));
                            if (p != null) {
                                jugadores.add(p);
                            } else {
                                System.out.println("Persona no encontrada (jugador) id=" + idStr);
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("ID de jugador inválido: " + idStr);
                        }
                    }
                }
                turno.setJugadores(jugadores);
                em.persist(turno);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonaDTO> selectJugadoresPorId(Integer turnoId) {
        List<PersonaDTO> personas = new ArrayList<>();
        EntityManager em = JPAUtil.getEntityManager();
        try {
            personas = em.createQuery(
                "SELECT new ej2.dto.PersonaDTO(p.nombre, p.edad, p.direccion.ciudad, p.direccion.calle) " +
                    "FROM Turno t JOIN t.jugadores p " + 
                    "WHERE t.id = :id",
                 PersonaDTO.class)
                .setParameter("id", turnoId)
                .getResultList();
        } finally {
            em.close();
        }
        return personas;
    }

    @Override
    public List<PersonaSocioDTO> selectJugadoresConSocioPorId(Integer turnoId) {
        EntityManager em = JPAUtil.getEntityManager();
        List<PersonaSocioDTO> personas = new ArrayList<>();
        try {
            personas = em.createQuery(
                "SELECT new ej2.dto.PersonaSocioDTO(p.nombre, p.edad, p.direccion.ciudad, p.direccion.calle, (s.id IS NOT NULL)) " +
                "FROM Turno t JOIN t.jugadores p " +
                "LEFT JOIN Socio s ON s.persona = p " +
                "WHERE t.id = :id",
                PersonaSocioDTO.class)
            .setParameter("id", turnoId)
            .getResultList();
        } finally {
            em.close();
        }
        return personas;
    }

    @Override
    public List<TurnoDTO> selectTodo() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT new ej2.dto.TurnoDTO(t.fecha, t.jugadores) " + 
                    "FROM Turno t " + 
                    "ORDER BY t.fecha",
                TurnoDTO.class)
                .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public TurnoDTO selectPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT new ej2.dto.TurnoDTO(t.fecha, t.jugadores) " +
                    "FROM Turno t WHERE t.id = :id",
                TurnoDTO.class)
                .setParameter("id", id)
                .getSingleResult();
        } finally {
            em.close();
        }
    }
}
