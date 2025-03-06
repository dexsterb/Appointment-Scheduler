package DAO;

import utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ContactsDao class provides methods for accessing and manipulating contact records in the database.
 */
public class ContactsDao {

    /**
     * Retrieves a list of contacts from the database and returns them as an ObservableList.
     *
     * This method connects to the database, executes an SQL query to retrieve all contacts,
     * and processes the result set to create a list of Contact objects.
     *
     * @return An ObservableList containing all retrieved contacts.
     */
    public static ObservableList<Contacts> getContacts() {
        // Create an ObservableList to store all retrieved contacts
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

        try {
            // Define the SQL query to retrieve all contacts from the database
            String sql = "SELECT * FROM CONTACTS";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set to process each contact record
            while (rs.next()) {
                // Retrieve the contact details from the result set
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                // Create a Contacts object to represent the contact
                Contacts contacts = new Contacts(contactId, contactName, email);

                // Add the contact object to the list of all contacts
                allContacts.add(contacts);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return the list of all retrieved contacts
        return allContacts;
    }


    /** Retrieves a contact record from the database by its unique identifier (contactId).
     @param contactId The unique identifier of the contact to retrieve.
     @return The Contacts object representing the retrieved contact, or null if not found.
     @throws SQLException If there is an error while accessing the database.
     */
    public static Contacts getContactById(int contactId) throws SQLException {
        // Define the SQL query to retrieve a contact by its ID
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";

        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Set the parameter in the SQL query to the specified contact ID
        ps.setInt(1, contactId);

        // Execute the SQL query and get the result set
        ResultSet rs = ps.executeQuery();

        // Check if the result set contains a contact record
        while (rs.next()) {
            // Retrieve the contact details from the result set
            int contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            // Create a Contacts object to represent the contact
            Contacts contacts = new Contacts(contactID, name, email);

            // Return the contact object
            return contacts;
        }

        // Return null if no contact with the specified ID was found
        return null;
    }
}