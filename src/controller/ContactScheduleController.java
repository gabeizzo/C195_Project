package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void toMainMenu(ActionEvent actionEvent) {
    }
}
