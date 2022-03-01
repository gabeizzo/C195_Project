package controller;

import DAO.CountryDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.DivisionDAOImpl;
import Utilities.DivisionsByCountryID;
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
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
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
    private TextField customerAddressTxt;
    @FXML
    private TextField customerPostalCodeTxt;
    @FXML
    private ComboBox<Country> customerCountryCB;
    @FXML
    private ComboBox<Division> customerDivisionCB;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Button cancelModifyCustomerBtn;

    //Provides the customer to be modified and ability to call the methods needed from the DAOImpl's.
    private Customer modifyCustomer = CustomersMenuController.modifyCustomer;
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private DivisionDAOImpl fldDAO = new DivisionDAOImpl();
    private CountryDAOImpl countryDAO = new CountryDAOImpl();

    //Used for country and division filtering and populating the combo boxes
    private DivisionsByCountryID divisionsByCountry = new DivisionsByCountryID();
    private ObservableList<Division> countryDivisions = FXCollections.observableArrayList();
    private final ObservableList<Country> allCountries = countryDAO.getAllCountriesFromDB();

    //Customer data
    private String customerName;
    private String phone;
    private String postalCode;
    private String address;
    private Country country;
    private Division division;

    /** ModifyCustomerController constructor.
     * @throws SQLException Thrown if there is a database access error.
     */
    public ModifyCustomerController() throws SQLException {
    }

    /** This method initializes the Modify Customer screen and gets the selected customer's data and populates the fields.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populates the fields with the selected customer's previously entered data.
        try {
            customerIDTxt.setText(String.valueOf(modifyCustomer.getCustomerID()));
            customerNameTxt.setText(modifyCustomer.getCustomerName());
            customerPhoneTxt.setText(modifyCustomer.getPhone());
            customerAddressTxt.setText(modifyCustomer.getAddress());
            customerPostalCodeTxt.setText(modifyCustomer.getPostalCode());
            customerCountryCB.setItems(allCountries);

        /* If division ID is between 1 and 54, sets Country combo box to U.S.(database index: 0).
        Necessary due to Customer not having a Country parameter or Country getters and setters.*/
            if(modifyCustomer.getDivisionID() >= 1 && modifyCustomer.getDivisionID() <= 54){
            customerCountryCB.getSelectionModel().select(0);
        }
        //If division ID is between 60 and 72, sets the Country combo box to UK(database index: 1).
        else if (modifyCustomer.getDivisionID() >= 101 && modifyCustomer.getDivisionID() <=104) {
            customerCountryCB.getSelectionModel().select(1);
        }
        //If division is not between 1 and 72, sets the Country combo box to Canada(database index: 2).
        else {
            customerCountryCB.getSelectionModel().select(2);
        }
            Country country = customerCountryCB.getSelectionModel().getSelectedItem();
            countryDivisions = divisionsByCountry.divisionsByCountryID(country.getCountryID());
            customerDivisionCB.setItems(countryDivisions);

            Division customerFLD = getDivision(modifyCustomer.getDivisionID());
            customerDivisionCB.getSelectionModel().select(customerFLD);
        }
        catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    private Division getDivision(int divisionID) throws SQLException{
        return fldDAO.getFLDByID(divisionID);
    }

    private void getCustomerData(){
        customerName = customerNameTxt.getText().trim();
        phone = customerPhoneTxt.getText().trim();
        address = customerAddressTxt.getText().trim();
        postalCode = customerPostalCodeTxt.getText().trim();
        country = customerCountryCB.getSelectionModel().getSelectedItem();
        division = customerDivisionCB.getSelectionModel().getSelectedItem();
    }

    private boolean validateCustomerData() {

        //Gets the customer data to be validated
        getCustomerData();

        // If any fields are blank displays an error message.
        if(customerName.isBlank() || phone.isBlank() || address.isBlank() || postalCode.isBlank()) {
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("All Fields Required");
            blankFields.setContentText("All fields are required and must not be left blank.\nPlease complete any blank/missing fields and try again.");
            blankFields.showAndWait();
            return false;
        }
        // If U.S is selected, postal codes must be 5 characters in length.
        else if((country.getCountryID() == 1) && postalCode.length() != 5) {
            Alert postalCodeError = new Alert(Alert.AlertType.ERROR);
            postalCodeError.setTitle("U.S Postal Code Error");
            postalCodeError.setContentText("Valid U.S postal codes should be 5 characters in length.");
            postalCodeError.showAndWait();
            return false;
        }
        // If Canada is selected, postal codes must be 6-7 characters in length.
        else if(country.getCountryID() == 3 && (postalCode.length() < 6 || postalCode.length() > 7)){
            Alert postalCodeError = new Alert(Alert.AlertType.ERROR);
            postalCodeError.setTitle("Canada Postal Code Error");
            postalCodeError.setContentText("Valid Canada postal codes should be 6-7 characters in length(including a space).");
            postalCodeError.showAndWait();
            return false;
        }
        // If UK is selected, postal codes must be 6-8 characters in length.
        else if(country.getCountryID() == 2 && (postalCode.length() < 6 || postalCode.length() > 8)){
            Alert postalCodeError = new Alert(Alert.AlertType.ERROR);
            postalCodeError.setTitle("UK Postal Code Error");
            postalCodeError.setContentText("Valid UK postal codes should be 6-8 characters in length(including a space).");
            postalCodeError.showAndWait();
            return false;
        }

        return true;
    }

    /** This method populates the first level divisions into the combo box depending on which country is selected.
     * @param actionEvent When there are any changes made to the country combo box.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @FXML
    private void populateDivisions(ActionEvent actionEvent) throws SQLException {
        Country country = customerCountryCB.getSelectionModel().getSelectedItem();
        countryDivisions = divisionsByCountry.divisionsByCountryID(country.getCountryID());
        customerDivisionCB.setItems(countryDivisions);
        customerDivisionCB.getSelectionModel().selectFirst();
    }

    /** This method saves the modified customer to the customers list.
     * @param actionEvent When the Save button is activated on the Modify Customer screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void saveModifiedCustomer(ActionEvent actionEvent) throws IOException, SQLException {

        if(validateCustomerData()) {
            customerDAO.modifyCustomer(modifyCustomer.getCustomerID(), customerName, address, postalCode, phone, division.getDivisionID(), LoginScreenController.userName);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
            Stage stage = (Stage) (saveCustomerBtn.getScene().getWindow());
            stage.setTitle("Customers Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
    }

    /** This method returns to the Customers Menu if the Cancel button is selected on the Modify Customer screen.
     * @param actionEvent When the Cancel button on the Modify Customer screen is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void toCustomersMenu(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel modifying this customer?");
        cancelAlert.setTitle("Cancel Modify Customer");
        Optional<ButtonType> result = cancelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
            Stage stage = (Stage) (cancelModifyCustomerBtn.getScene().getWindow());
            stage.setTitle("Customers Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
    }
}
