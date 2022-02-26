package DAO;

import javafx.collections.ObservableList;
import model.Customer;
import java.sql.SQLException;

/** This is the CustomerDAO interface.
 * This interface is used to update and retrieve Customer objects from the database for use in the data tables. */
public interface CustomerDAO {

    /** Gets a customer from the db based on their ID.
     * @param customerID The ID of the customer.
     * @return The customer whose ID is a match in the db.
     * @throws SQLException
     */
    public Customer getCustomerByID(int customerID) throws SQLException;

    /** Gets the list of all customers stored in the database.
     * @return The list of all customers stored in the database.
     * @throws SQLException
     */
    public ObservableList<Customer> getAllDBCustomers() throws SQLException;

    /** This method is used to add customers to the database once their information has been gathered in the Add Customer Menu form.
     * @param customerName The customer's name.
     * @param address The customer's address.
     * @param postalCode The customer's postal code.
     * @param phone The customer's phone number.
     * @param createdBy The user who created the customer in the database.
     * @param lastUpdatedBy The user who last updated the customer data in the database.
     * @param divisionID The customer's division ID.
     * @throws SQLException Thrown if there is a database access error.
     */
    public void addNewCustomer(String customerName, String address, String postalCode, String phone, String createdBy, String lastUpdatedBy, int divisionID) throws SQLException;

    /** This method modifies a selected customer's information.
     * @param customerID The customer's unique ID.
     * @param customerName The customer's name.
     * @param address The customer's address.
     * @param postalCode The customer's postal code.
     * @param phone The customer's phone number.
     * @param lastUpdatedBy The user who last updated the customer data in the database.
     * @param divisionID The customer's division ID.
     * @throws SQLException Thrown if there is a database access error.
     */
    public void modifyCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID, String lastUpdatedBy) throws SQLException;

    /** This method deletes customers from the database along with their existing appointments.
     * @param customerID The customer's unique ID.
     * @throws SQLException Thrown if there is a database access error.
     */
    public void deleteCustomer(int customerID) throws SQLException;

}
