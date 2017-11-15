package Dao;

import java.sql.*;
import java.lang.*;

/**
 * Provides a connection for the DAO's to work on the database
 */
public class Database {
    private Connection conn = null;
    private AuthTokenDao authDao;
    private EventDao eventDao;
    private PersonDao personDao;
    private UserDao userDao;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    /**
     * Opens a connection between the program and the database
     * so that SQL queries can be run
     */
    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:fms/db/FMS.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);

            eventDao = new EventDao(conn);
            authDao = new AuthTokenDao(conn);
            userDao = new UserDao(conn);
            personDao = new PersonDao(conn);
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnection failed", e);
        }
    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed", e);
        }
    }

    public void createTables() throws DatabaseException {
        Statement stmt = null;
        try {
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate("drop table if exists AuthToken");
                stmt.executeUpdate("drop table if exists Event");
                stmt.executeUpdate("drop table if exists Person");
                stmt.executeUpdate("drop table if exists User");

                stmt.executeUpdate("create table AuthToken (" +
                        "auth_token TEXT NOT NULL," +
                        "username TEXT NOT NULL," +
                        "PRIMARY KEY(auth_token))");

                stmt.executeUpdate("create table Event (" +
                        "event_id TEXT NOT NULL," +
                        "descendant TEXT NOT NULL," +
                        "person TEXT NOT NULL," +
                        "latitude TEXT NOT NULL," +
                        "longitude TEXT NOT NULL," +
                        "country TEXT NOT NULL," +
                        "city TEXT NOT NULL," +
                        "event_type TEXT NOT NULL," +
                        "year TEXT NOT NULL," +
                        "PRIMARY KEY(event_id)" +
                        ")");
                stmt.executeUpdate("CREATE TABLE Person (" +
                        "person_id TEXT NOT NULL," +
                        "descendant TEXT NOT NULL," +
                        "first_name TEXT NOT NULL," +
                        "last_name TEXT NOT NULL," +
                        "gender TEXT NOT NULL," +
                        "father TEXT," +
                        "mother TEXT," +
                        "spouse TEXT," +
                        "PRIMARY KEY(person_id)" +
                        ")");
                stmt.executeUpdate("CREATE TABLE User (" +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "email TEXT NOT NULL," +
                        "first_name TEXT NOT NULL," +
                        "last_name TEXT NOT NULL," +
                        "gender TEXT NOT NULL," +
                        "person_id TEXT NOT NULL," +
                        "PRIMARY KEY(username)" +
                        ")");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("createTables failed", e);
        }
    }

    public EventDao getEventDao() {
        return eventDao;
    }

    public AuthTokenDao getAuthDao() {
        return authDao;
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
