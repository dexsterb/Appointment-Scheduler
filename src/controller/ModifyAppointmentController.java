package controller;

import DAO.AppointmentsDao;
import DAO.ContactsDao;
import DAO.CustomersDao;
import DAO.UsersDao;
import utils.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/** The controller class for modifying appointments in the scheduling application.
 This class handles the logic for updating appointment details, input validation,
 and navigation within the application.
 */
public class ModifyAppointmentController implements Initializable {

    @FXML private ComboBox<Contacts> updateAppointmentContactComboBox;
    @FXML private ComboBox<Customers> updateAppointmentCustomerIdComboBox;
    @FXML private TextField updateAppointmentDescriptionTextField;
    @FXML private DatePicker updateAppointmentEndDateBox;
    @FXML private ComboBox<LocalTime> updateAppointmentEndTimeComboBox;
    @FXML private TextField updateAppointmentIdTextField;
    @FXML private TextField updateAppointmentLocationTextField;
    @FXML private DatePicker updateAppointmentStartDateBox;
    @FXML private ComboBox<LocalTime> updateAppointmentStartTimeComboBox;
    @FXML private TextField updateAppointmentTitleTextField;
    @FXML private TextField updateAppointmentTypeTextField;
    @FXML private ComboBox<Users> updateAppointmentUserIdComboBox;
    @FXML Stage stage;
    @FXML Parent scene;

    /** Handles the action when the "Cancel" button is clicked, returning to the appointment menu.
     @param event The event triggered by clicking the "Cancel" button.
     @throws IOException If an I/O error occurs while navigating to the appointment menu.
     */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        appointmentMenu(event);
    }

    /** Handles the action when the "Save" button is clicked, validating user input and saving the modified appointment.
     If input validation fails, an error message is displayed to the user.
     @param event The event triggered by clicking the "Save" button.
     @throws IOException If an I/O error occurs while navigating to the appointment menu after saving.
     */
    @FXML
    void save(ActionEvent event) throws IOException {
        if (!validateInput()) {
            return;
        }

        try {
            int appointmentId = Integer.parseInt(updateAppointmentIdTextField.getText());
            String title = updateAppointmentTitleTextField.getText();
            String description = updateAppointmentDescriptionTextField.getText();
            String location = updateAppointmentLocationTextField.getText();
            String type = updateAppointmentTypeTextField.getText();

            LocalDateTime start = LocalDateTime.of(updateAppointmentStartDateBox.getValue(), updateAppointmentStartTimeComboBox.getValue());
            LocalDateTime end = LocalDateTime.of(updateAppointmentEndDateBox.getValue(), updateAppointmentEndTimeComboBox.getValue());

            int contact = updateAppointmentContactComboBox.getValue().getContactId();
            int customerId = updateAppointmentCustomerIdComboBox.getValue().getCustomerId();
            int userId = updateAppointmentUserIdComboBox.getValue().getUserId();

            AppointmentsDao.modifyAppointment(appointmentId, title, description, location, type, start, end, customerId, userId, contact);

            appointmentMenu(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /** Validates user input for the modified appointment. Checks for valid data, date and time constraints,
     business hours, weekdays, and appointment overlaps. Displays error messages for invalid input.
     @return True if the input is valid; false otherwise.
     */
    private boolean validateInput() {
        // Retrieve input values from the UI components
        String title = updateAppointmentTitleTextField.getText();
        String description = updateAppointmentDescriptionTextField.getText();
        String location = updateAppointmentLocationTextField.getText();
        String type = updateAppointmentTypeTextField.getText();

        LocalDate startDate = updateAppointmentStartDateBox.getValue();
        LocalDate endDate = updateAppointmentEndDateBox.getValue();
        LocalTime startTime = updateAppointmentStartTimeComboBox.getValue();
        LocalTime endTime = updateAppointmentEndTimeComboBox.getValue();

        // Create LocalDateTime instances for start and end times
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        // Check if the start date and time are in the future
        if (startDateTime.isBefore(LocalDateTime.now())) {
            inputErrorDialog("Appointment start date and time must be in the future.");
            return false;
        }

        // Check for validation conditions for empty fields
        if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()) {
            showAlert("Input error", "All fields must be filled with valid data.");
            return false;
        }

        if (startDate.isBefore(LocalDate.now())) {
            showAlert("Date error", "Appointment cannot be made prior to the current date.");
            return false;
        }

        if (!startDate.isEqual(endDate)) {
            showAlert("Date error", "Appointment start and end must be on the same day.");
            return false;
        }

        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            showAlert("Time error", "Appointment end time cannot be before or the same as the start time.");
            return false;
        }

    //    if (!isValidBusinessHour(startDateTime, endDateTime)) {
  //          return false;
  //      }

        if (!isValidWeekday(startDate)) {
            return false;
        }

        if (AppointmentController.checkForOverlap(
                updateAppointmentCustomerIdComboBox.getValue().getCustomerId(),
                Integer.parseInt(updateAppointmentIdTextField.getText()),
                startDateTime,
                endDateTime)) {
            return false;
        }

        return true;
    }

    /** Helper method to validate if the appointment time falls within business hours (8:00 a.m. - 10:00 p.m. ET).
     @param startDateTime The start date and time of the appointment.
     @param endDateTime   The end date and time of the appointment.
     @return True if the appointment is within business hours; false otherwise.
     */
    //This code was not necessary due to me not allowing times to fall outside the specified windows when converted to eastern time.
    //It can be implemented if hours were available outside the window.
   // private boolean isValidBusinessHour(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    //    LocalTime businessHourStart = LocalTime.of(8, 0);
   //     LocalTime businessHourEnd = LocalTime.of(22, 0);

   //     LocalTime startTime = startDateTime.toLocalTime();
    //    LocalTime endTime = endDateTime.toLocalTime();

   //     if (startTime.isBefore(businessHourStart) || endTime.isAfter(businessHourEnd)) {
   //         inputErrorDialog("Appointments must be scheduled between 8:00 a.m. and 10:00 p.m. ET on weekdays.");
   //         return false;
   //     }

  //      return true;
  //  }}

    /** Helper method to validate if the appointment date is a weekday (not Saturday or Sunday).
     @param date The appointment date.
     @return True if the date is a weekday; false if it's a weekend (Saturday or Sunday).
     */
    private boolean isValidWeekday(LocalDate date) {
        // Check if the day of the week is a weekend (Saturday or Sunday)
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            inputErrorDialog("Appointments cannot be scheduled on weekends.");
            return false;
        }
        return true;
    }

    /** Displays an error dialog with the specified message. This method creates and shows
     an Alert dialog of type ERROR with a warning title and "Input error" header text.
     @param message The error message to be displayed in the dialog.
     */
    private void inputErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Input error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Populates the form fields with data from an existing appointment for modification.
     @param appointment The appointment to retrieve data from.
     @throws SQLException If a database access error occurs while retrieving data.
     */
    public void retrieveAppointmentData(Appointments appointment) throws SQLException {
        updateAppointmentIdTextField.setText(String.valueOf(appointment.getAppointmentId()));
        updateAppointmentTitleTextField.setText(appointment.getAppointmentTitle());
        updateAppointmentDescriptionTextField.setText(appointment.getAppointmentDescription());
        updateAppointmentLocationTextField.setText(appointment.getAppointmentLocation());
        updateAppointmentTypeTextField.setText(appointment.getAppointmentType());
        updateAppointmentContactComboBox.setValue(ContactsDao.getContactById(appointment.getContactId()));
        updateAppointmentCustomerIdComboBox.setValue(AppointmentsDao.getCustomerById(appointment.getCustomerId()));
        updateAppointmentUserIdComboBox.setValue(UsersDao.getUserById(appointment.getUserId()));
        updateAppointmentStartTimeComboBox.setValue(appointment.getAppointmentStart().toLocalTime());
        updateAppointmentEndDateBox.setValue(LocalDate.from(appointment.getAppointmentEnd().toLocalDate()));
        updateAppointmentEndTimeComboBox.setValue(appointment.getAppointmentEnd().toLocalTime());
        updateAppointmentStartDateBox.setValue(LocalDate.from(appointment.getAppointmentStart().toLocalDate()));
    }

    /** Initializes the controller after its root element has been completely processed.
     @param url The location used to resolve relative paths for the root object,
     or null if the location is not known.
     @param resourceBundle The resource bundle for the root object, or null if there is none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();
    }

    /** Initializes the ComboBoxes in the appointment modification form by populating them
     with data retrieved from corresponding DAO classes or utility methods. This method
     is called during controller initialization to set up selection options for contacts,
     customers, users, start times, and end times.
     */
    private void initializeComboBoxes() {
        updateAppointmentContactComboBox.setItems(ContactsDao.getContacts());
        updateAppointmentCustomerIdComboBox.setItems(CustomersDao.getCustomers());
        updateAppointmentUserIdComboBox.setItems(UsersDao.getAllUsers());
        updateAppointmentStartTimeComboBox.setItems(Time.startTime());
        updateAppointmentEndTimeComboBox.setItems(Time.endTime());
    }

    /** Redirects the user to the Appointment management menu when called. It loads the
     Appointment.fxml view and sets it as the active scene, allowing the user to manage
     appointments.
     @param event An ActionEvent representing a button click or user action that triggers
     the method.
     @throws IOException If an error occurs while loading the Appointment.fxml view.
     */
    private void appointmentMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles events related to updating the ComboBox for selecting a contact in the
     appointment modification form. This method is triggered when the user
     interacts with the contact selection ComboBox.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the contact ComboBox.
     */
    public void updateAppointmentContactComboBox(ActionEvent event) {
    }

    /** Handles events related to updating the date selection field for the appointment
     modification form's start date. This method is triggered when the user
     interacts with the start date DatePicker.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the start date field.
     */
    public void updateAppointmentStartDateBox(ActionEvent event) {
    }

    /** Handles events related to updating the date selection field for the appointment
     modification form's end date. This method is triggered when the user
     interacts with the end date DatePicker.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the end date field.
     */
    public void updateAppointmentEndDateBox(ActionEvent event) {
    }

    /** Handles events related to updating the ComboBox for selecting the start time in the
     appointment modification form. This method is triggered when the user
     interacts with the start time ComboBox.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the start time ComboBox.
     */
    public void updateAppointmentStartTimeComboBox(ActionEvent event) {
    }

    /** Handles events related to updating the ComboBox for selecting the end time in the
     appointment modification form. This method is triggered when the user
     interacts with the end time ComboBox.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the end time ComboBox.
     */
    public void updateAppointmentEndTimeComboBox(ActionEvent event) {
    }

    /** Handles events related to updating the ComboBox for selecting a customer in the
     appointment modification form. This method is triggered when the user
     interacts with the customer selection ComboBox.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the customer ComboBox.
     */
    public void updateAppointmentCustomerIdComboBox(ActionEvent event) {
    }

    /** Handles events related to updating the appointment ID text field in the appointment
     modification form. This method is typically triggered when the user interacts with
     the appointment ID text field.
     @param event An ActionEvent representing a user action or event that triggers the
     method. This event is associated with updating the appointment ID text field.
     */
    public void updateAppointmentIdTextField(ActionEvent event) {
    }
}
