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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


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
    private ComboBox customerStateCB;
    @FXML
    private ComboBox customerCountryCB;
    @FXML
    private ComboBox customerDivisionIDCB;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Button cancelAddCustomerBtn;

    private Connection connection = Main.connection;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAddedCustomer(ActionEvent actionEvent) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) (saveCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void toCustomersMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) (cancelAddCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();

    }
}
