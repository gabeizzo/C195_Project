package controller;

import Utilities.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Country;
import model.FirstLvlDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the AddCustomerController class.
 * This class defines the methods used to add customers to the database.
 */
public class AddCustomerController implements Initializable {
    @FXML
    private TextField customerIDTxt;
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField customerPhoneTxt;
    @FXML
    private TextField customerStreetAddressTxt;
    @FXML
    private TextField customerCityTxt;
    @FXML
    private TextField customerPostalCodeTxt;
    @FXML
    private ComboBox<Country> customerCountryCB;
    @FXML
    private ComboBox<FirstLvlDivision> customerDivisionIDCB;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Button cancelAddCustomerBtn;
    private Connection connection = Main.connection;

    /** This method initializes the Add Customer screen.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** This method saves the entered customer data and stores a new customer in the database.
     * @param actionEvent When the Save button is activated on the Add Customer screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveAddedCustomer(ActionEvent actionEvent) throws IOException{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        Stage stage = (Stage) (saveCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** This method cancels adding the customer to the database and returns to the Customers Menu.
     * @param actionEvent When the Cancel button is activated on the Add Customer screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toCustomersMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        Stage stage = (Stage) (cancelAddCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();

    }
}
