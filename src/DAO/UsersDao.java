package DAO;

import utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A data access object (DAO) class for managing user-related data in the application.
 */
public class UsersDao {

    /**
     * Retrieves a list of all users.
     *
     * @return An ObservableList containing all retrieved users.
     */
    public static ObservableList<Users> getAllUsers() {
        // Create an ObservableList to store all retrieved users
        ObservableList<Users> allUsers = FXCollections.observableArrayList();

        try {
            // Define the SQL query to retrieve all users
            String sql = "SELECT * FROM USERS";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create user objects
            while (rs.next()) {
                // Retrieve user details from the result set
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                // Create a Users object to represent the user
                Users users = new Users(userId, userName, password);

                // Add the user to the list of all users
                allUsers.add(users);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return the list of all retrieved users
        return allUsers;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A Users object representing the user, or null if not found.
     * @throws SQLException If a SQL error occurs.
     */
    public static Users getUserById(int id) throws SQLException {
        // Define the SQL query to retrieve a user by their ID
        String sql = "SELECT * FROM users where User_ID = ?";

        // Prepare a SQL statement using the JDBC connection
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        // Set the parameter in the SQL query to the specified user ID
        ps.setInt(1, id);

        // Execute the SQL query and get the result set
        ResultSet rs = ps.executeQuery();

        // Check if the result set contains a user record
        while (rs.next()) {
            // Retrieve user details from the result set
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");

            // Create a Users object to represent the user
            Users users = new Users(userId, userName, password);

            // Return the user object
            return users;
        }

        // Return null if no user with the specified ID was found
        return null;
    }

    /**
     * Validates a user's credentials.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return The user ID if valid, or -1 if invalid.
     */
    public static int validateUser(String username, String password) {
        try {
            // Define the SQL query to validate user credentials
            String sql = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";

            // Prepare a SQL statement using the JDBC connection
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute the SQL query and get the result set
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set to validate the credentials
            while (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        return rs.getInt("User_ID");
                    }
                }
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions and print the stack trace if an error occurs
            e.printStackTrace();
        }

        // Return -1 if user credentials are invalid
        return -1;
    }
}
