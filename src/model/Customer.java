package model;

import Utilities.ConvertTime;
import java.time.LocalDateTime;

/** This is the Customer class.
 * This class acts as the blueprint for Customer objects in the database.
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDateTime;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    /** The constructor for the Customer Class.
     * @param customerID The customer's ID.
     * @param customerName The customer's name.
     * @param address The customer's address.
     * @param postalCode The customer's postal code.
     * @param phone The customer's phone number.
     * @param createDateTime The date and time that the customer data was created.
     * @param createdBy The person who created the customer data in the database.
     * @param lastUpdate The time that the customer data was last updated.
     * @param lastUpdatedBy The person who last updated the customer data.
     * @param divisionID The customer's first level division ID.
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, LocalDateTime createDateTime, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDateTime = createDateTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /** Gets the ID of the customer.
     * @return customerID Integer
     */
    public int getCustomerID() {
        return customerID;
    }

    /** This method overrides the Object class's default toString method.
     * If not used, the combo box for Customer ID will populate with the package.class.hashcode.
     * This method fixes this issue and returns the customer's ID and name instead.
     * @return The customer's ID and name as a string to be displayed in the combo box.
     */
    @Override
    public String toString() {
        return customerID +": " + customerName;
    }

    /** Sets the ID of the customer.
     * @param customerID Integer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Gets the customer's name.
     * @return customerName String
     */
    public String getCustomerName() {
        return customerName;
    }

    /** Sets the name of the customer.
     * @param customerName String
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Gets the address of the customer.
     * @return address String
     */
    public String getAddress() {
        return address;
    }

    /** Sets the address of the customer.
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Gets the postalCode of the customer.
     * @return postalCode String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** Sets the customer postalCode.
     * @param postalCode String
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Gets the customer's phone number.
     * @return phone String
     */
    public String getPhone() {
        return phone;
    }

    /** Sets the customer's phone number.
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Gets the creation date/time of the customer in the system.
     * @return LocalDateTime createDateTime
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /** Gets the Customer creation date in the format of MMMM,DD YYYY.
     * @return createDate String
     */
    public String getCreateDateFormatted() {
        return ConvertTime.dateFormatted(getCreateDateTime());
    }

    /** Sets the date and time that the Customer was created.
     * @param createDateTime LocalDateTime
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /** Gets the User's name that created the appointment.
     * @return The User who created the Appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Sets the User who created the customer.
     * @param createdBy The User who created the customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Gets the local date and time of when the customer was updated.
     * @return The last time the customer was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** Gets the last time the customer was updated (formatted).
     * @return The last time Customer was updated.
     */
    public String getLastUpdateFormatted() {
        return ConvertTime.dateFormatted(getLastUpdate());
    }

    /** Sets the local date/time of customer's last update.
     * @param lastUpdate LocalDateTime
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Gets User who last updated the customer's data.
     * @return The user who last updated the customer data.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Sets the customer's lastUpdatedBy user's information.
     * @param lastUpdatedBy The user who last updated the Customer.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /** Gets the customer's first-level division ID.
     * @return The customer's first-level division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** Sets the customer's first-level division ID.
     * @param divisionID The customer's first-level division ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
