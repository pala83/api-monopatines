package org.db.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.db.dao.DAOPersona;
import org.db.entities.Persona;

public class MySQLHelper {
    private Connection conn;
    public MySQLHelper(Connection conn){
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public void closePsAndCommit(PreparedStatement ps) {
        if (this.conn != null){
            try {
                if (ps != null) {
                    ps.close();
                }
                this.conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void cargarTablas() throws SQLException {
        cargarTablaPersona();
    }

    public void populated() throws Exception {
        populatedPersonas();
    }

    public void populatedPersonas() throws Exception {
        try {
            CSVHelper csv = new CSVHelper("personas.csv");
            List<Persona> personas = new ArrayList<>();
            for (CSVRecord record : csv.getData()) {
                try {
                    int id = Integer.parseInt(record.get("id"));
                    int dni = Integer.parseInt(record.get("dni"));
                    String nombre = record.get("nombre");
                    String apellido = record.get("apellido");
                    String email = record.get("email");
                    Date fechaNacimiento = Date.valueOf(record.get("fechaNacimiento"));
                    personas.add(new Persona(id, dni, nombre, apellido, email, fechaNacimiento));
                } catch (NumberFormatException nfe) {
                    System.out.println("Registro con formatos inválidos, se omite: " + record.toString());
                }
            }

            DAOPersona dao = new DAOPersona(this.getConn());
            int inserted = dao.insertBatch(personas);
            System.out.println("Personas insertadas (batch): " + inserted);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void cargarTablaPersona() throws SQLException {
        String tablaPersona = 
        "CREATE TABLE IF NOT EXISTS Persona ("+
            "id INT NOT NULL," +
            "dni VARCHAR(20) NOT NULL," +
            "nombre VARCHAR(500) NOT NULL," +
            "apellido VARCHAR(150) NOT NULL," +
            "email VARCHAR(150) NOT NULL," +
            "fechaNacimiento DATE NOT NULL," +
            "CONSTRAINT pk_idPersona PRIMARY KEY (id)"+
        ")";
        try (Statement stm = this.getConn().createStatement()) {
            stm.executeUpdate(tablaPersona);
            System.out.println("Tabla Persona creada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
