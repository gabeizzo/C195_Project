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
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This is the ModifyAppointmentController class.
 * This class holds the methods used to modify appointments in the database.
 */
public class ModifyAppointmentController implements Initializable {

    //Appointment to modify related fields
    private final Appointment appt = MainMenuController.appt;
    private Customer customerName;
    private User userName;
    private String apptTitle;
    private String description;
    private String location;
    private Contact contactName;
    private String apptType;

    //GUI Buttons, Text-fields, Combo Boxes and Date Picker
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

    //Data Access Objects
    private final AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    private final ContactDAOImpl contactDAO = new ContactDAOImpl();
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();

    //List of Appointment Types for populating the apptTypeCB combo box
    private final ObservableList<String> apptTypes = FXCollections.observableArrayList();

    //Time variables for populating combo boxes and start/end time validation
    private LocalDate apptDate;
    private LocalTime apptStart;
    private LocalTime apptEnd;

    //Start times list at 3AM to account for 5-hour difference for Hawaii based appointments that potentially start at 8AM EST
    private final LocalTime start = LocalTime.of(3,0);
    private final LocalTime end = LocalTime.of(23, 0);
    private LocalDateTime apptStartDateTime;
    private LocalDateTime apptEndDateTime;

    /** This is the ModifyAppointmentController constructor.
     */
    public ModifyAppointmentController() throws SQLException {
    }

    /** Initializes the Modify Appointment screen with the selected appointment data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Sets the form fields with appointment data
        try {
            apptIDTxt.setText(String.valueOf(appt.getApptID()));
            apptTypes.addAll("Planning Session", "De-Briefing", "Department Meeting", "Escalation", "Review","Miscellaneous");
            apptTypeCB.setItems(apptTypes);
            int apptTypeIndex = apptTypes.indexOf(appt.getApptType());
            apptTypeCB.getSelectionModel().select(apptTypeIndex);

            //Fills the combo boxes with customers, names and contacts.
            customerIDCB.setItems(customerDAO.getAllDBCustomers());
            customerIDCB.getSelectionModel().select(customerDAO.getCustomerByID(appt.getCustomerID()));
            userNameCB.setItems(userDAO.getAllUsers());
            userNameCB.getSelectionModel().select(userDAO.getUserByID(appt.getUserID()));
            contactCB.setItems(contactDAO.getAllContactsFromDB());
            contactCB.getSelectionModel().select(contactDAO.getContactByID(appt.getContactID()));

            // set title, description, and location
            apptTitleTxt.setText(appt.getApptTitle());
            apptDescriptionTxt.setText(appt.getDescription());
            apptLocationTxt.setText(appt.getLocation());

            // set date-picker and get initial date value
            apptDatePicker.setValue(appt.getStartDateTime().toLocalDate());
            apptDate = apptDatePicker.getValue();

            //populate times and select time
            ConvertTime.displayValidTimes(apptStartTimeCB, start, end);
            ConvertTime.displayValidTimes(apptEndTimeCB, start, end);
            apptStartTimeCB.getSelectionModel().select(appt.getStartDateTime().toLocalTime());
            apptEndTimeCB.getSelectionModel().select(appt.getEndDateTime().toLocalTime());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Gets all data from the input fields on the Modify Appointment screen.
     */
    private void getApptData() {
        customerName = customerIDCB.getSelectionModel().getSelectedItem();
        userName = userNameCB.getSelectionModel().getSelectedItem();
        apptTitle = apptTitleTxt.getText().trim();
        description = apptDescriptionTxt.getText().trim();
        location = apptLocationTxt.getText().trim();
        contactName = contactCB.getSelectionModel().getSelectedItem();
        apptType = apptTypeCB.getSelectionModel().getSelectedItem();
        apptDate = apptDatePicker.getValue();
        apptStart = apptStartTimeCB.getValue();
        apptEnd = apptEndTimeCB.getValue();
    }

    /** This method checks to see if the appointment start and times are valid.
     * It then compares these times to the valid business hours in EST.
     * @return True if appointment start/end times entered line up with possible start/end times in EST, false if the start/end times in EST are not valid.
     */
    private boolean apptStartEndAreValid() {
        LocalDateTime apptStartDateTime = LocalDateTime.of(apptDate, apptStart);
        LocalDateTime apptEndDateTime = LocalDateTime.of(apptDate, apptEnd);
        return ConvertTime.compareLToESTBizHrs(apptStartDateTime) && ConvertTime.compareLToESTBizHrs(apptEndDateTime);

    }

    /** Checks to see if the appointment data entered is valid and that there are no blank or null fields or invalid start/end times.
     * @return True if the appointment data is valid, false if there are blank fields or start/end times are invalid.
     */
    private boolean apptDataIsValid(){
        getApptData();
        if(customerName == null || userName == null || apptTitle.isBlank() || description.isBlank() || location.isBlank() || contactName == null || apptType == null || apptDate == null || apptStart == null || apptEnd == null){
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("All Fields Required");
            blankFields.setContentText("All fields are required and must not be left blank.\nPlease complete any blank/missing fields and try again.");
            blankFields.showAndWait();
            return false;
        }
        else if(apptStart.isAfter(apptEnd) || apptStart.equals(apptEnd)){
            Alert apptStartInvalid = new Alert(Alert.AlertType.ERROR);
            apptStartInvalid.setTitle("Invalid Start Time");
            apptStartInvalid.setContentText("""
                    Attention: Start time is invalid.

                    Please ensure appointment start time is before and is not equal to appointment end time and try again.""");
            apptStartInvalid.showAndWait();
            return false;
        }
        else if(!apptStartEndAreValid()){
            Alert invalidTimes = new Alert(Alert.AlertType.ERROR);
            invalidTimes.setTitle("Invalid Start/End Times");
            invalidTimes.setHeight(500);
            invalidTimes.setWidth(450);
            invalidTimes.setContentText("Appointment start and end times must be between 08:00AM-10:00PM(EST).\n\nAppointment Start Time(EST): "
                    + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptStart)))
                    + "\nAppointment End Time(EST): " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptEnd)))
                    + "\n\nFor your reference:\nLocal Time Zone: " + ZoneId.of(TimeZone.getDefault().getID())
                    + "\nCurrent Local Time: " + ConvertTime.timeFormatted(LocalTime.now())
                    + "\nCurrent Local Time in EST: " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.now())));
            invalidTimes.showAndWait();
            return false;
        }
        else {
            //Checks if there is any appointments that overlap and returns false if there are conflicting appointments.
            try {
                if(!apptOverlap()){
                    return false;
                }
                else {
                    apptStartDateTime = LocalDateTime.of(apptDate, apptStart);
                    apptEndDateTime = LocalDateTime.of(apptDate, apptEnd);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /** This method checks to see if the appointment's start and end times overlap with any existing appointments.
     * If there is a conflict with the start or end times, an error dialog message displays showing the appointment there is a conflict with.
     * @return true if there are no conflicts with any existing appointments, false if there are.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    private boolean apptOverlap() throws SQLException {

        try{
            //establish the appointment start and end times for comparison.
            apptStartDateTime = LocalDateTime.of(apptDate, apptStart);
            apptEndDateTime = LocalDateTime.of(apptDate, apptEnd);
            ObservableList<Appointment> appts = apptDAO.getAllApptsFromDB();

            //Loop through the appointments and check if there are any with conflicting start and end times.
            for(Appointment a : appts){
                if (a.getApptID() != appt.getApptID()){
                if(a.getCustomerID() == customerName.getCustomerID()) {

                    //Checks appointment start times for overlap.
                    if (apptStartDateTime.isAfter(a.getStartDateTime().minusMinutes(1)) && apptStartDateTime.isBefore(a.getEndDateTime())) {
                        Alert apptCollision = new Alert(Alert.AlertType.ERROR);
                        apptCollision.setTitle("Appointment Conflict");
                        apptCollision.setContentText("This appointment's start/end times overlap with the following appointment:\n\n"
                                + "Appointment ID: " + a.getApptID() + "\nAppointment Title: " + a.getApptTitle() + "\nDescription: "
                                + a.getDescription() + "\nLocal Start Time:" + a.getStartTime() + "\nLocal End Time: " + a.getEndTime()
                                + "\nAppointment Start Time(EST): " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptStart)))
                                + "\nAppointment End Time(EST): " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptEnd)))
                        );
                        apptCollision.showAndWait();
                        return false;
                    }
                    //Checks appointment end times for overlap.
                    else if (apptEndDateTime.isAfter(a.getStartDateTime().minusMinutes(1)) && apptEndDateTime.isBefore(a.getEndDateTime())) {
                        Alert apptCollision = new Alert(Alert.AlertType.ERROR);
                        apptCollision.setTitle("Appointment Conflict");
                        apptCollision.setContentText("This appointment's start/end times overlap with the following appointment:\n\n"
                                + "Appointment ID: " + a.getApptID() + "\nAppointment Title: " + a.getApptTitle() + "\nDescription: "
                                + a.getDescription() + "\nLocal Start Time:" + a.getStartTime() + "\nLocal End Time: " + a.getEndTime()
                                + "\nAppointment start time(EST): " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptStart)))
                                + "\nAppointment end time(EST): " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptEnd)))
                        );
                        apptCollision.showAndWait();
                        return false;
                    }
                }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /** Saves the modified appointment to the database and returns to the Main Menu.
     * @param actionEvent When the save button is activated on the Modify Appointment screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveAppt(ActionEvent actionEvent) throws IOException {

        //Check that field data is valid before attempting to save the appointment
        if(apptDataIsValid()) {
            LocalDateTime createDate = LocalDateTime.now();
            String createdBy = LoginScreenController.userName;

            try {
                //Modify the appointment
                apptDAO.modifyAppt(appt.getApptID(), apptTitle, description, location, apptType, apptStartDateTime,
                        apptEndDateTime, appt.getCreateDate(), appt.getCreatedBy(), appt.getLastUpdate(), appt.getLastUpdatedBy(), customerName.getCustomerID(), userName.getUserID(), contactName.getContactID());

                //Return to Main Menu after appointment is saved
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
                Stage stage = (Stage) (saveApptButton.getScene().getWindow());
                stage.setTitle("Appointment+ Main Menu");
                stage.setScene(new Scene(root, 1200, 700));
                stage.show();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
