package model;

/**
 * The `Users` class represents user accounts in the scheduling system.
 * It contains information such as the user's unique identifier, username, and password.
 * This class provides methods to get and set these attributes.
 */
public class Users {

    private int userId;
    private String userName;
    private String userPassword;

    /**
     * Constructs a `Users` object with detailed information about the user.
     *
     * @param userId       The unique identifier for the user.
     * @param userName     The username associated with the user.
     * @param userPassword The password associated with the user's account.
     */
    public Users(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's unique identifier.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param userId The user's unique identifier to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username associated with the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username associated with the user.
     *
     * @param userName The username to set for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password associated with the user's account.
     *
     * @return The password of the user's account.
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the password associated with the user's account.
     *
     * @param userPassword The password to set for the user's account.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Returns a string representation of the user's unique identifier.
     *
     * @return A string representing the user's unique identifier.
     */
    @Override
    public String toString() {
        return String.valueOf(userId);
    }
}
