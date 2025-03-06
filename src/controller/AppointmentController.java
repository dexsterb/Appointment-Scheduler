package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import DAO.AppointmentsDao;
import model.Appointments;

/** This class is responsible for managing the appointment-related functionality of the application.
 It handles the display, creation, modification, and deletion of appointments.
 The class implements the Initializable interface to initialize the controller during JavaFX application startup.
 */
public class AppointmentController implements Initializable {

    // Define the FXML elements
    @FXML private TableColumn<Appointments, Integer> appointmentContactIdColumn;

    @FXML private TableColumn<Appointments, Integer> appointmentCustomerIdColumn;

    @FXML private TableColumn<Appointments, String> appointmentDescriptionColumn;

    @FXML private TableColumn<Appointments, Calendar> appointmentEndColumn;

    @FXML private TableColumn<Appointments, Integer> appointmentIdColumn;

    @FXML private TableColumn<Appointments, String> appointmentLocationColumn;

    @FXML private TableColumn<Appointments, Calendar> appointmentStartColumn;

    @FXML private TableView<Appointments> appointmentTableView;

    @FXML private TableColumn<Appointments, String> appointmentTitleColumn;

    @FXML private TableColumn<Appointments, String> appointmentTypeColumn;

    @FXML private TableColumn<Appointments, Integer> appointmentUserIdColumn;

    @FXML Stage stage;
    @FXML Parent scene;

    /** Handles the action when the "View All Appointments" button is clicked, displaying all appointments.
     @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void appointmentsViewAllButton(ActionEvent event) {
        setAppointments(AppointmentsDao.getAppointments());
    }

    /** Handles the action when the "By Month" button is clicked, displaying appointments for the current month.
     @param event The ActionEvent triggered by the button click.
     @throws SQLException If there is an error retrieving appointments from the database.
     */
    @FXML
    void appointmentByMonth(ActionEvent event) throws SQLException {
        setAppointments(AppointmentsDao.getAppointmentsByCurrentMonth());
    }

    /** Handles the action when the "By Week" button is clicked, displaying appointments for the current week.
     @param event The ActionEvent triggered by the button click.
     @throws SQLException If there is an error retrieving appointments from the database.
     */
    @FXML
    void appointmentByWeek(ActionEvent event) throws SQLException {
        setAppointments(AppointmentsDao.getAppointmentByCurrentWeek());
    }

    /** Handles the action when the "Main Menu" button is clicked, navigating to the main menu view.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If there is an error loading the main menu view.
     */
    @FXML
    void appointmentMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles the action when the "Add Appointment" button is clicked, navigating to the add appointment view.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If there is an error loading the add appointment view.
     */
    @FXML
    void appAddButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Define an ObservableList to store appointments
    static ObservableList<Appointments> appointments;

    /** Handles the action when the "Delete" button is clicked, allowing the user to delete a selected appointment.
     @param event The ActionEvent triggered by the button click.
     @throws SQLException If there is an error deleting the appointment from the database.
     */
    @FXML
    void appDeleteButton(ActionEvent event) throws SQLException {
        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            showAlert("Error", "Warning", "Select an appointment to delete.");
            return;
        }

        if (showConfirmationDialog("Confirmation", "Confirm",
                "Proceeding will result in the permanent deletion of this appointment. Do you wish to proceed?")) {
            int appointmentId = selectedAppointment.getAppointmentId();
            String appointmentType = selectedAppointment.getAppointmentType();

            try {
                AppointmentsDao.deleteAppointment(appointmentId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            showInformationAlert("Appointment cancelled", "Appointment successfully cancelled.",
                    appointmentType + " appointment with appointment ID #" + appointmentId + " has been cancelled.");

            setAppointments(AppointmentsDao.getAppointments());
        }
    }

    /** Handles the action when the "Update" button is clicked, allowing the user to update a selected appointment.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If there is an error loading the modify appointment view.
     */
    @FXML
    void appointmentUpdateButton(ActionEvent event) throws IOException {
        Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            showAlert("Error", "Warning", "Select an appointment to update.");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointmentController modifyAppointmentController = loader.getController();

            try {
                modifyAppointmentController.retrieveAppointmentData(selectedAppointment);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /** Checks for appointment overlaps for a given customer, appointment ID, start time, and end time.
     @param customerId The ID of the customer for whom the appointment is being checked.
     @param appointmentId The ID of the appointment being checked.
     @param start The start time of the appointment.
     @param end The end time of the appointment.
     @return True if there is an overlap with an existing appointment; otherwise, false.
     */
    public static boolean checkForOverlap(int customerId, int appointmentId, LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointments> appointmentList = AppointmentsDao.getAppointments();

        for (Appointments a : appointmentList) {
            if (customerId != a.getCustomerId() || appointmentId == a.getAppointmentId()) {
                continue;
            }

            LocalDateTime appointmentStart = a.getAppointmentStart();
            LocalDateTime appointmentEnd = a.getAppointmentEnd();

            if ((start.isAfter(appointmentStart) || start.isEqual(appointmentStart)) && start.isBefore(appointmentEnd)) {
                showAlert("Error", "Appointment Overlap",
                        "Appointment conflicts with customer ID#" + customerId + "'s existing appointment. Please correct.");
                return true;
            } else if (end.isAfter(appointmentStart) && (end.isBefore(appointmentEnd) || end.isEqual(appointmentEnd))) {
                showAlert("Error", "Appointment Overlap",
                        "Appointment conflicts with customer ID#" + customerId + "'s existing appointment. Please correct.");
                return true;
            } else if ((start.isBefore(appointmentStart) || start.isEqual(appointmentStart)) &&
                    (end.isAfter(appointmentEnd) || end.isEqual(appointmentEnd))) {
                showAlert("Error", "Appointment Overlap",
                        "Appointment conflicts with customer ID#" + customerId + "'s existing appointment. Please correct.");
                return true;
            }
        }
        return false;
    }

    /** Initializes and displays an error alert dialog with the specified title, header text, and content text.
     @param title The title of the error alert.
     @param headerText The header text of the error alert.
     @param contentText The content text of the error alert.
     */    private static void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /** Initializes and displays an information alert dialog with the specified title, header text, and content text.
     @param title The title of the information alert.
     @param headerText The header text of the information alert.
     @param contentText The content text of the information alert.
     */
    private void showInformationAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /** Initializes the controller by setting the appointments in the table view.
     @param url The location used to resolve relative paths for root object or null if the location is not known.
     @param resourceBundle The resources used to localize the root object or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAppointments(AppointmentsDao.getAppointments());
    }

    /** Sets the provided list of appointments in the table view.
     @param appointmentData An ObservableList containing appointment data to display in the table.
     */
    private void setAppointments(ObservableList<Appointments> appointmentData) {
        appointmentTableView.setItems(appointmentData);
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentContactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /** Displays a confirmation dialog with the specified title, header text, and content text and returns the user's choice.
     @param title The title of the confirmation dialog.
     @param headerText The header text of the confirmation dialog.
     @param contentText The content text of the confirmation dialog.
     @return True if the user confirms; otherwise, false.
     */
    private boolean showConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.map(buttonType -> buttonType == ButtonType.OK).orElse(false);
    }

}
