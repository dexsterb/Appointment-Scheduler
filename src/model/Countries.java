package model;

/**
 * The `Countries` class represents a country in the scheduling system.
 * It contains information such as the country's unique identifier and name.
 * This class provides methods to get and set these attributes.
 */
public class Countries {

    private int countryId;
    private String country;
    private int total;

    /**
     * Constructs a `Countries` object with detailed information about the country.
     *
     * @param countryId The unique identifier for the country.
     * @param country   The name of the country.
     */
    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Constructs a `Countries` object for reporting purposes.
     *
     * @param country The name of the country.
     * @param total   The total number of occurrences of the country.
     */
    public Countries(String country, int total) {
        this.country = country;
        this.total = total;
    }

    /**
     * Gets the total number of occurrences of the country.
     *
     * @return The total number of occurrences.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the total number of occurrences of the country.
     *
     * @param total The total number of occurrences to set.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Gets the unique identifier of the country.
     *
     * @return The country's unique identifier.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the unique identifier of the country.
     *
     * @param countryId The country's unique identifier to set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the name of the country.
     *
     * @return The name of the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     *
     * @param country The name of the country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns a string representation of the country's name.
     *
     * @return A string representing the country's name.
     */
    @Override
    public String toString() {
        return country;
    }
}
