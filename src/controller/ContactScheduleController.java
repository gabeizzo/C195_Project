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

public class ContactScheduleController implements Initializable {
    public Button mainMenuBtn;
    public TableColumn customerIDCol;
    public TableColumn endTimeCol;
    public TableColumn startTimeCol;
    public TableColumn apptDateCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn titleCol;
    public TableColumn apptIDCol;
    public TableColumn contactNameCol;
    public TableView contactScheduleTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
