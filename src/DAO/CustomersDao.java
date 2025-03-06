package DAO;


import utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** A data access object (DAO) class for managing customer-related data in the application.
 */
public class CustomersDao {

    /** Retrieves a list of all customers with associated division and country information.
     @return An ObservableList containing all retrieved customers.
     */
    public static ObservableList<Customers> getCustomers() {
        // Create an ObservableList to store all retrieved customers
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        try {
            // Define the SQL query to retrieve customers with division and country details
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, first_level_divisions.Division, first_level_divisions.Division_Id, countries.country from customers, first_level_divisions, countries\n" +
                    "WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";
            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create customer objects
            while (rs.next()) {
                // Retrieve customer details from the result set
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                String division = rs.getString("Division");
                int divisionId = rs.getInt("Division_Id");
                String country = rs.getString("Country");

                // Create a Customers object to represent the customer
                Customers customers = new Customers(customerId, customerName, address, postalCode, phoneNumber, divisionId, division, country);

                // Add the customer to the list of all customers
                allCustomers.add(customers);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return the list of all retrieved customers
        return allCustomers;
    }

    /** Adds a new customer to the database.
     @param customerName     The name of the customer.
     @param customerAddress  The address of the customer.
     @param customerPostalCode The postal code of the customer.
     @param customerPhone    The phone number of the customer.
     @param divisionId       The ID of the division associated with the customer.
     @throws SQLException If there is an error accessing the database.
     */
    public static void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId) throws SQLException {
        // Define the SQL query to insert a new customer
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        // Set the parameters in the SQL query
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPostalCode);
        ps.setString(4, customerPhone);
        ps.setInt(5, divisionId);

        // Execute the SQL query to add the customer
        ps.executeUpdate();
    }

    /** Deletes a customer from the database.
     @param customerId The ID of the customer to be deleted.
     @throws SQLException If there is an error accessing the database.
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        // Define the SQL query to delete a customer
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        // Set the parameter in the SQL query
        ps.setInt(1, customerId);

        // Execute the SQL query to delete the customer
        ps.executeUpdate();
    }

    /** Modifies an existing customer in the database.
     @param customerId        The ID of the customer to be modified.
     @param customerName      The updated name of the customer.
     @param customerAddress   The updated address of the customer.
     @param customerPostalCode The updated postal code of the customer.
     @param customerPhone     The updated phone number of the customer.
     @param divisionId        The updated division ID associated with the customer.
     @throws SQLException If there is an error accessing the database.
     */
    public static void modifyCustomer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId) throws SQLException {
        // Define the SQL query to update an existing customer
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        // Set the parameters in the SQL query
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerPostalCode);
        ps.setString(4, customerPhone);
        ps.setInt(5, divisionId);
        ps.setInt(6, customerId);

        // Execute the SQL query to modify the customer
        ps.executeUpdate();
    }



}
