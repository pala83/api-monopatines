package Integrador.repository;

import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;

import Integrador.dto.EstudianteDTO;
import Integrador.factory.JPAUtil;
import Integrador.model.Estudiante;
import jakarta.persistence.EntityManager;

public class EstudianteRepositoryImp implements EstudianteRepository {

    @Override
    public void cargarEstudiantes(String archivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(archivo))){
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();
            while((linea = reader.readNext()) != null){
                Estudiante estudiante = new Estudiante();
                estudiante.setDni(Integer.parseInt(linea[0]));
                estudiante.setNombre(linea[1]);
                estudiante.setApellido(linea[2]);
                estudiante.setEdad(Integer.parseInt(linea[3]));
                estudiante.setGenero(linea[4]);
                estudiante.setCiudad(linea[5]);
                estudiante.setNLibretaU(Integer.parseInt(linea[6]));
                em.persist(estudiante);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<EstudianteDTO> getEstudiantes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstudiantes'");
    }
    
}
