package org.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.db.entities.Persona;
import org.db.utils.DerbyHelper;

public class DAOPersona implements DAO<Persona> {
    private Connection conn;
    private DerbyHelper derbyHelper;
    public DAOPersona(Connection conn) {
        this.conn = conn;
        this.derbyHelper = new DerbyHelper(conn);
    }

    @Override
    public int insert(Persona c) throws Exception {
        String insert = "INSERT INTO Persona (id, dni, nombre, apellido, email, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(insert);
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getDni());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellido());
            ps.setString(5, c.getEmail());
            ps.setDate(6, c.getFechaNacimiento());
            if(ps.executeUpdate() == 0) throw new Exception("Fallo el insert del cliente: " + c.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            derbyHelper.closePsAndCommit(ps);
        }
        return 0;
    }

    public int insertBatch(List<Persona> personas) throws Exception {
        String insert = "INSERT INTO Persona (id, dni, nombre, apellido, email, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        int insertedCount = 0;
        try {
            ps = this.conn.prepareStatement(insert);
            for (Persona p : personas) {
                ps.setInt(1, p.getId());
                ps.setInt(2, p.getDni());
                ps.setString(3, p.getNombre());
                ps.setString(4, p.getApellido());
                ps.setString(5, p.getEmail());
                ps.setDate(6, p.getFechaNacimiento());
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            for (int res : results) {
                if (res == PreparedStatement.EXECUTE_FAILED) {
                    throw new Exception("Fallo el insert masivo de personas");
                } else {
                    insertedCount += res;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            derbyHelper.closePsAndCommit(ps);
        }
        return insertedCount;
    }

    @Override
    public boolean delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Persona find(Integer pk) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public boolean update(Persona dao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Persona> selectAll() {
        String select = "SELECT * FROM Persona";
        List<Persona> personas = new ArrayList<>();
        try (PreparedStatement ps = this.conn.prepareStatement(select);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona p = new Persona(
                    rs.getInt("id"),
                    rs.getInt("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email"),
                    rs.getDate("fechaNacimiento")
                );
                personas.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }
}