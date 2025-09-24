package ej8.repository;

import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;

import ej8.dto.PersonaDTO;
import ej8.factory.JPAUtil;
import ej8.modelo.Direccion;
import ej8.modelo.Persona;
import jakarta.persistence.EntityManager;

public class PersonaRepositoryImpl implements PersonaRepository {
    @Override
    public void insertarDesdeCSV(String pathCSV) {
        EntityManager em = JPAUtil.getEntityManager();
        try (CSVReader reader = new CSVReader(new FileReader(pathCSV))){
            String[] linea;
            reader.readNext();
            em.getTransaction().begin();

            while((linea = reader.readNext()) != null){
                Persona persona = new Persona();
                persona.setNombre(linea[1]);
                persona.setEdad(Integer.parseInt(linea[2]));
                persona.setEmail(linea[3]);

                Direccion direccion = em.find(Direccion.class, Integer.parseInt(linea[4]));
                persona.setDireccion(direccion);
                em.persist(persona);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Persona> buscarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Persona> personas = em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
        em.close();
        return personas;
    }

    @Override
    public List<Persona> buscarPorCiudad(String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Persona> personas = em.createQuery(
                        "SELECT p FROM Persona p JOIN p.direccion d WHERE d.ciudad = :ciudad", Persona.class)
                .setParameter("ciudad", ciudad)
                .getResultList();
        em.close();
        return personas;
    }

    @Override
    public List<PersonaDTO> buscarPorCiudad2(String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();

        List<PersonaDTO> personasDTO = em.createQuery(
                        "SELECT new ej8.dto.PersonaDTO(p.nombre, p.edad, d.ciudad, d.calle, d.numero) " +
                                "FROM Persona p JOIN p.direccion d " +
                                "WHERE d.ciudad = :ciudad",
                        PersonaDTO.class
                )
                .setParameter("ciudad", ciudad)
                .getResultList();

        em.close();
        return personasDTO;
    }

}
