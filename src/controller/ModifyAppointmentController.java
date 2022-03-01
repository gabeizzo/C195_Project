package controller;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import Utilities.ConvertTime;
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
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    @FXML
    private TextField apptIDTxt;
    @FXML
    private ComboBox<Customer> customerIDCB;
    @FXML
    private ComboBox<User> userNameCB;
    @FXML
    private TextField apptTitleTxt;
    @FXML
    private TextField apptDescriptionTxt;
    @FXML
    private TextField apptLocationTxt;
    @FXML
    private ComboBox<Contact> contactCB;
    @FXML
    private ComboBox<String> apptTypeCB;
    @FXML
    private DatePicker apptDatePicker;
    @FXML
    private ComboBox<LocalTime> apptStartTimeCB;
    @FXML
    private ComboBox<LocalTime> apptEndTimeCB;
    @FXML
    private Button saveApptButton;
    @FXML
    private Button cancelScheduleApptBtn;

    private LocalTime start = LocalTime.of(5,0);
    private LocalTime end = LocalTime.of(23, 0);
    private ContactDAOImpl contactDAO = new ContactDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    private ObservableList<String> apptTypes = FXCollections.observableArrayList();
    private ObservableList<Appointment> appts = FXCollections.observableArrayList();
    private Customer customer;
    private User user;
    private String title;
    private String description;
    private String location;
    private Contact contact;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Appointment apptToModify = MainMenuController.apptToModify;


    /** The ModifyAppointmentController constructor.
     */
    public ModifyAppointmentController() throws SQLException {
    }

    /** Initializes the Modify Appointment screen with the selected appointment data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIDTxt.setText(String.valueOf(apptToModify.getApptID()));
        apptTypes.addAll("Planning Session", "De-Briefing", "Department Meeting", "Escalation", "Review","Miscellaneous");
        apptTypeCB.setItems(apptTypes);
        int apptTypeIndex = apptTypes.indexOf(apptToModify.getApptType());
        apptTypeCB.getSelectionModel().select(apptTypeIndex);

        try {
            //Fills the combo boxes with customers, names and contacts.
            customerIDCB.setItems(customerDAO.getAllDBCustomers());
            customerIDCB.getSelectionModel().select(customerDAO.getCustomerByID(apptToModify.getCustomerID()));
            userNameCB.setItems(userDAO.getAllUsers());
            userNameCB.getSelectionModel().select(userDAO.getUserByID(apptToModify.getUserID()));
            contactCB.setItems(contactDAO.getAllContactsFromDB());
            contactCB.getSelectionModel().select(contactDAO.getContactByID(apptToModify.getContactID()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // set title, description, and location
        apptTitleTxt.setText(apptToModify.getApptTitle());
        apptDescriptionTxt.setText(apptToModify.getDescription());
        apptLocationTxt.setText(apptToModify.getLocation());

        // set datepicker and get initial date value
        apptDatePicker.setValue(apptToModify.getStartDateTime().toLocalDate());
        date = apptDatePicker.getValue();

        //populate times and select time
        ConvertTime.displayValidTimes(apptStartTimeCB, start, end);
        ConvertTime.displayValidTimes(apptEndTimeCB, start, end);
        apptStartTimeCB.getSelectionModel().select(apptToModify.getStartDateTime().toLocalTime());
        apptEndTimeCB.getSelectionModel().select(apptToModify.getEndDateTime().toLocalTime());

    }

    /** Saves the modified appointment to the database and returns to the Main Menu.
     * @param actionEvent When the save button is activated on the Modify Appointment screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveAppt(ActionEvent actionEvent) throws IOException {
        //if else here with try/catch
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (saveApptButton.getScene().getWindow());
        stage.setTitle("Appointment+ Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** Returns to the Main Menu when the cancel button is activated on the Modify Appointment screen.
     * @param actionEvent When the cancel button is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel modifying this appointment?");
        cancelAlert.setTitle("Cancel Modify Appointment");
        Optional<ButtonType> result = cancelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
            Stage stage = (Stage) (cancelScheduleApptBtn.getScene().getWindow());
            stage.setTitle("Appointment+ Main Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
    }
}
