package controller;

import javafx.event.ActionEvent;
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
    public TableView customerDataTable;

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
