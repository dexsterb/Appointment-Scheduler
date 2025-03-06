package model;

import java.time.LocalDateTime;

/** Appointments constructor class represents an appointment in the scheduling system.
 It contains information such as the appointment ID, title, description, location, type,
 start and end times, customer ID, user ID, contact ID, and other details.
 This class also includes methods to get and set these attributes.
 */
public class Appointments {

    private int appointmentId;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private int customerId;
    private int userId;
    private int contactId;
    private int total;
    private String appointmentMonth;
    private int appointmentTotal;

    /** Constructs an Appointments object with detailed information about the appointment.
     @param appointmentId         The unique identifier for the appointment.
     @param appointmentTitle      The title or name of the appointment.
     @param appointmentDescription A description of the appointment.
     @param appointmentLocation    The location where the appointment will take place.
     @param appointmentType       The type or category of the appointment.
     @param appointmentStart      The date and time when the appointment starts.
     @param appointmentEnd        The date and time when the appointment ends.
     @param customerId            The unique identifier of the customer associated with the appointment.
     @param userId                The unique identifier of the user who scheduled the appointment.
     @param contactId             The unique identifier of the contact person related to the appointment.
     */
    public Appointments(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** Constructs an Appointments object for reporting purposes.
     @param appointmentMonth The month in which the appointment occurs.
     @param appointmentType  The type or category of the appointment.
     @param appointmentTotal The total number of appointments for the specified month and type.
     */
    public Appointments(String appointmentMonth, String appointmentType, int appointmentTotal) {
        this.appointmentMonth = appointmentMonth;
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }

    /** gets appointment's unique identifier
     @return The appointment's unique identifier.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Sets the unique identifier of the appointment.
     @param appointmentId The appointment's unique identifier to set.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the title or name of the appointment.
     *
     * @return  The title or name of the appointment.
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }
    /**
     * Sets the title or name of the appointment.
     *
     * @param appointmentTitle The title or name of the appointment to set.
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * Gets the description of the appointment.
     *
     * @return  The description of the appointment.
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }
    /**
     * Sets the description of the appointment.
     *
     * @param appointmentDescription The description of the appointment to set.
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }
    /**
     * Gets the location of the appointment.
     *
     * @return The location of the appointment.
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    /**
     * Sets the location of the appointment.
     *
     * @param appointmentLocation The location of the appointment to set.
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
    /**
     * Gets the type of the appointment.
     *
     * @return The type of the appointment.
     */
    public String getAppointmentType() {
        return appointmentType;
    }
    /**
     * Sets the type of the appointment.
     *
     * @param appointmentType The type of the appointment to set.
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    /**
     * Gets the start date and time of the appointment.
     *
     * @return The start date and time of the appointment.
     */
    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }
    /**
     * Sets the start date and time of the appointment.
     *
     * @param appointmentStart The start date and time of the appointment to set.
     */
    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }
    /**
     * Gets the end date and time of the appointment.
     *
     * @return The end date and time of the appointment.
     */
    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }
    /**
     * Sets the end date and time of the appointment.
     *
     * @param appointmentEnd The end date and time of the appointment to set.
     */
    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }
    /**
     * Gets the unique identifier of the customer associated with the appointment.
     *
     * @return The customer's unique identifier.
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * Sets the unique identifier of the customer associated with the appointment.
     *
     * @param customerId The customer's unique identifier to set.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * Gets the unique identifier of the user who scheduled the appointment.
     *
     * @return The user's unique identifier.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Sets the unique identifier of the user who scheduled the appointment.
     *
     * @param userId The user's unique identifier to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * Gets the unique identifier of the contact person related to the appointment.
     *
     * @return The contact's unique identifier.
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * Sets the unique identifier of the contact person related to the appointment.
     *
     * @param contactId The contact's unique identifier to set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /**
     * Returns a string representation of the appointment type.
     *
     * @return A string representing the appointment type.
     */
    public String toString() {
        return (appointmentType);
    }
    /**
     * Gets the month in which the appointment occurs.
     *
     * @return The month of the appointment.
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }
    /**
     * Sets the month in which the appointment occurs.
     *
     * @param appointmentMonth The month of the appointment to set.
     */
    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    /**
     * Gets the total number of appointments for a specific month and type.
     *
     * @return The total number of appointments.
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }
    /**
     * Sets the total number of appointments for a specific month and type.
     *
     * @param appointmentTotal The total number of appointments to set.
     */
    public void setAppointmentTotal(int appointmentTotal) {
        this.appointmentTotal = appointmentTotal;
    }
    /**
     * Gets the total count.
     *
     * @return The total count.
     */
    public int getTotal() {
        return total;
    }
    /**
     * Sets the total count.
     *
     * @param total The total count to set.
     */
    public void setTotal(int total) {
        this.total = total;
    }

}