package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLvlDivision;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the ModifyCustomerController class.
 * This class defines the methods used to modify customer data.
 */
public class ModifyCustomerController implements Initializable {
    @FXML
    private TextField customerIDTxt;
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField customerPhoneTxt;
    @FXML
    private TextField customerStreetAddressTxt;
    @FXML
    private TextField customerCityTxt;
    @FXML
    private TextField customerPostalCodeTxt;
    @FXML
    private ComboBox<Country> customerCountryCB;
    @FXML
    private ComboBox<FirstLvlDivision> customerDivisionIDCB;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Button cancelModifyCustomerBtn;

    /**
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *
     * @param actionEvent
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveModifiedCustomer(ActionEvent actionEvent) throws IOException{
        //if else here with exceptions and try catch block
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        Stage stage = (Stage) (saveCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }

    /** This method returns to the Customers Menu if the Cancel button is selected on the Modify Customer screen.
     * @param actionEvent When the Cancel button on the Modify Customer screen is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toCustomersMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
        Stage stage = (Stage) (cancelModifyCustomerBtn.getScene().getWindow());
        stage.setTitle("Customers Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
}
