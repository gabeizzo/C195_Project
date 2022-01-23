package model;

import Utilities.ConvertTime;
import java.time.LocalDateTime;

/** The Customer class*/
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

    /**
     * The constructor for the Customer Class
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param createDateTime
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
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

    /**
     * getCustomerID. Returns the ID of the customer.
     * @return customerID Integer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * setCustomerID. Sets the ID of the customer.
     * @param customerID Integer.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * getCustomerName. Returns the customer's name.
     * @return customerName String.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * setCustomerName. Sets the name of the customer
     * @param customerName String
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * getAddress. Returns the address of the customer.
     * @return address String.
     */
    public String getAddress() {
        return address;
    }

    /**
     * setAddress. Sets the address of the customer.
     * @param address String.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getPostalCode. Returns the postalCode of the customer.
     * @return postalCode String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * setPostalCode. Sets the customer postalCode.
     * @param postalCode String
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the customer's phone number.
     * @return phone String.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number.
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the creation date/time of the customer in the system.
     * @return LocalDateTime createDateTime.
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    //FIX ME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * getFormattedCreateDate. Returns a formatted string representation of the creation date.
     * @return createDate String
     */
    /*public String getFormattedCreateDate() {
        return ConvertTime.formatDate(getCreateDateTime());
    }*/

    /**
     * Sets the date and time that the Customer was created.
     * @param createDateTime LocalDateTime
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     * Gets the User's name that created the appointment.
     * @return The User who created the Appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the User who created the customer.
     * @param createdBy The User who created the customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the local date and time of when the customer was updated.
     * @return The last time the customer was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    //FIX ME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * Gets the last time the customer was updated (formatted).
     * @return The last time Customer was updated.
     */
    /*public String getFormattedLastUpdate() {
        return ConvertTime.formatDate(getLastUpdate());
    }*/

    /**
     * Sets the local date/time of customer's last update.
     * @param lastUpdate LocalDateTime.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets User who last updated the customer's data.
     * @return The user who last updated the customer data.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the customer's lastUpdatedBy user's information.
     * @param lastUpdatedBy The user who last updated the Customer.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the customer's first-level division ID.
     * @return The customer's first-level division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets the customer's first-level division ID.
     * @param divisionID The customer's first-level division ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
