package DAO;

import utils.JDBC;
import model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/** A data access object (DAO) class for managing first-level division-related data in the application.
 */
public class FirstLevelDivisionsDao {

    /** Retrieves a list of all first-level divisions.
     @return An ObservableList containing all retrieved first-level divisions.
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        // Create an ObservableList to store all retrieved first-level divisions
        ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();

        try {
            // Define the SQL query to retrieve all first-level divisions
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create first-level division objects
            while (rs.next()) {
                // Retrieve division details from the result set
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                // Create a FirstLevelDivisions object to represent the division
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionId, divisionName, countryId);

                // Add the division to the list of all divisions
                allDivisions.add(firstLevelDivisions);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return the list of all retrieved first-level divisions
        return allDivisions;
    }

    /** Retrieves a list of first-level divisions with the total number of customers in each division.
     @return An ObservableList containing divisions with their respective total customer counts.
     */
    public static ObservableList<FirstLevelDivisions.DivisionTotalCustomers> getDivisionsWithTotalCustomers() {
        // Create an ObservableList to store divisions with total customer counts
        ObservableList<FirstLevelDivisions.DivisionTotalCustomers> divisionsWithTotalCustomers = FXCollections.observableArrayList();
        try {
            // Define the SQL query to retrieve divisions and their total customer counts
            String sql = "SELECT fd.Division, COUNT(c.Customer_ID) AS TotalCustomers " +
                    "FROM first_level_divisions fd " +
                    "LEFT JOIN customers c ON fd.Division_ID = c.Division_ID " +
                    "GROUP BY fd.Division";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create DivisionTotalCustomers objects
            while (rs.next()) {
                // Retrieve division details from the result set
                String divisionName = rs.getString("Division");
                int totalCustomers = rs.getInt("TotalCustomers");

                // Create a DivisionTotalCustomers object to represent the division and its total customer count
                FirstLevelDivisions.DivisionTotalCustomers divisionTotalCustomers = new FirstLevelDivisions.DivisionTotalCustomers(divisionName, totalCustomers);

                // Add the division and total customer count to the list
                divisionsWithTotalCustomers.add(divisionTotalCustomers);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }
        // Return the list of divisions with total customer counts
        return divisionsWithTotalCustomers;
    }

    /** Retrieves a list of first-level divisions in a specific country.
     @param countryId The ID of the country for which divisions are to be retrieved.
     @return An ObservableList containing first-level divisions in the specified country.
     */
    public static ObservableList<FirstLevelDivisions> getDivisionsByCountry(int countryId) {
        // Create an ObservableList to store first-level divisions in the specified country
        ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();

        try {
            // Define the SQL query to retrieve first-level divisions in the specified country
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = ?";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Set the parameter in the SQL query to the specified country ID
            ps.setInt(1, countryId);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create first-level division objects
            while (rs.next()) {
                // Retrieve division details from the result set
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                // Create a FirstLevelDivisions object to represent the division
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionId, divisionName, countryId);

                // Add the division to the list of all divisions
                allDivisions.add(firstLevelDivisions);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return the list of first-level divisions in the specified country
        return allDivisions;
    }

    /** Retrieves a first-level division by its ID.
     @param divisionId The ID of the division to retrieve.
     @return A FirstLevelDivisions object representing the division, or null if not found.
     */
    public static FirstLevelDivisions getDivisionsById(int divisionId) {

        try {
            // Define the SQL query to retrieve a division by its ID
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Set the parameter in the SQL query to the specified division ID
            ps.setInt(1, divisionId);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Check if the result set contains a division record
            while (rs.next()) {
                // Retrieve division details from the result set
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                // Create a FirstLevelDivisions object to represent the division
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionId, divisionName, countryId);

                // Return the division object
                return firstLevelDivisions;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return null if no division with the specified ID was found
        return null;
    }
}