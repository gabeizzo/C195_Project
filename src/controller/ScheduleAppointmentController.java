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
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the ScheduleAppointmentController class.
 * This class defines the methods used to schedule appointments and save them to the database.
 */
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

    /**
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *
     * @param actionEvent When the Save button is activated on the Schedule Appointment screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveAppt(ActionEvent actionEvent) throws IOException{
        //if else goes here
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (saveApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** This method loads the Main Menu screen when scheduling an appointment is cancelled.
     * @param actionEvent When the Cancel button is activated on the Schedule Appointment Screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (cancelScheduleApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
