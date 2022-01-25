/**
 * @author Gabriel Izzo<gizzo@wgu.edu>
 * @version 1.0
 * @Javadoc Javadoc located in the project folder in a separate folder titled javadoc. Example file path: \C195_Project\javadoc<b/>
 */


package controller;

import DAO.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private RadioButton currentMonthRadioBtn;
    @FXML
    private RadioButton allApptsRadioBtn;
    @FXML
    private RadioButton currentWeekRadioBtn;
    @FXML
    private ToggleGroup apptsViewToggle;
    @FXML
    private Label timeZoneLbl;
    @FXML
    private Button deleteApptButton;
    @FXML
    private Button scheduleApptButton;
    @FXML
    private Button modifyApptButton;
    @FXML
    private Button customersMenuButton;
    @FXML
    private Button reportsByMonthAndTypeButton;
    @FXML
    private Button exitAppButton;
    @FXML
    private Button contactScheduleButton;
    @FXML
    private Button userScheduleButton;
    @FXML
    private TableColumn <Appointment, Integer> apptIDCol;
    @FXML
    private TableColumn <Appointment, String> titleCol;
    @FXML
    private TableColumn <Appointment, String> descriptionCol;
    @FXML
    private TableColumn <Appointment, String> locationCol;
    @FXML
    private TableColumn <Appointment, String> contactCol;
    @FXML
    private TableColumn <Appointment, String> typeCol;
    @FXML
    private TableColumn <Appointment, String> dateCol;
    @FXML
    private TableColumn <Appointment, LocalTime> startTimeCol;
    @FXML
    private TableColumn <Appointment, LocalTime> endTimeCol;
    @FXML
    private TableColumn <Appointment, Integer> customerIDCol;
    @FXML
    private TableView<Appointment> apptsTable;

    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private  ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
    private  ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();
    private AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    public static Appointment apptToModify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptsViewToggle = new ToggleGroup();
        allApptsRadioBtn.setToggleGroup(apptsViewToggle);
        currentMonthRadioBtn.setToggleGroup(apptsViewToggle);
        currentWeekRadioBtn.setToggleGroup(apptsViewToggle);
        allApptsRadioBtn.setSelected(true);
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
