package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import DAO.FirstLevelDivisionsDao;
import DAO.CountriesDao;
import DAO.CustomersDao;
import model.FirstLevelDivisions;
import model.Countries;

/** Controller for adding a new customer.
 This controller manages the addition of a new customer to the database.
 It handles the user interface elements for selecting the country, state, and entering customer details.
 */
public class AddCustomerController implements Initializable {

    // FXML elements
    @FXML private ComboBox<Countries> addCustomerCountryComboBox;
    @FXML private ComboBox<FirstLevelDivisions> addCustomerStateComboBox;
    @FXML private TextField addCustomerAddressTextField;
    @FXML private TextField addCustomerNameTextField;
    @FXML private TextField addCustomerPhoneNumberTextField;
    @FXML private TextField addCustomerPostalCodeTextField;
    @FXML Stage stage;
    @FXML Parent scene;


    /** Helper method to navigate back to the customer menu view.
     @param event The ActionEvent that triggered the navigation.
     @throws IOException If there is an error loading the customer menu view.
     */
    @FXML
    private void customerMenuButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles the cancel button click event.
     @param event The ActionEvent triggered by the cancel button click.
     @throws IOException If there is an error navigating back to the customer menu.
     */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        // Navigate back to the customer menu
        customerMenuButton(event);
    }

    /** Handles the save button click event.
     @param event The ActionEvent triggered by the save button click.
     @throws SQLException If there is an error adding the customer to the database.
     @throws IOException  If there is an error navigating back to the customer menu.
     */
    @FXML
    void save(ActionEvent event) throws SQLException, IOException {
        String name = addCustomerNameTextField.getText();
        String address = addCustomerAddressTextField.getText();
        Countries country = addCustomerCountryComboBox.getValue();
        FirstLevelDivisions division = addCustomerStateComboBox.getValue();
        String postalCode = addCustomerPostalCodeTextField.getText();
        String phone = addCustomerPhoneNumberTextField.getText();

        // Check if any of the input fields are empty or if country or division is not selected
        if (invalidInput(name, address, postalCode, phone, country, division)) {
            showAlert("Invalid Input", "Warning", "Fill in all fields.");
        } else {
            int divisionId = division.getDivisionId();
            // Add the customer to the database
            CustomersDao.addCustomer(name, address, postalCode, phone, divisionId);

            // Navigate back to the customer menu
            customerMenuButton(event);
        }
    }

    /** Helper method to check for invalid input data.
     @param name The customer's name.
     @param address The customer's address.
     @param postalCode The customer's postal code.
     @param phone The customer's phone number.
     @param country The selected country.
     @param division The selected state or division.
     @return True if any of the input fields are empty or if the country or division is not selected; otherwise, false.
     */
    private boolean invalidInput(String name, String address, String postalCode, String phone, Countries country, FirstLevelDivisions division) {
        return name.isBlank() || address.isBlank() || postalCode.isBlank() || phone.isBlank() || country == null || division == null;
    }

    /** Helper method to show an alert dialog.
     @param title The title of the alert dialog.
     @param headerText The header text of the alert dialog.
     @param contentText The content text of the alert dialog.
     */
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /** Handles the selection of a country in the ComboBox.
     @param event The ActionEvent triggered by the selection.
     @throws SQLException If there is an error fetching divisions from the database.
     */
    @FXML
    void addCustomerCountry(ActionEvent event) throws SQLException {
        Countries country = addCustomerCountryComboBox.getValue();
        // Load the divisions for the selected country into the state combo box
        addCustomerStateComboBox.setItems(FirstLevelDivisionsDao.getDivisionsByCountry(country.getCountryId()));
        addCustomerStateComboBox.setValue(null);
    }

    /** Handles the selection of a state (currently empty).
     @param event The ActionEvent triggered by the state selection.
     */
    @FXML
    void addCustomerState(ActionEvent event) {
    }

    /** Initialize the controller.
     @param url The URL used to initialize the controller (not used in this implementation).
     @param resourceBundle The ResourceBundle used to initialize the controller (not used in this implementation).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load the list of countries into the country combo box
        addCustomerCountryComboBox.setItems(CountriesDao.getCountries());
        addCustomerStateComboBox.setVisibleRowCount(10);
    }
}
