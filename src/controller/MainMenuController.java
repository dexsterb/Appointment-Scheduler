package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** Controller class for the main menu view.
 */
public class MainMenuController implements Initializable {

    @FXML Stage stage;
    @FXML Parent scene;

    /** Handles the action when the "Customer Menu" button is clicked.
     Takes the user to the customer's menu.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If an error occurs during loading the Customer view.
     */
    @FXML
    void customerMenuButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles the action when the "Report Menu" button is clicked.
     Takes the user to the report's menu.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If an error occurs during loading the Reports view.
     */
    @FXML
    void reportMenuButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles the action when the "Appointment Menu" button is clicked.
     Takes the user to the appointment's menu.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If an error occurs during loading the Appointments view.
     */
    @FXML
    void appointmentMenuButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles the action when the "Logout" button is clicked.
     Logs the user out and takes them back to the login screen.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If an error occurs during loading the Login view.
     */
    @FXML
    void logoutButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Initializes the controller after its root element has been completely processed.
     @param url The location used to resolve relative paths for the root object,
     or null if the location is not known.
     @param resourceBundle The resource bundle for the root object, or null if there is none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}