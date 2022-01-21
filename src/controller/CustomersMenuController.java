package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomersMenuController implements Initializable {
    public Button deleteCustomerBtn;
    public Button addCustomerBtn;
    public Button modifyCustomerBtn;
    public Button mainMenuButton;
    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneCol;
    public TableColumn createDateCol;
    public TableColumn createdByCol;
    public TableColumn lastUpdatedCol;
    public TableColumn lastUpdatedByCol;
    public TableColumn divisionIDCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }

    public void toAddCustomer(ActionEvent actionEvent) {
    }

    public void toModifyCustomer(ActionEvent actionEvent) {
    }

    public void toMainMenu(ActionEvent actionEvent) {
    }
}
