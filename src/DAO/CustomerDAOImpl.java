package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerDAOImpl implements CustomerDAO{
    private final String selectAllCustomers = "SELECT * FROM customers";
    private String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private String getCustomerStatement = "SELECT * from customers where Customer_ID=";
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    private Connection connection = Main.connection;

    ObservableList<Customer> customers = FXCollections.observableArrayList();
    public CustomerDAOImpl() throws SQLException {

    }

    /** Creates a customer object.
     * @return Customer object.
     * @throws SQLException Throws SQLException if there is a MySQL database error.
     */
    private Customer createCustomer() throws SQLException {
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

        Customer customer = new Customer(customerID, customerName, customerAddress, postalCode, phone,
                createDateTime, createdBy, lastUpdate, lastUpdatedBy, divisionID);
        return customer;
    }
}
