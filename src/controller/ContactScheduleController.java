package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactScheduleController implements Initializable {
    @FXML
    private Button mainMenuBtn;
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;
    @FXML
    private TableColumn<Appointment, String> endTimeCol;
    @FXML
    private TableColumn<Appointment, String> startTimeCol;
    @FXML
    private TableColumn<Appointment, String> apptDateCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML
    private TableColumn<Appointment, String> contactNameCol;
    @FXML
    private TableView<Appointment> contactScheduleTable;

    private ObservableList<Appointment> contact1Appts;
    private ObservableList<Appointment> contact2Appts;
    private ObservableList<Appointment> contact3Appts;
    private ObservableList<Appointment> allContactsAppts = FXCollections.observableArrayList();

    public ContactScheduleController() throws SQLException{
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactScheduleTable.setItems(allContactsAppts);
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateFormatted"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeFormatted"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeFormatted"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
