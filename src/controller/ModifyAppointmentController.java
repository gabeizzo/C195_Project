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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
    private Button cancelScheduleApptButton;

    private LocalTime start = LocalTime.of(4,0);
    private LocalTime end = LocalTime.of(23, 0);
    private ContactDAOImpl contactDAO = new ContactDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
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
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // populate customer combo box and select current customer
            customerIDCB.setItems(customerDAO.getAllDBCustomers());
            customerIDCB.getSelectionModel().select(customerDAO.getCustomerByID(apptToModify.getCustomerID()));

            //  populate user combo box and select current user
            userNameCB.setItems(userDAO.getAllUsers());
            userNameCB.getSelectionModel().select(userDAO.getUser(apptToModify.getUserID()));
            // populate contact combo box
            contactCB.setItems(contactDAO.getAllContactsFromDB());
            contactCB.getSelectionModel().select(contactDAO.getContactByID(apptToModify.getContactID()));
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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

    public void saveAppt(ActionEvent actionEvent) throws IOException {
        //if else here with try/catch
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (saveApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (cancelScheduleApptButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
