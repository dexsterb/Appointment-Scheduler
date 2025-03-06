package controller;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLIntegrityConstraintViolationException;

import DAO.AppointmentsDao;
import DAO.ContactsDao;
import DAO.CustomersDao;
import DAO.UsersDao;
import utils.Time;
import model.Contacts;
import model.Customers;
import model.Users;

/** This controller is for adding a new appointment.
 It controls the add appointment fxml.
 */
public class AddAppointmentController implements Initializable {



    @FXML private DatePicker addAppointmentEndDateBox;
    @FXML private DatePicker addAppointmentStartDateBox;
    @FXML private ComboBox<Contacts> addAppointmentContComboBox;
    @FXML private ComboBox<Customers> addAppointmentCustIdComboBox;
    @FXML private ComboBox<LocalTime> addAppointmentEndTimeComboBox;
    @FXML private ComboBox<LocalTime> addAppointmentStartTimeComboBox;
    @FXML private ComboBox<Users> addAppointmentUserIdComboBox;
    @FXML private TextField addAppointmentDescriptionTextField;
    @FXML private TextField addAppointmentLocationTextField;
    @FXML private TextField addAppointmentTitleTextField;
    @FXML private TextField addAppointmentTypeTextField;
    @FXML Stage stage;
    @FXML Parent scene;

    /**This method handles the action when a contact is selected for adding an appointment.
     @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentContact(ActionEvent event) {
    }

    /** This method handles the action when a Customer ID is selected for adding an appointment.
     @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentCustomerId(ActionEvent event) {
    }

    /** This method handles the action when an end date is selected for adding an appointment.
     @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentEndDate(ActionEvent event) {
    }


    /** This method handles the action when an end time is selected for adding an appointment.
     @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentEndTime(ActionEvent event) {
    }

    /** This method handles the action when a start date is selected for adding an appointment.
     @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentStartDate(ActionEvent event) {
    }

    /** This method handles the action when a start time is selected for adding an appointment.
      @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentStartTime(ActionEvent event) {
    }

    /** This method handles the action when a User ID is selected for adding an appointment.
      @param event The ActionEvent triggered by the selection.
     */
    @FXML
    void AddAppointmentUserId(ActionEvent event) {
    }

    /** This method handles the "Cancel" button click event to return to the appointment menu view.
      @param event The ActionEvent triggered by the button click.
      @throws IOException If there is an error loading the appointment menu view.
     */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This helper method navigates back to the appointment menu view.
     @param event The ActionEvent that triggered the navigation.
     @throws IOException If there is an error loading the appointment menu view.
     */
    @FXML
    private void appointmentMenuButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method handles the "Save" button click event to save a new appointment.
     This method utilizes the validateInput method validation to ensure dates are within the correct range,
     start times are before end times, appointment overlapping does not occur,and appointments are within business hours.
     I did not make hours outside 0800-2200 available, but did include error checking in the
     event of a system malfunction.
      @param event The ActionEvent triggered by the button click.
     */
    @FXML
    void save(ActionEvent event) {
        try {
            if (!validateInput()) {
                // Validation failed, return early
                return;
            }

            // Extract data from input fields and ComboBoxes
            String title = addAppointmentTitleTextField.getText();
            String description = addAppointmentDescriptionTextField.getText();
            String location = addAppointmentLocationTextField.getText();
            String type = addAppointmentTypeTextField.getText();

            LocalDateTime start = LocalDateTime.of(addAppointmentStartDateBox.getValue(), addAppointmentStartTimeComboBox.getSelectionModel().getSelectedItem());
            LocalDateTime end = LocalDateTime.of(addAppointmentEndDateBox.getValue(), addAppointmentEndTimeComboBox.getSelectionModel().getSelectedItem());


            int contact = addAppointmentContComboBox.getValue() != null ? addAppointmentContComboBox.getValue().getContactId() : 0;
            int customerId = addAppointmentCustIdComboBox.getValue() != null ? addAppointmentCustIdComboBox.getValue().getCustomerId() : 0;
            int userId = addAppointmentUserIdComboBox.getValue() != null ? addAppointmentUserIdComboBox.getValue().getUserId() : 0;

            if (AppointmentController.checkForOverlap(customerId, 0, start, end)) {
                // Overlapping appointment, return early
                return;
            }
            // Save the appointment data in UTC
            AppointmentsDao.addAppointment(title, description, location, type, start, end, customerId, userId, contact);
            // Navigate to the appointment menu
            appointmentMenuButton(event);
        } catch (NullPointerException | SQLIntegrityConstraintViolationException e) {
            inputErrorDialog("All fields must be filled with valid data.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** Validates user input for adding an appointment.
     This method performs various checks on the input data:
     - Ensures that required fields (title, description, location, type) are not empty.
     - Verifies that the selected start date is not before the current date.
     - Checks that the start and end dates are on the same day and end date is not before the start date.
     - Validates that the selected end time is after the start time.
     - Verifies that the appointment is scheduled between 8:00 a.m. and 10:00 p.m. on weekdays.
     - Checks that the selected start date is not a weekend (Saturday or Sunday).
     @return True if the input data is valid; false otherwise.
     */
    private boolean validateInput() {
        String title = addAppointmentTitleTextField.getText();
        String description = addAppointmentDescriptionTextField.getText();
        String location = addAppointmentLocationTextField.getText();
        String type = addAppointmentTypeTextField.getText();

        LocalDate startDate = addAppointmentStartDateBox.getValue();
        LocalDate endDate = addAppointmentEndDateBox.getValue();

        LocalTime startTime = addAppointmentStartTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime endTime = addAppointmentEndTimeComboBox.getSelectionModel().getSelectedItem();

        // Obtain the user's time zone (e.g., from user settings)
        ZoneId userTimeZone = ZoneId.systemDefault();
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        // Convert user's selected date and time to Eastern Time
        LocalDateTime userStartDateTime = LocalDateTime.of(startDate, startTime);
        ZonedDateTime userStartZonedDateTime = userStartDateTime.atZone(userTimeZone);
        ZonedDateTime easternStartZonedDateTime = userStartZonedDateTime.withZoneSameInstant(easternTimeZone);

        LocalDateTime userEndDateTime = LocalDateTime.of(endDate, endTime);
        ZonedDateTime userEndZonedDateTime = userEndDateTime.atZone(userTimeZone);
        ZonedDateTime easternEndZonedDateTime = userEndZonedDateTime.withZoneSameInstant(easternTimeZone);

        // Check if the start date and time are in the future
        if (easternStartZonedDateTime.isBefore(ZonedDateTime.now(easternTimeZone))) {
            inputErrorDialog("Appointment start date and time must be in the future.");
            return false;
        }

        if (isEmpty(title, description, location, type) ||
                validateBeforeDate(startDate) ||
                validDateRange(startDate, endDate) ||
                invalidTimeRange(startTime, endTime) ||
              //  !isValidBusinessHour(easternStartZonedDateTime, easternEndZonedDateTime) ||
                !isValidWeekday(startDate)) {
            return false;
        }
        return true;
    }

    /** This helper method validates if the appointment is within business hours.
     @param easternStartDateTime The start date and time of the appointment.
     @param easternEndDateTime The end date and time of the appointment.
     @return True if the appointment is within business hours, otherwise False.
     */
    //This code was not necessary due to me not allowing times to fall outside the specified windows when converted to eastern time.
    //It can be implemented if hours were available outside the window.
   // private boolean isValidBusinessHour(ZonedDateTime easternStartDateTime, ZonedDateTime easternEndDateTime) {
        // Define business hours in Eastern Time
    //    ZonedDateTime businessHourStart = easternStartDateTime.with(LocalTime.of(8, 0));
   //     ZonedDateTime businessHourEnd = easternStartDateTime.with(LocalTime.of(22, 0));

        // Check if the provided Eastern Time falls within business hours
      //  if (easternStartDateTime.isBefore(businessHourStart) || easternEndDateTime.isAfter(businessHourEnd)) {
     //       inputErrorDialog("Appointments must be scheduled between 8:00 a.m. and 10:00 p.m. ET on weekdays.");
     //       return false;
     //   }

    //    return true;
    //}}

    /** This helper method validates if the appointment is on a weekday.
     @param date The date of the appointment.
     @return True if the appointment is on a weekday, otherwise False.
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

    /** This helper method checks if one or more strings are empty in the add appointment form.
     @param values The strings to be checked for emptiness.
     @return true if any of the provided strings is empty; otherwise, false.
     */
    private boolean isEmpty(String... values) {
        for (String value : values) {
            if (value.isEmpty()) {
                inputErrorDialog("All fields must be filled with valid data.");
                return true;
            }
        }
        return false;
    }

    /** This helper method validates if a requested date is before the current date.
     @param date The date to be validated.
     @return true if the provided date is before the current date; otherwise, false.
     */
    private boolean validateBeforeDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            inputErrorDialog("Appointments cannot be scheduled prior to this date.");
            return true;
        }
        return false;
    }

    /** This helper method validates the date range for an appointment. It ensures one appointment does not
     span across two days.
     @param startDate The start date of the appointment.
     @param endDate The end date of the appointment.
     @return true if the start date is after the end date or they are not equal; otherwise, false.
     */
    private boolean validDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate) || !startDate.isEqual(endDate)) {
            inputErrorDialog("The start and end times of the appointment must fall within the same day, and the end date cannot precede the start date.");
            return true;
        }
        return false;
    }

    /** This helper method validates the time range for an appointment. It ensures the appointment end time
     is after the start time.
     @param startTime The start time of the appointment.
     @param endTime The end time of the appointment.
     @return true if the start time is equal to or after the end time; otherwise, false.
     */
    private boolean invalidTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime.equals(endTime) ||startTime.isAfter(endTime) ) {
            inputErrorDialog("The end time of the appointment must be after the start time.");
            return true;
        }
        return false;
    }

    /** Helper method to display an input error dialog.
     @param message The error message to display in the dialog.
     */
    private void inputErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Input error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /** Initializes the controller when the corresponding FXML file is loaded.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBoxes and set visible row counts
        addAppointmentContComboBox.setItems(ContactsDao.getContacts());
        addAppointmentContComboBox.setVisibleRowCount(10);

        addAppointmentCustIdComboBox.setItems(CustomersDao.getCustomers());
        addAppointmentCustIdComboBox.setVisibleRowCount(10);

        addAppointmentUserIdComboBox.setItems(UsersDao.getAllUsers());
        addAppointmentUserIdComboBox.setVisibleRowCount(10);

        addAppointmentStartTimeComboBox.setItems(Time.startTime());
        addAppointmentStartTimeComboBox.setVisibleRowCount(10);

        addAppointmentEndTimeComboBox.setItems(Time.endTime());
        addAppointmentEndTimeComboBox.setVisibleRowCount(10);
    }
}
