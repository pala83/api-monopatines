package org.db;

import java.sql.SQLException;

import org.db.dao.DAOPersona;
import org.db.entities.Persona;
import org.db.factory.AbstractFactory;
import org.db.utils.DerbyHelper;
import org.db.utils.MySQLHelper;

public class Main {
    private static DAOPersona derbyDAOP;
    private static DAOPersona mysqlDAOP;
    private static DerbyHelper derbyHelper;
    private static MySQLHelper mysqlHelper;
    private static AbstractFactory derbyFactory = AbstractFactory.getDAOFactory(AbstractFactory.DERBY_JDBC);
    private static AbstractFactory mysqlFactory = AbstractFactory.getDAOFactory(AbstractFactory.MYSQL_JDBC);
    public static void main(String[] args) throws Exception {
        derbyHelper = new DerbyHelper(derbyFactory.getConnection());
        mysqlHelper = new MySQLHelper(mysqlFactory.getConnection());
        instanciarDaos();
        try {
            derbyHelper.cargarTablas();
            derbyHelper.populated();
        } catch (SQLException e) {
            System.out.println("La tabla persona ya existe, no se procede con el populated en la tabla");
        }
        System.out.println("Todas las Personas en Derby:");
        String header1 = String.format("| %-5s | %-10s | %-15s | %-15s | %-35s | %-15s |", "id", "dni", "nombre", "apellido", "email", "fechaNacimiento");
        System.out.println(header1);
        System.out.println("-".repeat(header1.length()));
        for(Persona p : derbyFactory.getDaoPersona().selectAll())
            System.out.println(p);

        try {
            mysqlHelper.cargarTablas();
            mysqlHelper.populated();
        } catch (SQLException e) {
            System.out.println("La tabla persona ya existe, no se procede con el populated en la tabla");
        }

        System.out.println("Todas las Personas en MySQL:");
        String header2 = String.format("| %-5s | %-10s | %-15s | %-15s | %-35s | %-15s |", "id", "dni", "nombre", "apellido", "email", "fechaNacimiento");
        System.out.println(header2);
        System.out.println("-".repeat(header2.length()));
        for(Persona p : mysqlFactory.getDaoPersona().selectAll())
            System.out.println(p);
    }

    public static void instanciarDaos() throws Exception {
        derbyDAOP = derbyFactory.getDaoPersona();
        mysqlDAOP = mysqlFactory.getDaoPersona();
    }

}