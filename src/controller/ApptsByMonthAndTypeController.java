package controller;

import utilities.MonthAndTypeData;
import utilities.MonthAndTypeReport;
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
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the ApptsByMonthAndTypeController class.
 * This class holds the methods used to gather appointment data by their Month and Types and displays them in a table view.
 */
public class ApptsByMonthAndTypeController implements Initializable {

    //Month and Type related fields
    private final ObservableList<MonthAndTypeData> apptData = FXCollections.observableArrayList();
    private final MonthAndTypeReport monthAndTypeReport = new MonthAndTypeReport();

    //GUI fx:id's
    @FXML
    private Button printReportBtn;
    @FXML
    private AnchorPane anchorPane;
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

    /** Gathers appointment data by their month and types and displays them in the reportsTable tableview.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Lists for each appointment type: "Planning Session", "De-Briefing", "Department Meeting", "Escalation", "Review","Miscellaneous"
        ObservableList<MonthAndTypeData> planningSessions = monthAndTypeReport.apptMonthAndTypeData("Planning Session");
        ObservableList<MonthAndTypeData> debriefings = monthAndTypeReport.apptMonthAndTypeData("De-Briefing");
        ObservableList<MonthAndTypeData> deptMeetings = monthAndTypeReport.apptMonthAndTypeData("Department Meeting");
        ObservableList<MonthAndTypeData> escalations = monthAndTypeReport.apptMonthAndTypeData("Escalation");
        ObservableList<MonthAndTypeData> reviews = monthAndTypeReport.apptMonthAndTypeData("Review");
        ObservableList<MonthAndTypeData> miscellaneousAppts = monthAndTypeReport.apptMonthAndTypeData("Miscellaneous");

        //Sets the table with the appointment data.
        apptsByMonthAndTypeTable.setItems(apptData);

        //Gets all the appointment types data
        getAllApptData(planningSessions);
        getAllApptData(debriefings);
        getAllApptData(deptMeetings);
        getAllApptData(escalations);
        getAllApptData(reviews);
        getAllApptData(miscellaneousAppts);

        //Sets up the table columns and sorts the data by Month in ascending order.
        apptMonthCol.setCellValueFactory(new PropertyValueFactory<>("apptMonth"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        numOfApptsCol.setCellValueFactory(new PropertyValueFactory<>("numOfAppts"));
        apptsByMonthAndTypeTable.getSelectionModel().selectFirst();
    }
    /** Gets all the appointment data to be added to the apptData list.
     * @param addAllApptTypes All appointment types to be added to the report.
     */
    private void getAllApptData(ObservableList<MonthAndTypeData> addAllApptTypes) {
        // add the appointment type lists
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
     */
    public void searchAppts(ActionEvent actionEvent) {

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
     */
    private ObservableList<MonthAndTypeData> searchByApptTypeOrMonth(String partialApptTypeOrMonth) {
        ObservableList<MonthAndTypeData> resultsSearch = FXCollections.observableArrayList();

        apptsByMonthAndTypeTable.setItems(apptData);
        ObservableList<MonthAndTypeData> allAppts = apptsByMonthAndTypeTable.getItems();

        for(MonthAndTypeData a : allAppts){
            if(a.getApptType().equalsIgnoreCase(partialApptTypeOrMonth) || a.getApptType().contains(partialApptTypeOrMonth)
                    || a.getApptType().toLowerCase().contains(partialApptTypeOrMonth.toLowerCase()) || a.getApptType().toUpperCase().contains(partialApptTypeOrMonth.toUpperCase())
                    || a.getApptMonth().contains(partialApptTypeOrMonth) || a.getApptMonth().equalsIgnoreCase(partialApptTypeOrMonth)
                    || a.getApptMonth().toLowerCase().contains(partialApptTypeOrMonth.toLowerCase()) || a.getApptMonth().toUpperCase().contains(partialApptTypeOrMonth.toUpperCase()))
                resultsSearch.add(a);
        }
        return resultsSearch;
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
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ApptsByMonthAndTypeReport.fxml")));
                Stage stage = (Stage) (printReportBtn.getScene().getWindow());
                stage.setTitle("Appointments By Month and Type");
                stage.setScene(new Scene(root,1200 ,700));
                stage.show();
                System.out.println("Report successfully printed!");
                job.endJob();
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
        //Reloads the screen even if printing gets cancelled.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ApptsByMonthAndTypeReport.fxml")));
        Stage stage = (Stage) (printReportBtn.getScene().getWindow());
        stage.setTitle("Appointments By Month and Type");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

}
