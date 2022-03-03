package controller;

import DAO.AppointmentDAOImpl;
import DAO.UserDAOImpl;
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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserScheduleController implements Initializable {
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    private final ObservableList<Appointment> allAppts;

    @FXML
    private TextField apptSearchBar;
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
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public UserScheduleController() throws SQLException {
        allAppts = apptDAO.getAllApptsFromDB();
    }

    /** This method initializes the User Schedule screen.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            viewAllApptsFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAllApptsFromDB() throws SQLException {
        // Set up the sorted table view in ascending order by User Name
        userScheduleTable.setItems(allAppts);
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateFormatted"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeFormatted"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeFormatted"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userScheduleTable.getSortOrder().add(userNameCol);
        userNameCol.setSortType(TableColumn.SortType.ASCENDING);
        userScheduleTable.sort();
        userScheduleTable.getSelectionModel().selectFirst();


    }

    /** This method searches the table for any appointments that have either an Appointment ID, Title, Type, or Contact Name that match the text input.
     * @param actionEvent When data is entered into the search bar on the main menu.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void searchAppts(ActionEvent actionEvent) throws SQLException {

        //Get the search text input
        String searchInput = apptSearchBar.getText();
        ObservableList<Appointment> appointments = searchByApptInfo(searchInput);

        try {
            int apptID = Integer.parseInt(searchInput);
            Appointment a = searchByApptID(apptID);
            if(a != null){
                appointments.add(a);
            }
        } catch (NumberFormatException e) {
            //ignore
        }
        userScheduleTable.setItems(appointments);

        //If blank search, display all appointments to the table
        if(apptSearchBar.getText().isBlank()){
            try{
                viewAllApptsFromDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            try{
                viewAllApptsFromDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /** Searches for Appointments by Title.
     @param partialApptInfo The text input entered into the search field above the table.
     @return resultsSearch The search results to be displayed in the table.
     @throws SQLException Thrown if there is a database access error.
     */
    private ObservableList<Appointment> searchByApptInfo(String partialApptInfo) throws SQLException {
        ObservableList<Appointment> resultsSearch = FXCollections.observableArrayList();

        try{
            viewAllApptsFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<Appointment> allAppts = userScheduleTable.getItems();

        for(Appointment a : allAppts){
            if(a.getApptTitle().equalsIgnoreCase(partialApptInfo) || a.getApptTitle().contains(partialApptInfo)
                    || a.getApptTitle().toLowerCase().contains(partialApptInfo) || a.getApptTitle().toUpperCase().contains(partialApptInfo)
                    || a.getApptType().equalsIgnoreCase(partialApptInfo) || a.getApptType().contains(partialApptInfo)
                    || a.getApptType().toLowerCase().contains(partialApptInfo) || a.getApptType().toUpperCase().contains(partialApptInfo)
                    || a.getContactName().toLowerCase().contains(partialApptInfo) || a.getContactName().toUpperCase().contains(partialApptInfo)
                    || a.getContactName().equalsIgnoreCase(partialApptInfo) || a.getContactName().contains(partialApptInfo)
                    )
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
        ObservableList<Appointment> allAppts = userScheduleTable.getItems();
        for (Appointment a : allAppts) {
            if (a.getApptID() == apptID) {
                return a;
            }
        }
        return null;
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
