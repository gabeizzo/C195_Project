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

/** This is the ScheduleAppointmentController class.
 * This class defines the methods used to schedule appointments and save them to the database.
 */
public class ScheduleAppointmentController implements Initializable {
    private LocalTime start = LocalTime.of(5,0);
    private LocalTime end = LocalTime.of(23,0);
    private ContactDAOImpl contactDAO = new ContactDAOImpl();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    private ObservableList<String> apptTypes = FXCollections.observableArrayList();
    private ObservableList<Appointment> appts = FXCollections.observableArrayList();
    private Customer customerName;
    private User userName;
    private String apptTitle;
    private String description;
    private String location;
    private Contact contact;
    private String apptType;
    private LocalDate apptDate;
    private LocalTime apptStart;
    private LocalTime apptEnd;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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
    private DatePicker apptDatePicker;
    @FXML
    private ComboBox<LocalTime> apptStartTimeCB;
    @FXML
    private ComboBox<LocalTime> apptEndTimeCB;
    @FXML
    private Button saveApptBtn;
    @FXML
    private Button cancelScheduleApptBtn;

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
            userNameCB.setItems(userDAO.getAllUsers());
            contactCB.setItems(contactDAO.getAllContactsFromDB());
            apptTypes.addAll("Planning Session", "De-Briefing", "Department Meeting", "Escalation", "Review","Miscellaneous");
            apptTypeCB.setItems(apptTypes);
            apptDatePicker.setConverter(new StringConverter<LocalDate>() {
                String date = "MM-dd-yyyy";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(date);

                @Override
                public String toString(LocalDate localDate) {
                    if(localDate != null) {
                        return dateTimeFormatter.format(localDate);
                    }
                    else {
                        return " ";
                    }
                }
                @Override
                public LocalDate fromString(String s) {
                    if(s != null && !s.isEmpty()){
                        return LocalDate.parse(s, dateTimeFormatter);
                    }
                    return null;
                }
            });

            apptDatePicker.setValue(LocalDate.now());
            apptDate = apptDatePicker.getValue();

            ConvertTime.displayValidTimes(apptStartTimeCB, start, end);
            ConvertTime.displayValidTimes(apptEndTimeCB, start, end);
            apptStartTimeCB.getSelectionModel().selectFirst();
            apptEndTimeCB.getSelectionModel().select(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Gets all data from the input fields on the Schedule Appointment screen.
     */
    private void getApptData() {
        customerName = customerCB.getSelectionModel().getSelectedItem();
        userName = userNameCB.getSelectionModel().getSelectedItem();
        apptTitle = apptTitleTxt.getText().trim();
        description = apptDescriptionTxt.getText().trim();
        location = apptLocationTxt.getText().trim();
        contact = contactCB.getSelectionModel().getSelectedItem();
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
        if(customerName == null || userName == null || apptTitle.isBlank() || description.isBlank() || location.isBlank() || contact == null || apptType == null || apptDate == null || apptStart == null || apptEnd == null){
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
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("Invalid Start/End Times");
            blankFields.setContentText("Appointment start and end times must be between 08:00AM-10:00PM(EST).\nAppointment start time(EST): "
                    + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptStart)))
                    + "\nAppointment end time(EST): " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.of(apptDate, apptEnd)))
                    + "\nLocal time: " + ConvertTime.timeFormatted(LocalTime.now())
                    + "\nLocal time in EST: " + ConvertTime.timeFormatted(ConvertTime.localToEST(LocalDateTime.now())));
            blankFields.showAndWait();
            return false;
        }
        else {
            //Checks if there is any appointments that overlap and returns false if there are conflicting appointments.
            try {
                if(!apptOverlap()){
                    return false;
                }
                else {
                    startTime = LocalDateTime.of(apptDate, apptStart);
                    endTime = LocalDateTime.of(apptDate, apptEnd);
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
            startTime = LocalDateTime.of(apptDate, apptStart);
            endTime = LocalDateTime.of(apptDate, apptEnd);
            appts = apptDAO.getAllApptsFromDB();

            //Loop through the appointments and check if there are any with conflicting start and end times.
            for(Appointment a : appts){
                if(a.getCustomerID() == customerName.getCustomerID()){

                    //Checks appointment start times for overlap.
                    if(startTime.isAfter(a.getStartDateTime().minusMinutes(1)) && startTime.isBefore(a.getEndDateTime())){
                        Alert apptCollision = new Alert(Alert.AlertType.ERROR);
                        apptCollision.setTitle("Appointment Conflict");
                        apptCollision.setContentText("This appointment's start/end times overlap with the following appointment:\n\n"
                                + "Appointment ID: " + a.getApptID() + "\nAppointment Title: " + a.getApptTitle() + "\nDescription: "
                                + a.getDescription() +"\nStart Time:" + a.getStartTime() + "\nEnd Time: " + a.getEndTime());
                        apptCollision.showAndWait();
                        return false;
                    }
                    //Checks appointment end times for overlap.
                    else if (endTime.isAfter(a.getStartDateTime().minusMinutes(1)) && endTime.isBefore(a.getEndDateTime())) {
                        Alert apptCollision = new Alert(Alert.AlertType.ERROR);
                        apptCollision.setTitle("Appointment Conflict");
                        apptCollision.setContentText("This appointment's start/end times overlap with the following appointment:\n\n"
                                + "Appointment ID: " + a.getApptID() + "\nAppointment Title: " + a.getApptTitle() + "\nDescription: "
                                + a.getDescription() +"\nStart Time:" + a.getStartTime() + "\nEnd Time: " + a.getEndTime());
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

            try {
                apptDAO.addAppt(apptTitle, description, location, apptType, startTime, endTime, LocalDateTime.now(),
                        LoginScreenController.userName, LocalDateTime.now(), LoginScreenController.userName,
                        customerName.getCustomerID(), userName.getUserID(), contact.getContactID());

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
                Stage stage = (Stage) (saveApptBtn.getScene().getWindow());
                stage.setTitle("Appointment Scheduler Main Menu");
                stage.setScene(new Scene(root, 1200, 700));
                stage.show();

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    /** This method loads the Main Menu screen when scheduling an appointment is cancelled.
     * @param actionEvent When the Cancel button is activated on the Schedule Appointment Screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {

        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel scheduling this appointment?");
        cancelAlert.setTitle("Cancel Scheduling Appointment");
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
