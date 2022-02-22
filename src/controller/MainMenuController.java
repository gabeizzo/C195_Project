
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** This is the controller for the Main Menu screen.
 * This class is responsible for providing the functionality to the Main Menu and all labels and buttons users interact with.
 */
public class MainMenuController implements Initializable {
    public static Appointment apptToModify;
    private AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    @FXML
    private TextField apptSearchBar;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label welcomeLbl;
    @FXML
    private Label timeZoneID;
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

    //Observable Lists used to populate the appointments table
    private ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private  ObservableList<Appointment> currWeekAppts = FXCollections.observableArrayList();
    private  ObservableList<Appointment> currMonthAppts = FXCollections.observableArrayList();


    /**
     *
     * @throws SQLException
     */
    public MainMenuController() throws SQLException{
        allAppts = apptDAO.getAllApptsFromDB();
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localTimezone = ZoneId.of(TimeZone.getDefault().getID());
        timeZoneID.setText(localTimezone.toString());

        apptsViewToggle = new ToggleGroup();
        allApptsRadioBtn.setToggleGroup(apptsViewToggle);
        currentMonthRadioBtn.setToggleGroup(apptsViewToggle);
        currentWeekRadioBtn.setToggleGroup(apptsViewToggle);
        allApptsRadioBtn.setSelected(true);

        try {
            viewAllAppts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method deletes a selected appointment from the database and updates the apptsTable.
     * @param actionEvent When the Delete Appointment button is clicked.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void deleteAppointment(ActionEvent actionEvent) throws IOException{

    }

    /** This method changes the stage to the Schedule Appointment screen.
     * @param actionEvent When the Schedule Appointment button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toScheduleAppointment(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ScheduleAppointment.fxml")));
        Stage stage = (Stage) (scheduleApptButton.getScene().getWindow());
        stage.setTitle("Schedule Appointment");
        stage.setScene(new Scene(root,400 ,700));
        stage.show();
    }

    /** This method changes the stage to display the Modify Appointments screen.
     * @param actionEvent When the Modify Appointment button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toModifyAppointment(ActionEvent actionEvent) throws IOException{
        apptToModify = apptsTable.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyAppointment.fxml")));
        Stage stage = (Stage) (modifyApptButton.getScene().getWindow());
        stage.setTitle("Modify Appointment");
        stage.setScene(new Scene(root,400 ,700));
        stage.show();
    }

    /** This method changes the stage to display the Customers Menu.
     * @param actionEvent When the View/Update Customers button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toCustomersMenu(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        Stage stage = (Stage) (customersMenuButton.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();

    }

    /** This method changes the stage to display the Appointments by Month and Type screen.
     * @param actionEvent When the Appointments By Month and Type button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toApptsByMonthAndType(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ApptsByMonthAndType.fxml")));
        Stage stage = (Stage) (reportsByMonthAndTypeButton.getScene().getWindow());
        stage.setTitle("Reports By Month and Type");
        stage.setScene(new Scene(root,600 ,500));
        stage.show();
    }

    /** This method changes the stage to display the Contact Schedule screen.
     * @param actionEvent When the Contact Schedule button is clicked.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toContactSchedule(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ContactSchedule.fxml")));
        Stage stage = (Stage) (contactScheduleButton.getScene().getWindow());
        stage.setTitle("Contact Schedule");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** This method changes the stage to the User Schedule screen.
     * @param actionEvent When the User Schedule button is clicked on.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toUserSchedule(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/UserSchedule.fxml")));
        Stage stage = (Stage) (userScheduleButton.getScene().getWindow());
        stage.setTitle("User Schedule");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** This method is called when the View Current Month's Appointments Radio button is selected.
     * This method runs through the list of all appointments and adds any appointments that start in the current month to a new Obs.List.
     * If there are no appointments starting this month, a dialog box displays letting the user know.
     * @param actionEvent When the user selects the currMonthRadioBtn.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void viewCurrentMonthAppts(ActionEvent actionEvent) throws IOException{
        Month currMonth = LocalDate.now().getMonth();
        int currYear = LocalDate.now().getYear();

        try {
            // Re-initializes the currMonthAppts ObservableList.
            if (!currMonthAppts.isEmpty()) {
                currMonthAppts.clear();
            }

            // Checks to see if there are any appointments in the current month.
            for (Appointment appt : allAppts) {
                if (appt.getStartDateTime().getMonth().equals(currMonth) && appt.getStartDateTime().getYear() == currYear) {
                    currMonthAppts.add(appt);
                }
            }
            // Sets the apptsTable with the current month's appointments.
            apptsTable.setItems(currMonthAppts);
            apptsTable.getSelectionModel().selectFirst();

            // If currMonthAppts list is empty, display a dialog box.
            if (currMonthAppts.isEmpty()) {
                Alert noAppts = new Alert(Alert.AlertType.INFORMATION);
                noAppts.setTitle("No Appointments");
                noAppts.setContentText("Currently, there are no appointments for the month of " + currMonth + ".");
                noAppts.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** This method is called when the View All Appointments Radio Button is selected.
     * viewAllAppts populates the apptsTable with all appointments from the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    private void viewAllAppts() throws SQLException {
        apptsTable.setItems(allAppts);
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startDateFormatted"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalTime>("startTimeFormatted"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalTime>("endTimeFormatted"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptsTable.getSelectionModel().selectFirst();

    }

    /** This method displays all appointments from the database into the apptsTable.
     * @param actionEvent When the allApptsRadioBtn is selected.
     */
    public void onActionViewAllAppts(ActionEvent actionEvent){
        try{
            viewAllAppts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method displays the current week's appointments in the database to the apptsTable.
     * @param actionEvent When the currWeeksRadioBtn is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void viewCurrentWeekAppts(ActionEvent actionEvent) throws IOException{

    }

    public void searchAppts(ActionEvent actionEvent) throws SQLException {

        String searchInput = apptSearchBar.getText();

        ObservableList<Appointment> appointments = searchByApptTitle(searchInput);
        try {
            int apptID = Integer.parseInt(searchInput);
            Appointment a = searchByApptID(apptID);
            if(a != null){
                appointments.add(a);
            }
        } catch (NumberFormatException e) {
            //ignore
        }
        apptsTable.setItems(appointments);

        if(apptSearchBar.getText().isBlank()){
            try{
                viewAllAppts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(appointments.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Results");
            alert.setContentText("No appointments found with the entered ID or Title.");
            alert.showAndWait();

            apptSearchBar.clear();
            try{
                viewAllAppts();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    /** Searches for Appointments by Title.
     @param partialApptTitle The text input entered into the search field above the Parts table.
     @return resultsSearch The search results to be displayed in the Parts table.
     @throws SQLException Thrown if there is a database access error.
     */
    private ObservableList<Appointment> searchByApptTitle(String partialApptTitle) throws SQLException {
        ObservableList<Appointment> resultsSearch = FXCollections.observableArrayList();

        try{
            viewAllAppts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<Appointment> allAppts = apptsTable.getItems();

        for(Appointment a : allAppts){
            if(a.getApptTitle().contains(partialApptTitle))
                resultsSearch.add(a);
        }
        return resultsSearch;
    }

    /**Searches Appointments by id.
     @param apptID The Appointment id that is searched.
     @return a, the appointment that matches the searched id.
     @throws SQLException Thrown if there is a database error.
     */
    private Appointment searchByApptID(int apptID) throws SQLException {
        ObservableList<Appointment> allAppts = apptsTable.getItems();
        for (Appointment a : allAppts) {
            if (a.getApptID() == apptID) {
                return a;
            }
        }
        return null;
    }

    /** This method closes the application if the user clicks on the Exit button and chooses the OK button option.
     * @param actionEvent When the Exit button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void exitApplication(ActionEvent actionEvent) throws IOException{
        Alert alertEnglish = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
        alertEnglish.setTitle("Exit Application");

        Optional<ButtonType> result = alertEnglish.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
