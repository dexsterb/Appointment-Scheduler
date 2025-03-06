package DAO;

import utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A data access object (DAO) class for managing country-related data in the application.
 */
public class CountriesDao {

    /** Retrieves a list of all countries.
     @return An ObservableList containing all retrieved countries.
     */
    public static ObservableList<Countries> getCountries() {
        // Create an ObservableList to store all retrieved countries
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();

        try {
            // Define the SQL query to retrieve all countries
            String sql = "SELECT * FROM COUNTRIES";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create country objects
            while (rs.next()) {
                // Retrieve country details from the result set
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                // Create a Countries object to represent the country
                Countries countries = new Countries(countryId, countryName);

                // Add the country to the list of all countries
                allCountries.add(countries);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of all retrieved countries
        return allCountries;
    }

    /** Retrieves a country by its associated division ID.
     @param divisionID The ID of the division associated with the country.
     @return A Countries object representing the country, or null if not found.
     @throws SQLException If there is an error accessing the database.
     */
    public static Countries getCountryByDivisionId(int divisionID) throws SQLException {
        // Define the SQL query to retrieve a country by its associated division ID
        String sql = "SELECT C.* FROM COUNTRIES AS C INNER JOIN FIRST_LEVEL_DIVISIONS AS D ON C.Country_ID = D.Country_ID AND D.Division_ID = ?";

        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Set the parameter in the SQL query to the specified division ID
        ps.setInt(1, divisionID);

        // Execute the SQL query and get the result set
        ResultSet rs = ps.executeQuery();

        // Check if the result set contains a country record
        while (rs.next()) {
            // Retrieve country details from the result set
            int countryId = rs.getInt("Country_ID");
            String country = rs.getString("Country");

            // Create a Countries object to represent the country
            Countries countries = new Countries(countryId, country);

            // Return the country object
            return countries;
        }

        // Return null if no country with the specified division ID was found
        return null;
    }

}