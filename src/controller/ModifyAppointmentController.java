package controller;

import javafx.event.ActionEvent;
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

public class ModifyAppointmentController implements Initializable {
    public TextField apptIDTxt;
    public ComboBox customerIDCB;
    public ComboBox userNameCB;
    public TextField apptTitleTxt;
    public TextField apptDescriptionTxt;
    public TextField apptLocationTxt;
    public ComboBox contactCB;
    public ComboBox apptTypeCB;
    public DatePicker apptDatePicker;
    public ComboBox apptStartTimeCB;
    public ComboBox apptEndTimeCB;
    public Button saveApptButton;
    public Button cancelScheduleApptButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveAppt(ActionEvent actionEvent) throws IOException {
        //if else here with try/catch
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (saveApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (cancelScheduleApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
