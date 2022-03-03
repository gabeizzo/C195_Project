package controller;

import DAO.AppointmentDAOImpl;
import Utilities.MonthAndTypeData;
import Utilities.MonthAndTypeReport;
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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the ApptsByMonthAndTypeController class.
 * This class holds the methods used to gather appointment data by their Month and Types and displays them in a table view.
 */
public class ApptsByMonthAndTypeController implements Initializable {
    private final ObservableList<MonthAndTypeData> apptData = FXCollections.observableArrayList();
    private final MonthAndTypeReport monthAndTypeReport = new MonthAndTypeReport();

    //GUI fx:id's
    @FXML
    private TextField apptSearchBar;
    @FXML
    private TableColumn<String, String> apptMonthCol;
    @FXML
    private TableColumn<?, ?> apptTypeCol;
    @FXML
    private TableColumn<Appointment, Integer> numOfApptsCol;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private TableView<MonthAndTypeData> apptsByMonthAndTypeTable;



    /** This is the ApptsByMonthAndTypeController constructor for initializing objects of this type.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ApptsByMonthAndTypeController() throws SQLException {
    }

    /** Gathers appointment data by their month and types and displays them in the reportsTable tableview.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Lists for each appointment type: "Planning Session", "De-Briefing", "Department Meeting", "Escalation", "Review","Miscellaneous"
        ObservableList<MonthAndTypeData> planningSessions = monthAndTypeReport.apptMonthAndTypeData("Planning Session");
        ObservableList<MonthAndTypeData> debriefings = monthAndTypeReport.apptMonthAndTypeData("De-Briefing");
        ObservableList<MonthAndTypeData> deptMeetings = monthAndTypeReport.apptMonthAndTypeData("Department Meeting");
        ObservableList<MonthAndTypeData> escalations = monthAndTypeReport.apptMonthAndTypeData("Escalation");
        ObservableList<MonthAndTypeData> reviews = monthAndTypeReport.apptMonthAndTypeData("Review");
        ObservableList<MonthAndTypeData> miscellaneousAppts = monthAndTypeReport.apptMonthAndTypeData("Miscellaneous");

        apptsByMonthAndTypeTable.setItems(apptData);

        getAllApptData(planningSessions);
        getAllApptData(debriefings);
        getAllApptData(deptMeetings);
        getAllApptData(escalations);
        getAllApptData(reviews);
        getAllApptData(miscellaneousAppts);

        apptMonthCol.setCellValueFactory(new PropertyValueFactory<>("apptMonth"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        numOfApptsCol.setCellValueFactory(new PropertyValueFactory<>("numOfAppts"));
        apptMonthCol.setSortType(TableColumn.SortType.ASCENDING);
        apptsByMonthAndTypeTable.sort();
        apptsByMonthAndTypeTable.getSelectionModel().selectFirst();


    }
    private void getAllApptData(ObservableList<MonthAndTypeData> addAllApptTypes) {
        // add each appointment to overall list
        apptData.addAll(addAllApptTypes);
    }

    /** This method returns users back to the Main Menu when the Main Menu button is selected.
     * @param actionEvent When the Main Menu button on the Appointment By Month and Type screen is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        Stage stage = (Stage) (mainMenuBtn.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** This method searches the appointment table for any appointments that have either an Appointment ID or Title that match the text input.
     * @param actionEvent When data is entered into the search bar on the main menu.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void searchAppts(ActionEvent actionEvent) throws SQLException {

        //Get the search text input
        String searchInput = apptSearchBar.getText();
        ObservableList<MonthAndTypeData> appointments = searchByApptTypeOrMonth(searchInput);

        apptsByMonthAndTypeTable.setItems(appointments);

        //If blank search, display all appointments to the table
        if(apptSearchBar.getText().isBlank()){
            apptsByMonthAndTypeTable.setItems(apptData);
        }

        //If there are no matches and the list is empty display message.
        if(appointments.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Results");
            alert.setContentText("""
                    No appointments found.
                    Please check spelling and try again.""");
            alert.showAndWait();

            //Clear the text field and display all appointments
            apptSearchBar.clear();
            apptsByMonthAndTypeTable.setItems(apptData);
        }
    }
    /** Searches for Appointments by Title.
     @param partialApptTypeOrMonth The text input entered into the search field above the table.
     @return resultsSearch The search results to be displayed in the table.
     @throws SQLException Thrown if there is a database access error.
     */
    private ObservableList<MonthAndTypeData> searchByApptTypeOrMonth(String partialApptTypeOrMonth) throws SQLException {
        ObservableList<MonthAndTypeData> resultsSearch = FXCollections.observableArrayList();

        apptsByMonthAndTypeTable.setItems(apptData);
        ObservableList<MonthAndTypeData> allAppts = apptsByMonthAndTypeTable.getItems();

        for(MonthAndTypeData a : allAppts){
            if(a.getApptType().equalsIgnoreCase(partialApptTypeOrMonth) || a.getApptType().contains(partialApptTypeOrMonth)
                    || a.getApptType().toLowerCase().contains(partialApptTypeOrMonth) || a.getApptType().toUpperCase().contains(partialApptTypeOrMonth)
                    || a.getApptMonth().contains(partialApptTypeOrMonth) || a.getApptMonth().equalsIgnoreCase(partialApptTypeOrMonth)
                    || a.getApptMonth().toLowerCase().contains(partialApptTypeOrMonth) || a.getApptMonth().toUpperCase().contains(partialApptTypeOrMonth))
                resultsSearch.add(a);
        }
        return resultsSearch;
    }
}
