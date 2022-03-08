package controller;

import DAO.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the ContactsReportController class.
 * This class defines the methods used to display the Contact Schedule GUI report.
 */
public class ContactsReportController implements Initializable {

    //GUI fx:id's
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button printReportBtn;
    @FXML
    private TextField apptSearchBar;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;
    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;
    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;
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
    private TableView<Appointment> contactsReportTable;

    private final AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
    private final ObservableList<Appointment> allAppts;



    /** This is the ContactsReportController constructor.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ContactsReportController() throws SQLException{
        allAppts = apptDAO.getAllApptsFromDB();
    }

    /** This method initializes the Contact Schedule screen and loads the appointments for contacts.
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

    /** Gets all the appointments from the database.
     * @throws SQLException Thrown if there is a database access error.
     */
    private void viewAllApptsFromDB() throws SQLException {

        //Sets up the apptsTable columns with all the Appointment object parameters
        contactsReportTable.setItems(allAppts);
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateFormatted"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeFormatted"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeFormatted"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactsReportTable.getSortOrder().add(contactNameCol);
        contactNameCol.setSortType(TableColumn.SortType.ASCENDING);
        contactsReportTable.sort();
        contactsReportTable.getSelectionModel().selectFirst();

    }

    /** This method searches the table for any appointments that have either an Appointment ID, Title, Type, or Contact Name that match the text input.
     * @param actionEvent When data is entered into the search bar on the main menu.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void searchAppts(ActionEvent actionEvent) throws SQLException {

        //Get the search text input
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
        contactsReportTable.setItems(appointments);

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
     @param partialApptTitle The text input entered into the search field above the table.
     @return resultsSearch The search results to be displayed in the table.
     @throws SQLException Thrown if there is a database access error.
     */
    private ObservableList<Appointment> searchByApptTitle(String partialApptTitle) throws SQLException {
        ObservableList<Appointment> resultsSearch = FXCollections.observableArrayList();

        try{
            viewAllApptsFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<Appointment> allAppts = contactsReportTable.getItems();

        for(Appointment a : allAppts){
            if(a.getApptTitle().equalsIgnoreCase(partialApptTitle) || a.getApptTitle().contains(partialApptTitle)
                    || a.getApptTitle().toLowerCase().contains(partialApptTitle) || a.getApptTitle().toUpperCase().contains(partialApptTitle)
                    || a.getApptType().equalsIgnoreCase(partialApptTitle) || a.getApptType().contains(partialApptTitle)
                    || a.getApptType().toLowerCase().contains(partialApptTitle) || a.getApptType().toUpperCase().contains(partialApptTitle)
                    || a.getContactName().toLowerCase().contains(partialApptTitle) || a.getContactName().toUpperCase().contains(partialApptTitle)
                    || a.getContactName().equalsIgnoreCase(partialApptTitle) || a.getContactName().contains(partialApptTitle))
                resultsSearch.add(a);
        }
        return resultsSearch;
    }

    /**Searches Appointments by id.
     @param apptID The Appointment id that is searched.
     @return a, the appointment that matches the searched id.
     */
    private Appointment searchByApptID(int apptID) {
        ObservableList<Appointment> allAppts = contactsReportTable.getItems();
        for (Appointment a : allAppts) {
            if (a.getApptID() == apptID) {
                return a;
            }
        }
        return null;
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

    /** This method prints the node passed into it and any nodes that are attached.
     * If the image to print is too large, it is resized to fit when printed.
     * @param node The node to be printed.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    private void printNode(Node node) throws IOException {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.getDefaultPageLayout();

        //Printable area
        double printableWidth = pageLayout.getPrintableWidth();
        double printableHeight = pageLayout.getPrintableHeight();

        //Node's dimensions
        double nodeWidth = node.getBoundsInParent().getWidth();
        double nodeHeight = node.getBoundsInParent().getHeight();

        //Determines whether the image is larger than the printable width/height.
        double widthRemaining = printableWidth - nodeWidth;
        double heightRemaining = printableHeight - nodeHeight;

        //Scale the node to fit the printable width and height
        double scale;

        if (widthRemaining < heightRemaining) scale = printableWidth / nodeWidth;
        else scale = printableHeight / nodeHeight;

        //Scales the same for width and height
        node.getTransforms().add(new Scale(scale, scale));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {

            boolean success = job.printPage(node);
            //If job prints successfully, reloads the screen back to full size.
            if (success) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ApptsByContactReport.fxml")));
                Stage stage = (Stage) (printReportBtn.getScene().getWindow());
                stage.setTitle("Contact Schedule");
                stage.setScene(new Scene(root,1200 ,700));
                stage.show();
                System.out.println("Report successfully printed!");
                job.endJob();
            }
            else {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ApptsByContactReport.fxml")));
                Stage stage = (Stage) (printReportBtn.getScene().getWindow());
                stage.setTitle("Contact Schedule");
                stage.setScene(new Scene(root,1200 ,700));
                stage.show();
                System.out.println("Printing was cancelled.");
            }
        }
    }

    /** This method prints the report screen when the Print Report button is activated.
     * @param actionEvent When the printReportBtn is activated.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void printReport(ActionEvent actionEvent) throws IOException {
        //Prints the node/screen
        printNode(anchorPane);
        //Reloads the screen even if print job gets cancelled
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ApptsByContactReport.fxml")));
        Stage stage = (Stage) (printReportBtn.getScene().getWindow());
        stage.setTitle("Contact Schedule");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
