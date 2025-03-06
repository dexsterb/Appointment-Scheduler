package main;
import utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;

/** The main class that starts the JavaFX application.
 * author: Dexster Bowman
 * Lambda: Included in the ReportsController.
 */
public class Main extends Application {

    /** The entry point of the JavaFX application.
     @param stage The primary stage for the application.
     @throws Exception If an exception occurs during startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load the login.fxml file as the application's root
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));

        // Set the scene for the primary stage
        stage.setScene(new Scene(root, 500, 350));

        // Display the primary stage
        stage.show();
    }

    /** The main method of the application.
     @param args The command-line arguments.
     @throws SQLException If a SQL exception occurs.
     */
    public static void main(String[] args) throws SQLException {
       //Test French language without rebooting my system repeatedly
        // Locale.setDefault(new Locale("fr"));

        // Open a database connection
        JDBC.openConnection();

        // Launch the JavaFX application
        launch(args);

        // Close the database connection when the application exits
        JDBC.closeConnection();
    }
}