package Integrador.consultas;

import java.util.List;

import Integrador.model.EstudianteCarrera;
import Integrador.model.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class Consultas {
    private EntityManagerFactory emf;
    private EntityManager em;

    public Consultas(String baseDeDatos) {
        this.emf = Persistence.createEntityManagerFactory(baseDeDatos);
        this.em = emf.createEntityManager();
    }

    public void darDeAltaEstudiante(String nombre, String apellido, String genero, int edad, String ciudad, int nLibretaU){

    try {
        this.em.getTransaction().begin();
        Query query = em.createNativeQuery(
          "INSERT INTO estudiante (nombre, apellido, genero, edad, ciudad, nLibretaU) VALUES (?, ?, ?, ?,? ?)");
        query.setParameter(1, nombre);
        query.setParameter(2, apellido);
        query.setParameter(3, genero);
        query.setParameter(4, edad);
        query.setParameter(5, ciudad);
        query.setParameter(6, nLibretaU);
        query.executeUpdate();
        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        throw new RuntimeException("Error de inserción SQL nativa: " + e.getMessage(), e);
    }
}

    public void matricularEstudianteEnMateria(int estudianteId, int materiaId) {
        try {
            this.em.getTransaction().begin();
            Query query = em.createNativeQuery(
                    "INSERT INTO estudiante_materia (estudiante_id, materia_id) VALUES (?, ?)");
            query.setParameter(1, estudianteId);
            query.setParameter(2, materiaId);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error de inserción SQL nativa: " + e.getMessage(), e);
        }
    }

    public List<Estudiante> getEstudiantesOrderTo(String order) {
        List<String> camposValidos = List.of("nombre", "apellido", "genero", "edad", "ciudad", "nLibretaU");
        if (!camposValidos.contains(order)) {
            throw new IllegalArgumentException("Campo de orden inválido: " + order);
        }

        String jpql = "SELECT e FROM Estudiante e ORDER BY e." + order;
        return em.createQuery(jpql, Estudiante.class).getResultList();
    }

    public Estudiante getEstudiantePorLibreta(int nLibretaU) {
        Estudiante estudiante = null;
        String jpql = "SELECT e FROM Estudiante e WHERE e.nLibretaU = :nLibretaU";
        Query query = em.createQuery(jpql, Estudiante.class);
        query.setParameter("nLibretaU", nLibretaU);
        
        estudiante = (Estudiante) query.getSingleResult();
        return estudiante;
    }

    public List<Estudiante> getEstudianesPorGenero(String genero) {
        String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero";
        Query query = em.createQuery(jpql, Estudiante.class);
        
        query.setParameter("genero", genero);
        List <Estudiante> estudiantes = query.getResultList();
        return estudiantes;
    }

    public List<EstudianteCarrera> getCarrerasConInscriptosOrderTo(){
        String jpql = 
        "Select c.id, c.estudiantes.size"+
        "From EstudianteCarrera c " +
        "grup by c.id " +
        "where c.estudiantes.size > 0"+ 
        "Order by c.estudiantes.size";

        return em.createQuery(jpql, EstudianteCarrera.class).getResultList();
    }


    public List<EstudianteCarrera> getEstudiantesCarreraPorCiudad(String ciudad){
        String jpql = 
        "Select ec.estudiantes "+
        "From EstudianteCarrera ec "+
        "Join ec.estudiantes e "+
        "Where e.ciudad = :ciudad";

        Query query = em.createQuery(jpql, EstudianteCarrera.class);
        query.setParameter("ciudad", ciudad);
        return query.getResultList();
    }

    






    public void eliminar(Object entidad) {
        em.getTransaction().begin();
        em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
        em.getTransaction().commit();
    }

    public void cerrar() {
        if (em.isOpen()) em.close();
        if (emf.isOpen()) emf.close();
    }
}


