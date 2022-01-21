package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    public TextField customerIDTxt;
    public TextField customerNameTxt;
    public TextField customerPhoneTxt;
    public TextField customerStreetAddressTxt;
    public TextField customerCityTxt;
    public TextField customerPostalCodeTxt;
    public TextField customerStateTxt;
    public ComboBox customerCountryCB;
    public ComboBox customerDivisionIDCB;
    public Button saveCustomerBtn;
    public Button cancelAddCustomerBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAddedCustomer(ActionEvent actionEvent) {
    }

    public void toMainMenu(ActionEvent actionEvent) {
    }
}
