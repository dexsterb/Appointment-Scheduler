package model;
/**
 * The `FirstLevelDivisions` class represents first-level divisions in the scheduling system.
 * It contains information such as the division's unique identifier, name, and the associated country ID.
 * This class provides methods to get and set these attributes.
 */
public class FirstLevelDivisions {
    private int divisionId;
    private String division;
    private int countryId;

    /**
     * Constructs a `FirstLevelDivisions` object with detailed information about the division.
     *
     * @param divisionId The unique identifier for the division.
     * @param division   The name of the division.
     * @param countryId  The unique identifier of the country associated with the division.
     */
    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * The `DivisionTotalCustomers` class represents the total number of customers in a division.
     * It contains information such as the division's name and the total number of customers in that division.
     * This class provides methods to get and set these attributes.
     */
    public static class DivisionTotalCustomers {
        private String divisionName;
        private int totalCustomers;

        /**
         * Constructs a `DivisionTotalCustomers` object with information about the division's name and total customers.
         *
         * @param divisionName    The name of the division.
         * @param totalCustomers  The total number of customers in the division.
         */
        public DivisionTotalCustomers(String divisionName, int totalCustomers) {
            this.divisionName = divisionName;
            this.totalCustomers = totalCustomers;
        }

        /**
         * Gets the name of the division.
         *
         * @return The name of the division.
         */
        public String getDivisionName() {
            return divisionName;
        }

        /**
         * Sets the name of the division.
         *
         * @param divisionName The name of the division to set.
         */
        public void setDivisionName(String divisionName) {
            this.divisionName = divisionName;
        }

        /**
         * Gets the total number of customers in the division.
         *
         * @return The total number of customers in the division.
         */
        public int getTotalCustomers() {
            return totalCustomers;
        }

        /**
         * Sets the total number of customers in the division.
         *
         * @param totalCustomers The total number of customers in the division to set.
         */
        public void setTotalCustomers(int totalCustomers) {
            this.totalCustomers = totalCustomers;
        }
    }

    /**
     * Gets the unique identifier of the division.
     *
     * @return The division's unique identifier.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the unique identifier of the division.
     *
     * @param divisionId The division's unique identifier to set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the name of the division.
     *
     * @return The name of the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the division.
     *
     * @param division The name of the division to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the unique identifier of the country associated with the division.
     *
     * @return The country's unique identifier.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the unique identifier of the country associated with the division.
     *
     * @param countryId The country's unique identifier to set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Returns a string representation of the division's name.
     *
     * @return A string representing the division's name.
     */
    @Override
    public String toString() {
        return division;
    }
}
