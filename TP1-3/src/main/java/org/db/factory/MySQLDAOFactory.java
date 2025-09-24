package org.db.factory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.db.dao.DAOPersona;

public class MySQLDAOFactory extends AbstractFactory {
    private static Connection conn;
    private static MySQLDAOFactory instance = null;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PORT = System.getenv().getOrDefault("MYSQL_HOST_PORT", "3306");
    private static final String DATABASE = System.getenv().getOrDefault("MYSQL_DATABASE", "facturadoraDB");
    private static final String USER = System.getenv().getOrDefault("MYSQL_USER", "admin");
    private static final String PASS = System.getenv().getOrDefault("MYSQL_PASSWORD", "MiPassUsuario123!");
    private static final String URI =  "jdbc:mysql://localhost:"+PORT+"/"+DATABASE;

    private MySQLDAOFactory(){}

    public static synchronized MySQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySQLDAOFactory();
            conn = createConnection();
        }
        return instance;
    }

    public static Connection createConnection(){
        if(conn!=null){
            return conn;
        }
        try{
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            conn = DriverManager.getConnection(URI, USER, PASS);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
