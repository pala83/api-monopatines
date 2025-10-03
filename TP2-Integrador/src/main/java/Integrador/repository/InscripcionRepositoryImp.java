package Integrador.repository;

import java.util.List;

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
    public List<InscripcionDTO> getInscripciones() {
        throw new UnsupportedOperationException("Unimplemented method 'getInscripciones'");
    }



}
