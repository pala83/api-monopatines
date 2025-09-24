package org.db.factory;

import java.sql.Connection;
import java.sql.SQLException;

import org.db.dao.DAOPersona;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public static AbstractFactory getDAOFactory(int factory) {
        switch (factory) {
            case MYSQL_JDBC : return MySQLDAOFactory.getInstance();
            case DERBY_JDBC: return DerbyDAOFactory.getInstance();
            default: return null;
        }
    }

    public abstract Connection getConnection() throws SQLException;
    public abstract DAOPersona getDaoPersona() throws SQLException;
}
