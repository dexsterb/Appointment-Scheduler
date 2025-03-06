package controller;

import DAO.CountriesDao;
import DAO.CustomersDao;
import DAO.FirstLevelDivisionsDao;
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
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** The ModifyCustomerController class controls the modification of customer information.
 It allows users to update customer details such as name, address, country, state, postal code, and phone number.
 */
public class ModifyCustomerController implements Initializable {

    @FXML private TextField updateCustomerAddressTextField;

    @FXML private ComboBox<Countries> updateCustomerCountryComboBox;

    @FXML private TextField updateCustomerIdTextField;

    @FXML private TextField updateCustomerNameTextField;

    @FXML private TextField updateCustomerPhoneNumberTextField;

    @FXML private TextField updateCustomerPostalCodeTextField;

    @FXML private ComboBox<FirstLevelDivisions> updateCustomerStateComboBox;
    @FXML Stage stage;
    @FXML Parent scene;

    /** Saves the modified customer information.
     @param event The action event triggered by the save button.
     */
    @FXML
    void save(ActionEvent event) {
        try {
            int id = Integer.parseInt(updateCustomerIdTextField.getText());
            String name = updateCustomerNameTextField.getText();
            String address = updateCustomerAddressTextField.getText();
            Countries country = updateCustomerCountryComboBox.getValue();
            FirstLevelDivisions division = updateCustomerStateComboBox.getValue();
            String postalCode = updateCustomerPostalCodeTextField.getText();
            String phone = updateCustomerPhoneNumberTextField.getText();

            if (isInputValid(name, address, country, division, postalCode, phone)) {
                int divisionId = division.getDivisionId();
                CustomersDao.modifyCustomer(id, name, address, postalCode, phone, divisionId);

                customerScreen(event);
            } else {
                showAlert("Input Error", "All fields must be filled out");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Checks if the input data is valid.
     @param name The customer's name.
     @param address The customer's address.
     @param country The selected country.
     @param division The selected state or division.
     @param postalCode The customer's postal code.
     @param phone The customer's phone number.
     @return True if the input is valid; otherwise, false.
     */
    boolean isInputValid(String name, String address, Countries country, FirstLevelDivisions division, String postalCode, String phone) {
        return !name.isBlank() && !address.isBlank() && country != null && division != null && !postalCode.isBlank() && !phone.isBlank();
    }

    /** Displays an alert with the given title and content.
     @param title   The title of the alert.
     @param content The content or message to display.
     */
    void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /** Navigates to the customer screen.
     @param event The action event triggered by the save button.
     @throws IOException If there is an error navigating to the customer screen.
     */
    void customerScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Cancels the customer modification and navigates back to the customer screen.
     @param event The action event triggered by the cancel button.
     @throws IOException If there is an error navigating to the customer screen.
     */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Updates the available divisions based on the selected country.
     @param event The action event triggered by selecting a country in the combo box.
     */
    @FXML
    void updateCustomerCountry(ActionEvent event) {
        Countries country = updateCustomerCountryComboBox.getValue();
        updateCustomerStateComboBox.setItems(FirstLevelDivisionsDao.getDivisionsByCountry(country.getCountryId()));
        updateCustomerStateComboBox.setValue(null);
    }

    /** Performs actions when the customer state is updated (e.g., selecting a state or division).
     @param event The action event triggered by updating the customer state.
     */
    @FXML
    void updateCustomerState(ActionEvent event) {

    }

    /** Sends customer data to populate the modification form.
     @param customer The customer data to be displayed and modified.
     @throws SQLException If there is an error retrieving customer data.
     */
    public void sendCustomer(Customers customer) throws SQLException {
        updateCustomerIdTextField.setText(String.valueOf(customer.getCustomerId()));
        updateCustomerNameTextField.setText(customer.getCustomerName());
        updateCustomerAddressTextField.setText(customer.getCustomerAddress());
        updateCustomerPostalCodeTextField.setText(customer.getCustomerPostalCode());
        updateCustomerPhoneNumberTextField.setText(customer.getCustomerPhoneNumber());
        Countries country = CountriesDao.getCountryByDivisionId(customer.getDivisionId());
        updateCustomerCountryComboBox.setValue(country);
        updateCustomerStateComboBox.setItems(FirstLevelDivisionsDao.getDivisionsByCountry(country.getCountryId()));
        updateCustomerStateComboBox.setValue(FirstLevelDivisionsDao.getDivisionsById(customer.getDivisionId()));
    }

    /** Initializes the controller, setting up the initial state and populating combo boxes.
     The populated combo boxes are country and state. The visible items will be 10 on the dropdown.
     @param url            The URL used to initialize the controller.
     @param resourceBundle The resource bundle used for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCustomerCountryComboBox.setItems(CountriesDao.getCountries());
        updateCustomerStateComboBox.setVisibleRowCount(10);
    }
}