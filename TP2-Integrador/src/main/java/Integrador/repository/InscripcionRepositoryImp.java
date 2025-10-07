package Integrador.repository;

import java.util.List;

import Integrador.dto.CarrerasConInscriptosDTO;
import Integrador.dto.EstudianteEnCarreraXCiudadDTO;
import Integrador.dto.InscripcionDTO;
import Integrador.factory.JPAUtil;
import Integrador.model.Inscripcion;
import jakarta.persistence.EntityManager;

public class InscripcionRepositoryImp implements InscripcionRepository {

    @Override
    public void insert(Inscripcion inscripcion) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(inscripcion);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public InscripcionDTO getById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        InscripcionDTO dto = null;
        try {
            Inscripcion inscripcion = em.find(Inscripcion.class, id);
            dto = new InscripcionDTO(
                inscripcion.getId(),
                inscripcion.getEstudiante().getDni(),
                inscripcion.getCarrera().getId(),
                inscripcion.getInscripcion(),
                inscripcion.getGraduacion(),
                inscripcion.getAntiguedad()
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return dto;
    }

    @Override
    public List<CarrerasConInscriptosDTO> getCarrerasConInscriptos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<CarrerasConInscriptosDTO> resultados = null;
        try {
            String queryStr = "SELECT new Integrador.dto.CarrerasConInscriptosDTO(c.nombre, COUNT(i.id)) " +
                              "FROM Inscripcion i " +
                              "JOIN i.carrera c " +
                              "GROUP BY c.nombre " +
                              "ORDER BY COUNT(i.id) DESC";
            resultados = em.createQuery(queryStr, CarrerasConInscriptosDTO.class)
                           .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return resultados;
    }

    @Override
    public List<EstudianteEnCarreraXCiudadDTO> getEstudiantesEnCarreraXCiudad(String carrera, String ciudad){
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteEnCarreraXCiudadDTO> resultados = null;
        try {
            String queryStr = "SELECT new Integrador.dto.EstudianteEnCarreraXCiudadDTO(e.dni, e.nombre, e.apellido, c.nombre, e.ciudad) " +
                              "FROM Inscripcion i " +
                              "JOIN i.estudiante e " +
                              "JOIN i.carrera c " +
                              "WHERE c.nombre = :carrera AND e.ciudad = :ciudad";
            resultados = em.createQuery(queryStr, EstudianteEnCarreraXCiudadDTO.class)
                           .setParameter("carrera", carrera)
                           .setParameter("ciudad", ciudad)
                           .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return resultados;
    }

    @Override
    public List<InscripcionDTO> getInscripciones() {
        throw new UnsupportedOperationException("Unimplemented method 'getInscripciones'");
    }



}
