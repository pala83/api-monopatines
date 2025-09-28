package ej1.repository;

import java.io.FileReader;

import com.opencsv.CSVReader;

import ej1.factory.JPAUtil;
import ej1.model.Persona;
import ej1.model.Socio;
import jakarta.persistence.EntityManager;

public class SocioRepositoryImpl implements SocioRepository {

    @Override
    public void insertarDesdeCSV(String pathCSV) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(pathCSV))) {
            String[] linea;
            reader.readNext(); // encabezado
            em.getTransaction().begin();
            while ((linea = reader.readNext()) != null) {
                // Formato: id,persona_id,type
                Integer personaId = Integer.valueOf(linea[1]);
                Persona persona = em.find(Persona.class, personaId);
                if (persona == null) {
                    System.out.println("Persona no encontrada para socio id=" + linea[0] + ", personaId=" + personaId);
                    continue;
                }
                Socio socio = new Socio();
                // @MapsId hará que el id del socio sea el de la persona.
                socio.setPersona(persona);
                socio.setType(linea[2]);
                em.persist(socio);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
