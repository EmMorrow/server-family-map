package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.AuthToken;

/**
 * Performs SQL queries on the AuthToken table
 */
public class AuthTokenDao {
    private Connection conn;

    public AuthTokenDao (Connection conn) {
        this.conn = conn;
    }

    /**
     * Insert new row into AuthToken SQL table
     * @param myToken AuthToken object to be added to the table
     */
    public void createAuthToken(AuthToken myToken) {
        PreparedStatement stmt = null;
        try {
            try {
                String sql = "insert into AuthToken (auth_token, username) values (?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, myToken.getAuthToken());
                stmt.setString(2, myToken.getUsername());

                if (stmt.executeUpdate() != 1) {
                    System.out.println("createAuthToken failed: could not insert AuthToken");
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
     * Select a row from the AuthToken SQL table
     * @param authToken The primary key of the row to be read
     * @return The AuthToken object from the selected row of the SQL table
     */
    public AuthToken getAuthToken(String authToken) {
        AuthToken myAuth = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            try {
                String sql = "select * from AuthToken where auth_token = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, authToken);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    myAuth = new AuthToken();
                    myAuth.setAuthToken(rs.getString(1));
                    myAuth.setUsername(rs.getString(2));
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

        return myAuth;
    }

    /**
     * Deletes a row from the AuthToken SQL table
     * @param authToken The primary key of the row to be deleted
     */
    public void deleteAuthToken(String authToken) {
        PreparedStatement stmt = null;
        try {
            try {
                String sql = "delete from AuthToken where auth_token = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, authToken);

                if (stmt.executeUpdate() != 1) {
                    System.out.println("deleteAuthToken failed: could not delete AuthToken");
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
}
