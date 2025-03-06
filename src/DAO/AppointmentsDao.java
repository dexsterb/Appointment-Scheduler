package DAO;


import model.Customers;
import utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.*;
import java.time.LocalDateTime;

/** The AppointmentsDao class provides data access methods for managing appointments.
 */
public class AppointmentsDao {

    /** Retrieves a list of all appointments from the database.
     @return An ObservableList containing all appointments.
     */
    public static ObservableList<Appointments> getAppointments() {
        // Create an ObservableList to store all retrieved appointments
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        try {
            // SQL query to retrieve all appointments
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create appointment objects
            while (rs.next()) {
                // Extract appointment details from the result set
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime appointmentStart = start.toLocalDateTime();
                Timestamp end = rs.getTimestamp("End");
                LocalDateTime appointmentEnd = end.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                // Create an Appointments object with the retrieved data
                Appointments appointments = new Appointments(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);

                // Add the appointment to the list of all appointments
                allAppointments.add(appointments);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return the list of all retrieved appointments
        return allAppointments;
    }

    /** Deletes all appointments associated with a specified customer ID.
     @param customerId The unique identifier of the customer whose appointments will be deleted.
     @throws SQLException If a database error occurs during the deletion process.
     */
    public static void deleteAppointmentsByCustomerId(int customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);

        ps.executeUpdate();
    }


    /** Adds a new appointment to the database.
     @param title       The title of the appointment.
     @param description The description of the appointment.
     @param location    The location of the appointment.
     @param type        The type of the appointment.
     @param start       The start date and time of the appointment.
     @param end         The end date and time of the appointment.
     @param customerId  The ID of the associated customer.
     @param userId      The ID of the user responsible for the appointment.
     @param contactId   The ID of the associated contact.
     @throws SQLException If there is an error accessing the database.
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException {
        // SQL query to insert a new appointment record into the database
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Set parameters for the SQL query
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);

        // Execute the SQL query to add the appointment
        ps.executeUpdate();
    }

    /** Modifies an existing appointment in the database.
     @param appointmentId The ID of the appointment to be modified.
     @param title         The updated title of the appointment.
     @param description   The updated description of the appointment.
     @param location      The updated location of the appointment.
     @param type          The updated type of the appointment.
     @param start         The updated start date and time of the appointment.
     @param end           The updated end date and time of the appointment.
     @param customerId    The updated ID of the associated customer.
     @param userId        The updated ID of the user responsible for the appointment.
     @param contactId     The updated ID of the associated contact.
     @throws SQLException If there is an error accessing the database.
     */
    public static void modifyAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException {

        // SQL query to update an existing appointment record in the database
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Set parameters for the SQL query
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, appointmentId);

        // Execute the SQL query to modify the appointment
        ps.executeUpdate();
    }

    /**
     * Deletes an appointment from the database.
     *
     * @param appointmentId The ID of the appointment to be deleted.
     * @throws SQLException If there is an error accessing the database.
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {

        // SQL query to delete an appointment record from the database
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Set the parameter for the SQL query
        ps.setInt(1, appointmentId);

        // Execute the SQL query to delete the appointment
        ps.executeUpdate();
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return A Customers object representing the customer, or null if not found.
     * @throws SQLException If there is an error accessing the database.
     */
    public static Customers getCustomerById(int id) throws SQLException {
        // Define the SQL query to retrieve a customer by their ID
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = ?";
        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        // Set the parameter in the SQL query
        ps.setInt(1, id);
        // Execute the SQL query and get the result set
        ResultSet rs = ps.executeQuery();

        // Check if the result set contains a customer record
        while (rs.next()) {
            // Retrieve customer details from the result set
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");

            // Create a Customers object to represent the customer
            return new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhone, divisionId);
        }

        // Return null if no customer with the specified ID was found
        return null;
    }



    /** Retrieves a list of appointments for the current month.
     @return An ObservableList of Appointments for the current month.
     @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointments> getAppointmentsByCurrentMonth() throws SQLException {
        ObservableList<Appointments> appointmentsByCurrentMonth = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(curdate())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime appointmentStart = start.toLocalDateTime();
                Timestamp end = rs.getTimestamp("End");
                LocalDateTime appointmentEnd = end.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);

                appointmentsByCurrentMonth.add(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsByCurrentMonth;
    }

    /** Retrieves a list of appointments for the current week.
     @return An ObservableList of Appointments for the current week.
     @throws SQLException If a database error occurs.
     */
    public static ObservableList<Appointments> getAppointmentByCurrentWeek() throws SQLException {
        ObservableList<Appointments> appointmentsByCurrentWeek = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(curdate())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime appointmentStart = start.toLocalDateTime();
                Timestamp end = rs.getTimestamp("End");
                LocalDateTime appointmentEnd = end.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);

                appointmentsByCurrentWeek.add(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsByCurrentWeek;
    }

    /** Retrieves a list of appointments for a specific customer.
     @param customerID The ID of the customer for whom to retrieve appointments.
     @return An ObservableList of Appointments for the specified customer.
     */
    public static ObservableList<Appointments> getAppointmentsByCustomerId(int customerID) {
        ObservableList<Appointments> appointmentsById = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                LocalDateTime appointmentStart = start.toLocalDateTime();
                Timestamp end = rs.getTimestamp("End");
                LocalDateTime appointmentEnd = end.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(appointmentId, title, description, location, type, appointmentStart, appointmentEnd, customerId, userId, contactId);

                appointmentsById.add(appointments);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointmentsById;
    }

    /** Retrieves a list of appointments grouped by month and type, along with the total count for each group.
     This method is used in the Reports controller.
     @return An ObservableList of Appointments grouped by month and type.
     */
    public static ObservableList<Appointments> getAppointmentsByMonthType() {
        ObservableList<Appointments> appointmentsByMonthType = FXCollections.observableArrayList();

        String sql = "SELECT monthname(start) AS Month, Type, COUNT(*) AS Total " +
                "FROM appointments " +
                "GROUP BY Type, monthname(start) " +
                "ORDER BY Total DESC, Month";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String appointmentMonth = rs.getString("Month");
                String appointmentType = rs.getString("Type");
                int appointmentTotal = rs.getInt("Total");

                Appointments appointment = new Appointments(appointmentMonth, appointmentType, appointmentTotal);
                appointmentsByMonthType.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsByMonthType;
    }


}