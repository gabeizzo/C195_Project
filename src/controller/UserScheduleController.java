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

public class UserScheduleController implements Initializable {
    private AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<Appointment> testUser;
    private ObservableList<Appointment> adminUser;
    @FXML
    private Button mainMenuButton;
    @FXML
    private TableColumn<Appointment, String> userNameCol;
    @FXML
    private TableColumn<Appointment, Integer> apptIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> apptDateCol;
    @FXML
    private TableColumn<Appointment, String> startTimeCol;
    @FXML
    private TableColumn<Appointment, String> endTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;
    @FXML
    private TableView<Appointment> userScheduleTable;

    /** This is the constructor for the UserScheduleController.
     * @throws SQLException
     */
    public UserScheduleController() throws SQLException {
    }

    /**
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            // Grab all user specific users appointments
            testUser = apptDAO.getApptsByUserID(1);
            adminUser = apptDAO.getApptsByUserID(2);
            // add each user's list to overall list
           // addListToAllContactAppointments(firstUser);
           // addListToAllContactAppointments(secondUser);

            // set up table view
            userScheduleTable.setItems(allAppointments);
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("user"));
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
            apptDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateFormatted"));
            startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeFormatted"));
            endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeFormatted"));
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Returns to the Main Menu screen when the Return to Main Menu button is activated.
     * @param actionEvent When the Return to Main Menu Button is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (mainMenuButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
