package controller;

import Utilities.MonthAndTypeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private ObservableList<Appointment> allApptData = FXCollections.observableArrayList();

    /** Gathers appointment data by their month and types and displays them in the reportsTable tableview.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
