package controller;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import utilities.ConvertTime;
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
import javafx.util.StringConverter;
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
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This is the ScheduleAppointmentController class.
 * This class defines the methods used to schedule appointments and save them to the database.
 */
public class ScheduleAppointmentController implements Initializable {

    //Start times list at 3AM to account for 5-hour difference for Hawaii based appointments to start at 8AM EST
    private final LocalTime start = LocalTime.of(3,0);
    private final LocalTime end = LocalTime.of(23,0);

    //Data Access Objects
    private final ContactDAOImpl contactDAO = new ContactDAOImpl();
    private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();

    //Appointment data
    private Customer customerName;
    private User userName;
    private String apptTitle;
    private String description;
    private String location;
    private Contact contactName;
    private String apptType;
    private LocalDate apptDate;
    private LocalTime apptStart;
    private LocalTime apptEnd;
    private LocalDateTime apptStartDateTime;
    private LocalDateTime apptEndDateTime;

    //GUI Form Fields and Buttons
    @FXML
    private TextField apptIDTxt;
    @FXML
    private ComboBox<Customer> customerCB;
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
    private DatePicker apptCalendar;
    @FXML
    private ComboBox<LocalTime> apptStartTimeCB;
    @FXML
    private ComboBox<LocalTime> apptEndTimeCB;
    @FXML
    private Button saveApptBtn;
    @FXML
    private Button cancelScheduleApptBtn;

    private final ObservableList<String> apptTypes = FXCollections.observableArrayList();


    /** This is the ScheduleAppointmentController constructor.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ScheduleAppointmentController() throws SQLException {
    }

    /** This method initializes the Schedule Appointment screen.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerCB.setItems(customerDAO.getAllDBCustomers());
            customerCB.setPromptText("Select Customer");

            userNameCB.setItems(userDAO.getAllUsers());
            userNameCB.setPromptText("Select User");

            contactCB.setItems(contactDAO.getAllContactsFromDB());
            contactCB.setPromptText("Select Contact");

            apptTypes.addAll("Planning Session", "De-Briefing", "Department Meeting", "Escalation", "Review","Miscellaneous");
            apptTypeCB.setItems(apptTypes);
            apptTypeCB.setPromptText("Select Type");

            apptCalendar.setConverter(new StringConverter<>() {
                final String date = "MM-dd-yyyy";
                final DateTimeFormatter DTF = DateTimeFormatter.ofPattern(date);

                //Formats the localDate as a String with format MM-dd-yyyy
                @Override
                public String toString(LocalDate localDate) {
                    if (localDate != null) {
                        return DTF.format(localDate);
                    } else {
                        return "LocalDate value is Null.";
                    }
                }
                //Converts the String format of MM-dd-yyyy displayed back to LocalDate
                @Override
                public LocalDate fromString(String s) {
                    if (s != null && !s.isEmpty()) {
                        return LocalDate.parse(s, DTF);
                    }
                    return null;
                }});

            //Set the date picker
            apptCalendar.setValue(LocalDate.now());
            apptDate = apptCalendar.getValue();

            //Displays start and end times to combo boxes and sets the end time to 15 after initialized start time by default
            ConvertTime.displayValidTimes(apptStartTimeCB, start, end);
            ConvertTime.displayValidTimes(apptEndTimeCB, start, end);
            apptStartTimeCB.getSelectionModel().selectFirst();
            apptEndTimeCB.getSelectionModel().select(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

/** This method gets all data from the form input fields on the Schedule Appointment screen.
 */
    private void getApptData() {
        customerName = customerCB.getSelectionModel().getSelectedItem();
        userName = userNameCB.getSelectionModel().getSelectedItem();
        apptTitle = apptTitleTxt.getText().trim();
        description = apptDescriptionTxt.getText().trim();
        location = apptLocationTxt.getText().trim();
        contactName = contactCB.getSelectionModel().getSelectedItem();
        apptType = apptTypeCB.getSelectionModel().getSelectedItem();
        apptDate = apptCalendar.getValue();
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
        return ConvertTime.isApptWithinBusinessHrs(apptStartDateTime) && ConvertTime.isApptWithinBusinessHrs(apptEndDateTime);

    }

    /** Checks to see if the appointment data entered is valid and that there are no blank or null fields or invalid start/end times.
     * @return True if the appointment data is valid, false if there are blank fields or start/end times are invalid.
     */
    private boolean apptDataIsValid(){

        //Gets the appointment data
        getApptData();

        //Ensures no form fields are left blank/null
        if(customerName == null || userName == null || apptTitle.isBlank() || description.isBlank() || location.isBlank() || contactName == null || apptType == null || apptDate == null || apptStart == null || apptEnd == null){
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("All Fields Required");
            blankFields.setContentText("All fields are required and must not be left blank.\nPlease complete any blank/missing fields and try again.");
            blankFields.showAndWait();
            return false;
        }
        //Ensures that the start time is before and not equal to the end time.
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

        //If entered data is valid check for appointment overlap
        else {
            //Checks if there is any appointments that overlap and returns false if there are conflicting appointments.
            try {
                if(!apptCollision()){
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
    private boolean apptCollision() throws SQLException {
        try{
            //establish the appointment start and end times for comparison.
            apptStartDateTime = LocalDateTime.of(apptDate, apptStart);
            apptEndDateTime = LocalDateTime.of(apptDate, apptEnd);
            ObservableList<Appointment> appts = apptDAO.getAllApptsFromDB();

            //Loop through the appointments and check if there are any with conflicting start and end times.
            for(Appointment a : appts){
                if(a.getCustomerID() == customerName.getCustomerID()){

                    //Checks appointment start times for overlap.
                    if(apptStartDateTime.isAfter(a.getStartDateTime().minusMinutes(1)) && apptStartDateTime.isBefore(a.getEndDateTime())){
                        Alert apptCollision = new Alert(Alert.AlertType.ERROR);
                        apptCollision.setTitle("Appointment Conflict");
                        apptCollision.setContentText("This appointment's start/end times overlap with the following appointment:\n\n"
                                + "Appointment ID: " + a.getApptID() + "\nAppointment Title: " + a.getApptTitle() + "\nDescription: "
                                + a.getDescription() +"\nLocal Start Time:" + a.getStartTime() + "\nLocal End Time: " + a.getEndTime());
                        apptCollision.showAndWait();
                        return false;
                    }
                    //Checks appointment end times for overlap.
                    else if (apptEndDateTime.isAfter(a.getStartDateTime().minusMinutes(1)) && apptEndDateTime.isBefore(a.getEndDateTime())) {
                        Alert apptCollision = new Alert(Alert.AlertType.ERROR);
                        apptCollision.setTitle("Appointment Conflict");
                        apptCollision.setContentText("This appointment's start/end times overlap with the following appointment:\n\n"
                                + "Appointment ID: " + a.getApptID() + "\nAppointment Title: " + a.getApptTitle() + "\nDescription: "
                                + a.getDescription() +"\nLocal Start Time:" + a.getStartTime() + "\nLocal End Time: " + a.getEndTime());
                        apptCollision.showAndWait();
                        return false;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /** This method saves the new appointment to the database.
     * @param actionEvent When the Save button is activated on the Schedule Appointment screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveAppt(ActionEvent actionEvent) throws IOException{

        if(apptDataIsValid()) {

            //Add the appointment to the database
            try {
                apptDAO.addAppt(apptTitle, description, location, apptType, apptStartDateTime, apptEndDateTime, LocalDateTime.now(),
                        LoginScreenController.userName, LocalDateTime.now(), LoginScreenController.userName,
                        customerName.getCustomerID(), userName.getUserID(), contactName.getContactID());

                //Return to the Main Menu
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
                Stage stage = (Stage) (saveApptBtn.getScene().getWindow());
                stage.setTitle("Appointment Scheduler Main Menu");
                stage.setScene(new Scene(root, 1200, 700));
                stage.show();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** This method loads the Main Menu screen when scheduling an appointment is cancelled.
     * @param actionEvent When the Cancel button is activated on the Schedule Appointment Screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {

        //Cancel button alert
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel scheduling this appointment?");
        cancelAlert.setTitle("Cancel Scheduling Appointment");
        Optional<ButtonType> result = cancelAlert.showAndWait();

        //Return to the Main Menu
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
            Stage stage = (Stage) (cancelScheduleApptBtn.getScene().getWindow());
            stage.setTitle("Appointment+ Main Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
    }
}
