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

public class UserScheduleController implements Initializable {
    @FXML
    private Button mainMenuButton;
    @FXML
    private TableColumn userNameCol;
    @FXML
    private TableColumn apptIDCol;
    @FXML
    private TableColumn titleCol;
    @FXML
    private TableColumn typeCol;
    @FXML
    private TableColumn descriptionCol;
    @FXML
    private TableColumn apptDateCol;
    @FXML
    private TableColumn startTimeCol;
    @FXML
    private TableColumn endTimeCol;
    @FXML
    private TableColumn customerIDCol;
    @FXML
    private TableView userScheduleTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (mainMenuButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
