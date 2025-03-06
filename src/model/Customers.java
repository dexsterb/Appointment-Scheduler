package model;

/**
 * The `Customers` class represents a customer in the scheduling system.
 * It contains information such as the customer's unique identifier, name, address,
 * postal code, phone number, division, and country.
 * This class provides methods to get and set these attributes.
 */
public class Customers {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionId;
    private String division;
    private String country;

    /**
     * Constructs a `Customers` object with detailed information about the customer.
     *
     * @param customerId          The unique identifier for the customer.
     * @param customerName        The name of the customer.
     * @param customerAddress     The address of the customer.
     * @param customerPostalCode  The postal code of the customer's location.
     * @param customerPhoneNumber The phone number of the customer.
     * @param divisionId          The unique identifier of the division associated with the customer.
     * @param division            The name of the division associated with the customer.
     * @param country             The name of the country associated with the customer.
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, int divisionId, String division, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionId = divisionId;
        this.division = division;
        this.country = country;
    }

    /**
     * Constructs a `Customers` object with basic information about the customer.
     *
     * @param customerId          The unique identifier for the customer.
     * @param customerName        The name of the customer.
     * @param customerAddress     The address of the customer.
     * @param customerPostalCode  The postal code of the customer's location.
     * @param customerPhone       The phone number of the customer.
     * @param divisionId          The unique identifier of the division associated with the customer.
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhone;
        this.divisionId = divisionId;
    }

    /**
     * Gets the name of the country associated with the customer.
     *
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country associated with the customer.
     *
     * @param country The name of the country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the unique identifier of the customer.
     *
     * @return The customer's unique identifier.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the unique identifier of the customer.
     *
     * @param customerId The customer's unique identifier to set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName The name of the customer to set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the address of the customer.
     *
     * @return The address of the customer.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets the address of the customer.
     *
     * @param customerAddress The address of the customer to set.
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Gets the postal code of the customer's location.
     *
     * @return The postal code.
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Sets the postal code of the customer's location.
     *
     * @param customerPostalCode The postal code to set.
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param customerPhoneNumber The phone number to set.
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * Gets the unique identifier of the division associated with the customer.
     *
     * @return The division's unique identifier.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the unique identifier of the division associated with the customer.
     *
     * @param divisionId The division's unique identifier to set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the name of the division associated with the customer.
     *
     * @return The name of the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the division associated with the customer.
     *
     * @param division The name of the division to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns a string representation of the customer's unique identifier.
     *
     * @return A string representing the customer's unique identifier.
     */
    @Override
    public String toString() {
        return String.valueOf((customerId));
    }
}
