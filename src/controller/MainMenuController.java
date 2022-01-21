package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public Button deleteApptButton;
    public Button scheduleApptButton;
    public Button modifyApptButton;
    public Button customersMenuButton;
    public Button reportsByMonthAndTypeButton;
    public Button exitAppButton;
    public Button contactScheduleButton;
    public Button userScheduleButton;
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn dateCol;
    public TableColumn startTimeCol;
    public TableColumn endTimeCol;
    public TableColumn customerIDCol;
    public RadioButton currentMonthRadioBtn;
    public RadioButton allApptsRadioBtn;
    public RadioButton currentWeekRadioBtn;
    public Label timeZoneLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void toScheduleAppointment(ActionEvent actionEvent) {
    }

    public void toModifyAppointment(ActionEvent actionEvent) {
    }

    public void toCustomersMenu(ActionEvent actionEvent) {
    }

    public void toReportsByMonthAndType(ActionEvent actionEvent) {
    }

    public void exitApplication(ActionEvent actionEvent) {
    }

    public void toContactSchedule(ActionEvent actionEvent) {
    }

    public void toUserSchedule(ActionEvent actionEvent) {
    }

    public void viewCurrentMonthAppts(ActionEvent actionEvent) {
    }

    public void viewAllAppts(ActionEvent actionEvent) {
    }

    public void viewCurrentWeekAppts(ActionEvent actionEvent) {
    }
}
