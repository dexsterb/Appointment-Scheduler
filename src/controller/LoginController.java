package controller;

import DAO.AppointmentsDao;
import DAO.UsersDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import model.Appointments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/** The LoginController class controls the behavior of the login view in the application.
 It handles user authentication, displays appropriate messages, and manages user interface elements.
 */
public class LoginController implements Initializable {

    @FXML private Label loginLabel;

    @FXML private Button loginExitButton;

    @FXML private Button loginButton;

    @FXML private TextField loginPasswordTextField;

    @FXML private Label loginTimeZoneLabel;

    @FXML private TextField loginUsernameTextField;

    @FXML private Label passwordLabel;

    @FXML private Label usernameLabel;

    @FXML private Label warningText;

    @FXML Stage stage;

    @FXML Parent scene;

    /** Switches the application view to the main menu.
     @param event The ActionEvent triggered by a button click or user interaction.
     @throws IOException If there is an error loading the main menu view.
     */
    @FXML
    private void mainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Exits the application when the exit button is clicked.
     @param event The ActionEvent associated with the exit button click.
     */
    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    /** Displays an information alert dialog with the specified title, header text, and content text.
     *
     * @param title The title of the information alert.
     * @param headerText  The header text of the information alert.
     * @param contentText The content text of the information alert.
     */
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

    /** Displays an error alert dialog with the specified title, header text, and content text.
     @param title       The title of the error alert.
     @param headerText  The header text of the error alert.
     @param contentText The content text of the error alert.
     */
    private void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

    /** Handles the login button click event, attempts user authentication, and redirects to the main menu upon successful login.
     @param event The ActionEvent associated with the login button click.
     @throws IOException  If an error occurs while redirecting to the main menu view.
     @throws SQLException If an error occurs during database interaction.
     */
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        // Define the name of the log file and create a File object for it.
        String fileName = "login_activity.txt";
        File file = new File(fileName);

        // Start a try-with-resources block to automatically close resources (fileWriter and printWriter).
        try (FileWriter fileWriter = new FileWriter(file, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            // Get the username and password from the login text fields.
            String username = loginUsernameTextField.getText();
            String password = loginPasswordTextField.getText();

            // Validate the user's credentials and get the user ID from the database.
            int userId = UsersDao.validateUser(username, password);

            // Load language resource bundle based on the user's default locale.
            ResourceBundle resourceBundle = ResourceBundle.getBundle("language/lang", Locale.getDefault());

            // Check if the user's default language is French or English.
            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {

                // If the user is successfully authenticated (userId > 0):
                if (userId > 0) {
                    // Create a list to store appointments within the next 15 minutes.
                    ObservableList<Appointments> appointmentIn15 = FXCollections.observableArrayList();
                    // Get a list of all appointments from the database.
                    ObservableList<Appointments> appointments = AppointmentsDao.getAppointments();

                    // Get the current time in the system's default time zone and convert it to UTC.
                    ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Etc/UTC"));

                    // Log the successful login attempt including the username and login time.
                    printWriter.println("Successful login attempt: User " + username + " Time " + zonedDateTime);

                    // Get the current time and time 15 minutes from now.
                    LocalDateTime localDateTime = LocalDateTime.now();
                    LocalDateTime fifteenMinuteAdd = localDateTime.plusMinutes(15);

                    // Check if there are any appointments within the next 15 minutes.
                    if (appointments != null) {
                        for (Appointments appointment1 : appointments) {
                            // If an appointment falls within the next 15 minutes, add it to the list.
                            if (appointment1.getAppointmentStart().isAfter(localDateTime) && appointment1.getAppointmentStart().isBefore(fifteenMinuteAdd)) {
                                appointmentIn15.add(appointment1);
                                // Show an alert for the upcoming appointment.
                                showAlert(
                                        resourceBundle.getString("upcoming_appointment_title"),
                                        resourceBundle.getString("upcoming_appointment_message"),
                                        appointment1.getAppointmentStart() + ":" +
                                                resourceBundle.getString("upcoming_appointment_message") + " #" + appointment1.getAppointmentId() +
                                                resourceBundle.getString("upcoming_appointment_message")
                                );
                            }
                        }
                    }

                    // If there are no upcoming appointments within 15 minutes, show an alert.
                    if (appointmentIn15.isEmpty()) {
                        showAlert(
                                resourceBundle.getString("upcoming_appointment_no_appointments_title"),
                                resourceBundle.getString("upcoming_appointment_no_appointments_message"),
                                ""
                        );
                    }

                    // Redirect to the main menu.
                    mainMenu(event);

                    // If user authentication fails (userId < 0):
                } else if (userId < 0) {
                    // Get the current time in the system's default time zone and convert it to UTC.
                    ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Etc/UTC"));

                    // Check if the username is blank or empty and log accordingly.
                    if (username.isBlank() || username.isEmpty()) {
                        printWriter.println(zonedDateTime + ": No username. Login attempt failed");
                    } else {
                        // Log the unsuccessful login attempt including the username and login time.
                        zonedDateTime = LocalDateTime.now()
                                .atZone(ZoneId.systemDefault())
                                .withZoneSameInstant(ZoneId.of("Etc/UTC"));

                        printWriter.println("Unsuccessful login attempt: User " + username + " Time " + zonedDateTime);
                    }

                    // Show an error alert for invalid credentials.
                    showErrorAlert(
                            resourceBundle.getString("Error"),
                            resourceBundle.getString("LoginError"),
                            resourceBundle.getString("Credentials")
                    );

                    // Clear the username and password text fields.
                    loginUsernameTextField.clear();
                    loginPasswordTextField.clear();
                }
            }
        } catch (IOException e) {
            // Handle any IOException that may occur.
            e.printStackTrace();
        }
    }

    /** Sets the text of the loginTimeZoneLabel to display the system's default time zone.
     This method retrieves the default time zone and updates the label's text accordingly.
     */
    private void setLoginTimezone() {
        // Get the user's selected locale.
        Locale userLocale = Locale.getDefault();

        // Load the appropriate resource bundle based on the user's locale.
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language/lang", userLocale);

        // Get the system's default time zone identifier.
        ZoneId systemTimeZone = ZoneId.systemDefault();

        // Get the Zone ID in the user's locale.
        String localizedTimeZoneId = systemTimeZone.getId();

        String timezoneLabel;

        if (userLocale.getLanguage().equals("fr")) {
            // Use the French resource bundle for the time zone label if the locale is French.
            timezoneLabel = resourceBundle.getString("TimeZone");

            // Check if the default time zone identifier starts with "America" and replace it with "Amerique" in French.
            if (localizedTimeZoneId.startsWith("America/")) {
                String frenchTimeZoneId = localizedTimeZoneId.replace("America/", "Amerique/");
                loginTimeZoneLabel.setText(timezoneLabel + ": " + frenchTimeZoneId);
            } else {
                // Get the French display name of the time zone.
                String frenchTimeZoneName = systemTimeZone.getDisplayName(TextStyle.FULL, Locale.FRENCH);
                loginTimeZoneLabel.setText(timezoneLabel + ": " + frenchTimeZoneName);
            }
        } else {
            // Use the default resource bundle for the time zone label if the locale is not French.
            timezoneLabel = resourceBundle.getString("TimeZone");
            loginTimeZoneLabel.setText(timezoneLabel + ": " + localizedTimeZoneId);
        }
    }



    /** Sets the labels and buttons in the login view to their localized versions based on the user's locale.
     This method reads resource bundle properties for labels and buttons and updates their text values.
     It checks if the current locale is supported (French or English) before making changes.
     */
    private void setLocalizedLabels() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("language/lang", Locale.getDefault());

        if (isSupportedLocale()) {
            loginLabel.setText(resourceBundle.getString("Login"));
            usernameLabel.setText(resourceBundle.getString("Username"));
            passwordLabel.setText(resourceBundle.getString("Password"));
            loginExitButton.setText(resourceBundle.getString("Exit"));
            loginButton.setText(resourceBundle.getString("Enter"));
            warningText.setText(resourceBundle.getString("warningText"));
        }
    }

    /** Checks if the current locale is supported for language localization.
     @return True if the current language is supported (French or English); otherwise, false.
     */
    private boolean isSupportedLocale() {
        String currentLanguage = Locale.getDefault().getLanguage();
        return currentLanguage.equals("fr") || currentLanguage.equals("en");
    }
    /** Initializes the login view with the appropriate labels, buttons, and time zone information.
     @param url The location used to resolve relative paths for the root object or null if the location is not known.
     @param resourceBundle The resources to be used for localization or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize UI components and set localized labels.
   setLocalizedLabels();
        setLoginTimezone();
    }
}
