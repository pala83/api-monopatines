package ej1.repository;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import ej1.factory.JPAUtil;
import ej1.model.Persona;
import ej1.model.Turno;
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
}
