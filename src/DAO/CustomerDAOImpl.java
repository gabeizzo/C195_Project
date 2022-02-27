package DAO;

import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Customer;
import java.sql.*;
import java.time.LocalDateTime;

/** This is the CustomerDAOImpl class which is an implementation of the CustomerDAO interface.
 * This class defines the methods used to manipulate i.e. add/modify/delete/retrieve Customer data from/in the database.
 */
public class CustomerDAOImpl implements CustomerDAO{
    private Connection connection = Main.connection;
    private PreparedStatement pst;
    private ResultSet rs;

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();


    /** This is the constructor for the CustomerDAOImpl class.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public CustomerDAOImpl() throws SQLException {
        //empty class constructor
    }

    /** Creates a new customer object and associates the related table columns with the customer data.
     * @return A new Customer object.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    private Customer newCustomer() throws SQLException {
        int customerID = rs.getInt("Customer_ID");
        String customerName = rs.getString("Customer_Name");
        String customerAddress = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        LocalDateTime createDateTime = rs.getTimestamp("Create_Date").toLocalDateTime();
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int divisionID = rs.getInt("Division_ID");

        return new Customer(customerID, customerName, customerAddress, postalCode, phone,
                createDateTime, createdBy, lastUpdate, lastUpdatedBy, divisionID);
    }

    /** This method gets a customer from the database based on the customer's ID.
     * @param customerID The ID of the customer.
     * @return Customer object matching the customerID in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public Customer getCustomerByID(int customerID) throws SQLException {
        String getCustomerByIDFromDB = "SELECT * from customers where Customer_ID=" + customerID;
        DBQuery.setPreparedStatement(connection, getCustomerByIDFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();
        try {
            while(rs.next()) {
                Customer c = newCustomer();
                return c;
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /** Gets the list of all customers from the database.
     * @return The list of all customers.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Customer> getAllDBCustomers() throws SQLException {
        //Query database to get all customers listed.
        String getAllCustomersFromDB = "SELECT * FROM customers";
        DBQuery.setPreparedStatement(connection, getAllCustomersFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();
        try {
            while(rs.next()) {
                Customer c = newCustomer();
                allCustomers.add(c);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allCustomers;
    }

    /** This method adds a customer to the database by use of an insert statement.
     * @param customerName The customer's name.
     * @param address The customer's address.
     * @param postalCode The customer's postal code.
     * @param phone The customer's phone number.
     * @param createdBy The user who created the customer in the database.
     * @param lastUpdatedBy The user who last updated the customer data in the database.
     * @param divisionID The customer's division ID.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void addNewCustomer(String customerName, String address, String postalCode, String phone, String createdBy, String lastUpdatedBy, int divisionID) throws SQLException{
        String addCustomerToDB = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatement(connection, addCustomerToDB);
        pst = DBQuery.getPreparedStatement();
        pst.setString(1, customerName);
        pst.setString(2, address);
        pst.setString(3, postalCode);
        pst.setString(4, phone);
        pst.setTimestamp(5, new Timestamp(System.currentTimeMillis())); //Adds Create_Date to database
        pst.setString(6,createdBy);
        pst.setTimestamp(7, new Timestamp(System.currentTimeMillis())); //Adds Last_Update to database
        pst.setString(8, lastUpdatedBy);
        pst.setInt(9, divisionID);
        pst.execute();

    }

    /** Modifies a selected customer in the database when the save button is clicked on the Modify Customer screen.
     * @param customerID The customer's unique ID.
     * @param customerName The customer's name.
     * @param address The customer's address.
     * @param postalCode The customer's postal code.
     * @param phone The customer's phone number.
     * @param divisionID The customer's division ID.
     * @param lastUpdatedBy The user who last updated the customer data in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void modifyCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID, String lastUpdatedBy) throws SQLException {
        String modifyCustomerInDB = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Create_Date = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
        Customer customerToModify = getCustomerByID(customerID);
        LocalDateTime createDate = customerToModify.getCreateDateTime();
        DBQuery.setPreparedStatement(connection, modifyCustomerInDB);
        try {
            pst = DBQuery.getPreparedStatement();
            pst.setString(1, customerName);
            pst.setString(2, address);
            pst.setString(3, postalCode);
            pst.setString(4, phone);
            pst.setInt(5, divisionID);
            pst.setDate(6, Date.valueOf(createDate.toLocalDate()));
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.setString(8, lastUpdatedBy);
            pst.setInt(9, customerID);
            pst.execute();
        }
        catch (SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** This method deletes the customer's appointments from the database first, then deletes the customer.
     * @param customerID The customer's unique ID.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void deleteCustomer(int customerID) throws SQLException {
        String deleteCustomerFromDB = "DELETE FROM customers WHERE Customer_ID =" + customerID;
        AppointmentDAOImpl apptDAO =  new AppointmentDAOImpl();
        DBQuery.setPreparedStatement(connection, deleteCustomerFromDB);
        try {
            PreparedStatement pst = DBQuery.getPreparedStatement();
            apptDAO.deleteCustomerAppts(customerID);
            pst.execute();
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
