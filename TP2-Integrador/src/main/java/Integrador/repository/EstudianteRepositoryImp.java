package Integrador.repository;

import java.util.List;

import Integrador.dto.EstudianteDTO;
import Integrador.factory.JPAUtil;
import Integrador.model.Estudiante;
import jakarta.persistence.EntityManager;

public class EstudianteRepositoryImp implements EstudianteRepository {

    // Ejercicio 2.a)
    @Override
    public void insert(Estudiante estudiante) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Estudiante getById(Integer dni) {
        EntityManager em = JPAUtil.getEntityManager();
        Estudiante estudiante = null;
        try {
            estudiante = em.find(Estudiante.class, dni);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiante;
    }

    @Override
    public EstudianteDTO getByLu(Integer lu) {
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estudianteDTO = null;
        try {
            String queryStr = "SELECT new Integrador.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.lu) " +
                              "FROM Estudiante e " +
                              "WHERE e.lu = :lu";
            estudianteDTO = em.createQuery(queryStr, EstudianteDTO.class)
                              .setParameter("lu", lu)
                              .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudianteDTO;
    }

    @Override
    public List<EstudianteDTO> getEstudiantes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstudiantes'");
    }

    @Override
    public List<EstudianteDTO> getEstudiantesConOrden(String atributo, String orden) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = null;
        try {
            String queryStr = "SELECT new Integrador.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.lu) " +
                              "FROM Estudiante e " +
                              "ORDER BY e." + atributo + " " + orden;
            estudiantes = em.createQuery(queryStr, EstudianteDTO.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiantes;
    }

    @Override
    public List<EstudianteDTO> getEstudiantesPorGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantes = null;
        try {
            String queryStr = "SELECT new Integrador.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.lu) " +
                              "FROM Estudiante e " +
                              "WHERE e.genero = :genero";
            estudiantes = em.createQuery(queryStr, EstudianteDTO.class)
                            .setParameter("genero", genero)
                            .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return estudiantes;
    }
}
