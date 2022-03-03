package controller;

import DAO.CustomerDAOImpl;
import Utilities.TimeZoneLambda;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This is the CustomersMenuController class which defines the methods to be used for adding, deleting, modifying and displaying Customers from the database.
 */
public class CustomersMenuController implements Initializable {
    @FXML
    private TextField customerSearchBar;
    @FXML
    private Label timeZoneLbl;
    @FXML
    private Label dateTimeLbl;
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

    private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    public static Customer modifyCustomer;

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

        //TimeZoneLambda changes the timeZoneLbl on the Main Menu screen based on the user's system default.
        TimeZoneLambda getZone = () -> {
            timeZoneLbl.setText(ZoneId.of(TimeZone.getDefault().getID()).toString());
        };
        getZone.showZone();
        displayClock();
        viewAllCustomerFromDB();
    }

    private void viewAllCustomerFromDB() {
        try {
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            customerDataTable.setItems(customerDAO.getAllDBCustomers());
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** displayClock method uses the EventHandler interface with a lambda expression to display a formatted animated digital clock on the Customers Menu.
     */
    public void displayClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTimeLbl.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /** This method deletes a selected customer from the database and re-loads the Customers Menu to prevent duplicate table entries.
     * @param actionEvent When Delete Customer button is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void deleteCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        Customer delete = customerDataTable.getSelectionModel().getSelectedItem();

        if(delete != null){
        Alert deleteCustomer = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer: " + delete.getCustomerName() + "?\n" +
                "\n" + delete.getCustomerName() + "'s appointments will also be deleted.\n\nContinue anyways?");
        deleteCustomer.setTitle("Delete Confirmation");

        Optional<ButtonType> result = deleteCustomer.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            customerDAO.deleteCustomer(delete.getCustomerID());

            //Reload the screen after the delete to re-initialize the Customers Menu, otherwise will get duplicates.
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
            Stage stage = (Stage) (deleteCustomerBtn.getScene().getWindow());
            stage.setTitle("Customers Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
            }
        }
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
        modifyCustomer = customerDataTable.getSelectionModel().getSelectedItem();
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

    /** This method searches the Customer Data table for any customers that have either a Customer ID or Name that match the text input.
     * @param actionEvent When data is entered into the search bar on the main menu.
     * @throws SQLException Thrown if there is a database access error.
     */
    public void searchCustomers(ActionEvent actionEvent) throws SQLException {

        String searchInput = customerSearchBar.getText();

        ObservableList<Customer> customers = searchByCustomerName(searchInput);
        try {
            int customerID = Integer.parseInt(searchInput);
            Customer c = searchByCustomerID(customerID);
            if(c != null){
                customers.add(c);
            }
        } catch (NumberFormatException e) {
            //ignore
        }
        customerDataTable.setItems(customers);

        if(customerSearchBar.getText().isBlank()){

            viewAllCustomerFromDB();
        }

        if(customers.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Results");
            alert.setContentText("""
                    No customers found with the entered ID or Name.
                    Please check spelling and try again.""");
            alert.showAndWait();

            customerSearchBar.clear();
            viewAllCustomerFromDB();

        }

    }
    /** Searches for Customers by Name.
     @param partialCustomerName The text input entered into the search field above the Customer Data table.
     @return resultsSearch The search results to be displayed in the Customers table.
     @throws SQLException Thrown if there is a database access error.
     */
    private ObservableList<Customer> searchByCustomerName(String partialCustomerName) throws SQLException {
        ObservableList<Customer> resultsSearch = FXCollections.observableArrayList();

        viewAllCustomerFromDB();
        ObservableList<Customer> allCustomers = customerDataTable.getItems();

        for(Customer c : allCustomers){
            if(c.getCustomerName().equalsIgnoreCase(partialCustomerName) || c.getCustomerName().contains(partialCustomerName)
                    || c.getCustomerName().toLowerCase().contains(partialCustomerName) || c.getCustomerName().toUpperCase().contains(partialCustomerName))
                resultsSearch.add(c);
        }
        return resultsSearch;
    }

    /** Searches customers by id.
     @param customerID The customer id that is searched.
     @return c, the customer that matches the searched id.
     @throws SQLException Thrown if there is a database access error.
     */
    private Customer searchByCustomerID(int customerID) throws SQLException {
        ObservableList<Customer> allCustomers = customerDataTable.getItems();
        for (Customer c : allCustomers) {
            if (c.getCustomerID() == customerID) {
                return c;
            }
        }
        return null;
    }

}
