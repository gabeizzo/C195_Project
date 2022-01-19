package model;

import Utilities.ConvertTime;
import java.time.LocalDateTime;

/** Customer model class. Customer POJO representation of the Customer table in the database */
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
     * Constructor for Customer Class
     * @param customerID Integer
     * @param customerName String
     * @param address String
     * @param postalCode String
     * @param phone String
     * @param createDateTime LocalDateTime
     * @param createdBy String
     * @param lastUpdate LocalDateTime
     * @param lastUpdatedBy String
     * @param divisionID Integer
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
     * getPhone. Returns the phone number of the customer.
     * @return phone String.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setPhone. Returns the phone number of the customer.
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getCreateDateTime. Returns the creation date and time of the customer.
     * @return LocalDateTime createDateTime.
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

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
     * Gets the User that created the Appointment.
     * @return The User who created the Appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the User who created the Customer.
     * @param createdBy The User who created the Customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the local date and time of when the Customer was updated.
     * @return The last time the Customer was updated.
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the last time the Customer was updated (formatted).
     * @return The last time Customer was updated.
     */
    /*public String getFormattedLastUpdate() {
        return ConvertTime.formatDate(getLastUpdate());
    }*/

    /**
     * Sets the local Date and Time of Customer's last update.
     * @param lastUpdate LocalDateTime.
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets User who last updated the Customer's information.
     * @return User who last updated the Customer info.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the Customer's lastUpdatedBy User information.
     * @param lastUpdatedBy The User who last updated the Customer.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets Customer's first-level division ID.
     * @return Customer's first-level division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets Customer's first-level division ID.
     * @param divisionID The first-level division ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
