package ej1.repository;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import ej1.dto.DireccionDTO;
import ej1.factory.JPAUtil;
import ej1.model.Direccion;
import jakarta.persistence.EntityManager;

public class DireccionRepositoryImpl implements DireccionRepository {
    @Override
    public void insertarDesdeCSV(String pathCSV) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(pathCSV))){
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();
            while((linea = reader.readNext()) != null){
                Direccion direccion = new Direccion();
                direccion.setCiudad(linea[1]);
                direccion.setCalle(linea[2]);
                direccion.setNumero(Integer.parseInt(linea[3]));
                direccion.setCodigoPostal(Integer.parseInt(linea[4]));
                em.persist(direccion);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<DireccionDTO> direccion_de_persona(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        List<DireccionDTO> direcciones = new ArrayList<>();
        try {
            direcciones = em.createQuery(
                "SELECT new ej8.dto.DireccionDTO(d.ciudad, d.calle, d.numero, d.codigoPostal) " +
                    "FROM Direccion d JOIN d.personas p " +
                    "WHERE p.nombre = :nombre",
                 DireccionDTO.class)
                .setParameter("nombre", nombre)
                .getResultList();
            if(direcciones.isEmpty()) {
                System.out.println("No se encontraron direcciones para la persona con nombre: " + nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return direcciones;
    }

}
