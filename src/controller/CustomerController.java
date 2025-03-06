package controller;

import DAO.AppointmentsDao;
import DAO.CustomersDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is responsible for managing customer-related functionality in the application.
 It handles the display, creation, modification, and deletion of customer records.
 The class implements the Initializable interface to initialize the controller during JavaFX application startup.
 */
public class CustomerController implements Initializable {

    @FXML private TableColumn<Customers, String> customerAddressColumn;

    @FXML private TableColumn<Customers, String> customerCountryColumn;

    @FXML private TableColumn<Customers, Integer> customerIdColumn;

    @FXML private TableColumn<Customers, String> customerNameColumn;

    @FXML private TableColumn<Customers, String> customerPhoneNumberColumn;

    @FXML private TableColumn<Customers, String> customerPostalCodeColumn;

    @FXML private TableColumn<Customers, Integer> customerDivisionColumn;

    @FXML private TableView<Customers> customerTableView;
    @FXML Stage stage;
    @FXML Parent scene;
    static ObservableList<Customers> customers;

    /** Handles the event when the "Add Customer" button is clicked.
     Takes the user to the add customer form.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If there is an error loading the Add Customer view.
     */
    @FXML
    void customerAdd(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Checks if the selected customer has any associated appointments.
     @param selectedCustomer The customer for whom appointments are being checked.
     @return True if the customer has no appointments, false otherwise.
     */
    private boolean hasAppointmentForCustomer(Customers selectedCustomer) {
        ObservableList<Appointments> appointments = AppointmentsDao.getAppointmentsByCustomerId(selectedCustomer.getCustomerId());
        return appointments != null && appointments.isEmpty();
    }

    /** Handles the event when the "Main Menu" button is clicked.
     Takes the user to the main menu.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If there is an error loading the Customer Menu view.
     */
    @FXML
    void mainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Handles the event when the "Modify Customer" button is clicked.
     Retrieves the selected customer and opens the Modify Customer view for editing.
     @param event The ActionEvent triggered by the button click.
     @throws IOException If there is an error loading the Modify Customer view.
     */
    @FXML
    void customerModifyButton(ActionEvent event) throws IOException {
        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showSelectionErrorAlert();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomer.fxml"));
            Parent root = loader.load();

            ModifyCustomerController modifyController = loader.getController();
            modifyController.sendCustomer(selectedCustomer);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

            // Update the customer table if needed
            customers = CustomersDao.getCustomers();
            customerTableView.setItems(customers);
            customerTableView.refresh();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Displays an error alert when no customer is selected for modification or deletion.
     */
    private void showSelectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Selection error");
        alert.setContentText("Select a customer to update.");
        alert.showAndWait();
    }

    /** Displays an information alert with the specified title and content text.
     @param title The title of the information alert.
     @param contentText The content text to be displayed in the alert.
     */
    private void showInformationAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /** Handles the event when the "Delete Customer" button is clicked.
     Deletes the selected customer while notifying user they will also delete associated appointments.
     @param event The ActionEvent triggered by the button click.
     @throws SQLException If there is an error accessing the database.
     */
    @FXML
    void customerDelete(ActionEvent event) {
        Customers selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection error");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        } else if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete this customer. Continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean valid = hasAppointmentForCustomer(selectedCustomer);
                    if (valid) {
                        int customerId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
                        String customerName = customerTableView.getSelectionModel().getSelectedItem().getCustomerName();
                        CustomersDao.deleteCustomer(customerId);
                        alert = new Alert(Alert.AlertType.INFORMATION, customerName + ", ID #" + customerId + ", successfully deleted.");
                        alert.showAndWait();
                        customers = CustomersDao.getCustomers();
                        customerTableView.setItems(customers);
                        customerTableView.refresh();
                    } else {
                        int customerId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
                        String customerName = customerTableView.getSelectionModel().getSelectedItem().getCustomerName();
                        alert = new Alert(Alert.AlertType.CONFIRMATION, "All appointments associated with " + customerName + " will be deleted as well. Continue?");
                        Optional<ButtonType> result1 = alert.showAndWait();

                        if (result1.isPresent() && result1.get() == ButtonType.OK) {
                            AppointmentsDao.deleteAppointmentsByCustomerId(customerId);
                            CustomersDao.deleteCustomer(customerId);
                            alert = new Alert(Alert.AlertType.INFORMATION, customerName + ", ID #" + customerId + ", successfully deleted.");
                            alert.showAndWait();
                            customers = CustomersDao.getCustomers();
                            customerTableView.setItems(customers);
                            customerTableView.refresh();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Initializes the controller during JavaFX application startup.
     Sets up the customer table view and binds columns to data properties.
     @param url The location used to resolve relative paths.
     @param resourceBundle The resource bundle to localize the UI elements (not used in this case).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTableView.setItems(CustomersDao.getCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
    }
}
