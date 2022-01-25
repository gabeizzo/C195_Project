package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    private TableColumn customerIDCol;
    @FXML
    private TableColumn customerNameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn postalCodeCol;
    @FXML
    private TableColumn phoneCol;
    @FXML
    private TableColumn createDateCol;
    @FXML
    private TableColumn createdByCol;
    @FXML
    private TableColumn lastUpdatedCol;
    @FXML
    private TableColumn lastUpdatedByCol;
    @FXML
    private TableColumn divisionIDCol;
    @FXML
    private TableView customerDataTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void deleteCustomer(ActionEvent actionEvent) throws IOException{
    }

    public void toAddCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) (addCustomerBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,400 ,600));
        stage.show();
    }

    public void toModifyCustomer(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
        Stage stage = (Stage) (modifyCustomerBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,400 ,600));
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (mainMenuButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
