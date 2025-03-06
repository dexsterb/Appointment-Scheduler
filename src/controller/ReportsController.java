package controller;

import DAO.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.FirstLevelDivisions;
import utils.JDBC;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** Controller class for managing reports. Contains three Lambda expressions.
 */
public class ReportsController implements Initializable {

    @FXML private TableColumn<Appointments, String> reportAppointmentMonthColumn;

    @FXML private TableColumn<Appointments, String> reportAppointmentTypeColumn;

    @FXML private TableView<Appointments> reportAppointmentTypeView;

    @FXML private TableColumn<Appointments, Integer> reportAppointmentTotalColumn;

    @FXML private ComboBox<Appointments> reportAppointmentComboBox;

    @FXML private ComboBox<Contacts> reportContactComboBox;

    @FXML private ComboBox<Customers> reportCustomerComboBox;

    @FXML private TableColumn<Appointments, String> reportDivisionColumn;

    @FXML private TableView<FirstLevelDivisions.DivisionTotalCustomers> reportFirstlevelDivisionTableView;

    @FXML private TableColumn<Appointments, Integer> reportCustomerTotalColumn;

    @FXML private TableColumn<Appointments, Integer> reportCustomerIdColumn;

    @FXML private TableColumn<Appointments, String> reportDescriptionColumn;

    @FXML private TableColumn<Appointments, LocalDateTime> reportEndColumn;

    @FXML private TableColumn<Appointments, Integer> reportIdColumn;

    @FXML private TableColumn<Appointments, LocalDateTime> reportStartColumn;

    @FXML private TableView<Appointments> reportAppointmentsTableView;

    @FXML private TableColumn<Appointments, String> reportTitleColumn;

    @FXML private TableColumn<Appointments, String> reportTypeColumn;

    @FXML Stage stage;

    @FXML Parent scene;

    /** Switches to the main menu.
     @param event The ActionEvent triggered by the button.
     @throws IOException If an error occurs while loading the MainMenu.fxml.
     */
    @FXML
    void mainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Resets the appointments table to display all appointments.
     This method also clears the combo boxes.
     @param event The ActionEvent triggered by the button.
     */
    @FXML
    void resetButton(ActionEvent event) {
        reportAppointmentsTableView.setItems(AppointmentsDao.getAppointments());
        // Clear ComboBoxes for accurate results
        reportContactComboBox.setValue(null);
        reportCustomerComboBox.setValue(null);
        reportAppointmentComboBox.setValue(null);

    }

    /** Exits the application and closes the database connection.
     @param event The ActionEvent triggered by the button.
     */
    @FXML
    void exit(ActionEvent event) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /** Lambda Expression for filtering appointments based on ComboBox selection.
     Filters appointments by selected appointment.
     @param event The ActionEvent triggered by the ComboBox.
     */
    @FXML
    void reportAppointmentFilter(ActionEvent event) {
        Appointments appointments = reportAppointmentComboBox.getValue();
        if (appointments == null) {
            return;
        }
        ObservableList<Appointments> appointmentList = AppointmentsDao.getAppointments();
        ObservableList<Appointments> appointments1 = appointmentList.filtered(a -> {
            if (a.getAppointmentId() == appointments.getAppointmentId()) {
                return true;
            }
            return false;
        });
        reportAppointmentsTableView.setItems(appointments1);
    }

    /** Lambda Expression for filtering appointments based on ComboBox selection.
     Filters appointments by selected contact.
     @param event The ActionEvent triggered by the ComboBox.
     */
    @FXML
    void reportContactFilter(ActionEvent event) {
        Contacts contacts = reportContactComboBox.getValue();
        if (contacts == null) {
            return;
        }
        ObservableList<Appointments> appointmentList = AppointmentsDao.getAppointments();
        ObservableList<Appointments> contactList = appointmentList.filtered(b -> {
            if (b.getContactId() == contacts.getContactId()) {
                return true;
            }
            return false;
        });
        reportAppointmentsTableView.setItems(contactList);
    }

    /** Lambda Expression for filtering appointments based on ComboBox selection.
     Filters appointments by selected customer.
     @param event The ActionEvent triggered by the ComboBox.
     */
    @FXML
    void reportCustomerFilter(ActionEvent event) {
        Customers customers = reportCustomerComboBox.getValue();
        if (customers == null) {
            return;
        }
        ObservableList<Appointments> appointmentList = AppointmentsDao.getAppointments();
        ObservableList<Appointments> customerList = appointmentList.filtered(c -> {
            if (c.getCustomerId() == customers.getCustomerId()) {
                return true;
            }
            return false;
        });
        reportAppointmentsTableView.setItems(customerList);
    }

    /** Initializes the ReportsController, populating UI components and setting up event handlers.
     @param url The location used to resolve relative paths for the root object.
     @param resourceBundle The resources used to localize the root object, or null if none.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the ComboBox with contact data
        reportAppointmentComboBox.setItems(AppointmentsDao.getAppointments());
        reportContactComboBox.setItems(ContactsDao.getContacts());
        reportCustomerComboBox.setItems(CustomersDao.getCustomers());


        // Initialize the Appointments TableView and columns
        reportAppointmentsTableView.setItems(AppointmentsDao.getAppointments());
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        reportTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        reportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        reportDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        reportStartColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        reportEndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        reportCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        // Initialize the First Level Divisions TableView and columns
        ObservableList<FirstLevelDivisions.DivisionTotalCustomers> divisionsWithTotalCustomers = FirstLevelDivisionsDao.getDivisionsWithTotalCustomers();
        reportFirstlevelDivisionTableView.setItems(divisionsWithTotalCustomers);
        reportDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        reportCustomerTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalCustomers"));
        reportFirstlevelDivisionTableView.refresh();



        // Initialize the Appointment Types TableView and columns
        reportAppointmentTypeView.setItems(AppointmentsDao.getAppointmentsByMonthType());
        reportAppointmentMonthColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        reportAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        reportAppointmentTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTotal"));
    }
}
