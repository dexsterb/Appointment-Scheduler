package model;
/**
 * The `Contacts` class represents a contact in the scheduling system.
 * It contains information such as the contact's unique identifier, name, and email address.
 * This class provides methods to get and set these attributes.
 */
public class Contacts {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Constructs a `Contacts` object with detailed information about the contact.
     *
     * @param contactId    The unique identifier for the contact.
     * @param contactName  The name of the contact.
     * @param contactEmail The email address of the contact.
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the unique identifier of the contact.
     *
     * @return The contact's unique identifier.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the unique identifier of the contact.
     *
     * @param contactId The contact's unique identifier to set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the name of the contact.
     *
     * @param contactName The name of the contact to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the email address of the contact.
     *
     * @return The email address of the contact.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the email address of the contact.
     *
     * @param contactEmail The email address of the contact to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Returns a string representation of the contact's name.
     *
     * @return A string representing the contact's name.
     */
    @Override
    public String toString() {
        return contactName;
    }
}
