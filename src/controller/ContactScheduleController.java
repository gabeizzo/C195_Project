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
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the ContactScheduleController class.
 * This class defines the methods used to display the Contact Schedule GUI report.
 */
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

    /** This is the ContactScheduleController constructor.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ContactScheduleController() throws SQLException{
    }

    /** This method initializes the Contact Schedule screen and loads the appointments for contacts.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactScheduleTable.setItems(allContactsAppts);
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateFormatted"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeFormatted"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeFormatted"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /** This method returns to the main menu if the Main Menu button gets selected.
     * @param actionEvent When the Main Menu button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
