package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    public TextField customerIDTxt;
    public TextField customerNameTxt;
    public TextField customerPhoneTxt;
    public TextField customerStreetAddressTxt;
    public TextField customerCityTxt;
    public TextField customerPostalCodeTxt;
    public ComboBox customerStateCB;
    public ComboBox customerCountryCB;
    public ComboBox customerDivisionIDCB;
    public Button saveCustomerBtn;
    public Button cancelModifyCustomerBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveModifiedCustomer(ActionEvent actionEvent) throws IOException{
        //if else here with exceptions and try catch block
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) (saveCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void toCustomersMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) (cancelModifyCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
