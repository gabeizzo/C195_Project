package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleAppointmentController implements Initializable {
    @FXML
    private TextField apptIDTxt;
    @FXML
    private ComboBox customerIDCB;
    @FXML
    private ComboBox userNameCB;
    @FXML
    private TextField apptTitleTxt;
    @FXML
    private TextField apptDescriptionTxt;
    @FXML
    private TextField apptLocationTxt;
    @FXML
    private ComboBox contactCB;
    @FXML
    private ComboBox apptTypeCB;
    @FXML
    private DatePicker apptDatePicker;
    @FXML
    private ComboBox apptStartTimeCB;
    @FXML
    private ComboBox apptEndTimeCB;
    @FXML
    private Button saveApptButton;
    @FXML
    private Button cancelScheduleApptButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAppt(ActionEvent actionEvent) throws IOException{
        //if else goes here
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (saveApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (cancelScheduleApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
