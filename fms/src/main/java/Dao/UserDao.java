package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;

/**
 * Performs SQL queries on the User table
 */
public class UserDao {
    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Inserts a new row in the User SQL table
     * @param myUser User object to be added to the SQL table
     * @return the userId
     */
    public void createUser(User myUser) {
        try {
            PreparedStatement stmt = null;
            try {
                String sql = "insert into User values (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, myUser.getUsername());
                stmt.setString(2, myUser.getPassword());
                stmt.setString(3, myUser.getEmail());
                stmt.setString(4, myUser.getFirstName());
                stmt.setString(5, myUser.getLastName());
                stmt.setString(6, myUser.getGender());
                stmt.setString(7, myUser.getPersonId());

                if (stmt.executeUpdate() != 1) {
                    System.out.println("createPerson failed: could not insert event");
                }
            }
            finally {
                if (stmt != null) stmt.close();
            }
        }
        catch (SQLException e) {
           e.printStackTrace();
        }
    }

    /**
     * Selects a row from the User SQL table
     * @param username the primary key of the row to be read
     * @return the user object containing the row information
     */
    public User getUser(String username) {
        User myUser = null;
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from User where username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    myUser = new User();
                    myUser.setUsername(rs.getString(1));
                    myUser.setPassword(rs.getString(2));
                    myUser.setEmail(rs.getString(3));
                    myUser.setFirstName(rs.getString(4));
                    myUser.setLastName(rs.getString(5));
                    myUser.setGender(rs.getString(6));
                    myUser.setPersonId(rs.getString(7));
                }
            }
            finally {
                if (stmt != null) stmt.close();
                if(rs != null) rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return myUser;
    }

    /**
     * Deletes a row from the User SQL table
     * @param username primary key of the row to be deleted
     */
    public void deleteUser(String username) {
        try {
            String sql = "delete from User where username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);

            if (stmt.executeUpdate() != 1) {
                System.out.println("deleteUser failed: couldn't delete user");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
