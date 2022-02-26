package controller;

import DAO.CustomerDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the CustomersMenuController class which defines the methods to be used for adding, deleting, modifying and displaying Customers from the database.
 */
public class CustomersMenuController implements Initializable {
    @FXML
    private Button deleteCustomerBtn;
    @FXML
    private Button addCustomerBtn;
    @FXML
    private Button modifyCustomerBtn;
    @FXML
    private Button mainMenuButton;
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, String> createDateCol;
    @FXML
    private TableColumn<Customer, String> createdByCol;
    @FXML
    private TableColumn<Customer, LocalTime> lastUpdatedCol;
    @FXML
    private TableColumn<Customer, String> lastUpdatedByCol;
    @FXML
    private TableColumn<Customer, Integer> divisionIDCol;
    @FXML
    private TableView<Customer> customerDataTable;

    /** This is the CustomersMenuController constructor.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public CustomersMenuController() throws SQLException{
    }

    /** This method initializes the Customers Menu and sets up the customerDataTable with database data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            customerDataTable.setItems(customerDAO.getAllDBCustomers());
            customerIDCol.setCellValueFactory( new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDateFormatted"));
            createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdateFormatted"));
            lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

            customerDataTable.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** This method deletes a selected customer from the database.
     * @param actionEvent When Delete Customer button is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void deleteCustomer(ActionEvent actionEvent) throws IOException{
    }

    /** This method loads the Add Customer screen.
     * @param actionEvent When the Add Customer button is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    @FXML
    private void toAddCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Stage stage = (Stage) (addCustomerBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,400 ,600));
        stage.show();
    }

    /** This method loads the Modify Customer screen.
     * @param actionEvent When the Modify Customer button is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toModifyCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyCustomer.fxml")));
        Stage stage = (Stage) (modifyCustomerBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,400 ,600));
        stage.show();
    }

    /** This method returns the user to the Main Menu from the Customers Menu screen.
     * @param actionEvent When the Main Menu button is activated from the Customers Menu.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (mainMenuButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
