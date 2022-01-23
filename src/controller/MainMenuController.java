/**
 * @author Gabriel Izzo<gizzo@wgu.edu>
 * @version 1.0
 * @Javadoc Javadoc located in the project folder in a separate folder titled javadoc. Example file path: \C195_Project\javadoc<b/>
 */


package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
    public TableView apptsTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void deleteAppointment(ActionEvent actionEvent) throws IOException{
    }

    public void toScheduleAppointment(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/ScheduleAppointment.fxml"));
        Stage stage = (Stage) (scheduleApptButton.getScene().getWindow());
        stage.setTitle("Schedule Appointment");
        stage.setScene(new Scene(root,400 ,700));
        stage.show();
    }

    public void toModifyAppointment(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
        Stage stage = (Stage) (modifyApptButton.getScene().getWindow());
        stage.setTitle("Modify Appointment");
        stage.setScene(new Scene(root,400 ,700));
        stage.show();
    }

    public void toCustomersMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) (customersMenuButton.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();

    }

    public void toReportsByMonthAndType(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsByMonthAndType.fxml"));
        Stage stage = (Stage) (reportsByMonthAndTypeButton.getScene().getWindow());
        stage.setTitle("Reports By Month and Type");
        stage.setScene(new Scene(root,600 ,500));
        stage.show();
    }

    public void toContactSchedule(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactSchedule.fxml"));
        Stage stage = (Stage) (contactScheduleButton.getScene().getWindow());
        stage.setTitle("Contact Schedule");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void toUserSchedule(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserSchedule.fxml"));
        Stage stage = (Stage) (userScheduleButton.getScene().getWindow());
        stage.setTitle("User Schedule");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void viewCurrentMonthAppts(ActionEvent actionEvent) throws IOException{
    }

    public void viewAllAppts(ActionEvent actionEvent) throws IOException{
    }

    public void viewCurrentWeekAppts(ActionEvent actionEvent) throws IOException{
    }

    public void exitApplication(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }
}
