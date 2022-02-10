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
     * @throws SQLException
     */
    public CustomersMenuController() throws SQLException{
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Grab all customers from the database and populate the table view.
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            customerDataTable.setItems(customerDAO.getAllDBCustomers());
            customerIDCol.setCellValueFactory( new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            createDateCol.setCellValueFactory(new PropertyValueFactory<>("formattedCreateDate"));
            createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("formattedLastUpdate"));
            lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

            customerDataTable.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }



    }

    public void deleteCustomer(ActionEvent actionEvent) throws IOException{
    }

    public void toAddCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddCustomer.fxml")));
        Stage stage = (Stage) (addCustomerBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,400 ,600));
        stage.show();
    }

    public void toModifyCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyCustomer.fxml")));
        Stage stage = (Stage) (modifyCustomerBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,400 ,600));
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (mainMenuButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
