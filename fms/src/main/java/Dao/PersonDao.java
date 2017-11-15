package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Person;

/**
 * Performs SQL queries on the Person table
 */
public class PersonDao {
    private Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new row in the person SQL table
     * @param myPerson The person object to be added to the table
     * @return The person ID
     */
    public void createPerson(Person myPerson) {
        PreparedStatement stmt = null;
        try {
            try {
                String sql = "insert into Person values (?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, myPerson.getPersonId());
                stmt.setString(2, myPerson.getDescendant());
                stmt.setString(3, myPerson.getFirstName());
                stmt.setString(4, myPerson.getLastName());
                stmt.setString(5, myPerson.getGender());
                stmt.setString(6, myPerson.getFather());
                stmt.setString(7, myPerson.getMother());
                stmt.setString(8, myPerson.getSpouse());

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
     * Selects and returns a row from the person SQL table
     * @param personId the Id of the person to be read
     * @return the person information
     */
    public Person getPerson(String personId) {
        Person myPerson = null;
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Person where person_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, personId);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    myPerson = new Person();
                    myPerson.setPersonId(rs.getString(1));
                    myPerson.setDescendant(rs.getString(2));
                    myPerson.setFirstName(rs.getString(3));
                    myPerson.setLastName(rs.getString(4));
                    myPerson.setGender(rs.getString(5));
                    myPerson.setFather(rs.getString(6));
                    myPerson.setMother(rs.getString(7));
                    myPerson.setSpouse(rs.getString(8));
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
        return myPerson;
    }

    /**
     * Selects and returns multiple rows from the person SQL table
     * @param username the username of the rows to be returned
     * @return an array of people objects
     */
    public ArrayList<Person> getPeople(String username) {
        ArrayList<Person> people = new ArrayList<Person>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from Person where descendant = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Person myPerson = new Person();
                    myPerson.setPersonId(rs.getString(1));
                    myPerson.setDescendant(rs.getString(2));
                    myPerson.setFirstName(rs.getString(3));
                    myPerson.setLastName(rs.getString(4));
                    myPerson.setGender(rs.getString(5));
                    myPerson.setFather(rs.getString(6));
                    myPerson.setMother(rs.getString(7));
                    myPerson.setSpouse(rs.getString(8));
                    people.add(myPerson);
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
        return people;
    }

    /**
     * Deletes a row in the person SQL table
     * @param descendant The username of the person row to be deleted
     */
    public void deletePerson(String descendant) {
        try {
            String sql = "delete from Person where descendant = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,descendant);

            if (stmt.executeUpdate() != 1) {
                System.out.println("deletePerson failed: couldn't delete person");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
