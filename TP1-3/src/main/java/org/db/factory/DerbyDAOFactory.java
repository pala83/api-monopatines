package org.db.factory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.db.dao.DAOPersona;

public class DerbyDAOFactory extends AbstractFactory{
    private static Connection conn;
    private static DerbyDAOFactory instance = null;
    private static final String DATABASE = System.getenv().getOrDefault("DATABASE", "facturadoraDB");
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String URI =  "jdbc:derby:"+DATABASE+";create=true";

    private DerbyDAOFactory(){}

    public static synchronized DerbyDAOFactory getInstance() {
        if (instance == null) {
            instance = new DerbyDAOFactory();
            conn = createConnection();
        }
        return instance;
    }

    public static Connection createConnection(){
        try{
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        }catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException |
               NoSuchMethodException | SecurityException | ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
        try{
            conn = DriverManager.getConnection(URI);
            conn.setAutoCommit(false);
        } catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return conn;
    }

    @Override
    public DAOPersona getDaoPersona() throws SQLException {
        return new DAOPersona(conn);
    }

}
