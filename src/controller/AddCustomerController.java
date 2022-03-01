package controller;

import DAO.CountryDAOImpl;
import DAO.CustomerDAOImpl;
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
import model.Division;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the AddCustomerController class.
 * This class defines the methods used to add customers to the database.
 */
public class AddCustomerController implements Initializable {

    //Customer data to be collected
    private String customerName;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String state;
    private String address;
    private Country country;
    private Division division;

    //Enables use of methods from the DAO's and division filtering
    private final CountryDAOImpl countryDAO = new CountryDAOImpl();
    private final CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private final DivisionsByCountryID divisionsByCountry = new DivisionsByCountryID();

    //Countries and divisions lists for the combo boxes
    private ObservableList<Division> countryDivisions = FXCollections.observableArrayList();
    private final ObservableList<Country> allCountries = countryDAO.getAllCountriesFromDB();

    @FXML
    private TextField customerIDTxt;
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField customerPhoneTxt;
    @FXML
    private TextField customerStreetTxt;
    @FXML
    private TextField customerCityTxt;
    @FXML
    private TextField customerStateTxt;
    @FXML
    private TextField customerPostalCodeTxt;
    @FXML
    private ComboBox<Country> customerCountryCB;
    @FXML
    private ComboBox<Division> customerDivisionCB;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Button cancelAddCustomerBtn;


    /** This is the AddCustomerController constructor which initializes objects of this type.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public AddCustomerController() throws SQLException {
    }

    /** This method initializes the Add Customer screen and sets up the Country and Division combo boxes.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            //Populates the country combo box and shows the first country in the list(U.S)
            customerCountryCB.setItems(allCountries);
            customerCountryCB.getSelectionModel().selectFirst();

            //Populates the First Level Division combo box with divisions based on the selected country's ID.
            Country country = customerCountryCB.getSelectionModel().getSelectedItem();
            countryDivisions = divisionsByCountry.divisionsByCountryID(country.getCountryID());
            customerDivisionCB.setItems(countryDivisions);
            customerDivisionCB.getSelectionModel().selectFirst();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method gets all customer data entered into the Add Customer form.
     */
    private void getCustomerData() {
        customerName = customerNameTxt.getText().trim();
        phone = customerPhoneTxt.getText().trim();
        street = customerStreetTxt.getText().trim();
        city = customerCityTxt.getText().trim();
        postalCode = customerPostalCodeTxt.getText().trim();
        state = customerStateTxt.getText().toUpperCase().trim();
        country = customerCountryCB.getSelectionModel().getSelectedItem();
        division = customerDivisionCB.getSelectionModel().getSelectedItem();
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

    /** This method validates the customer data entered into the Add Customer form.
     * @param actionEvent When the Save button is activated on the Add Customer screen.
     * @return Returns true if customer data is complete, otherwise returns false.
     */
    private boolean validateCustomerData(ActionEvent actionEvent) {

        //Gets the customer data to be validated
        getCustomerData();

        // If any fields are blank displays an error message.
        if(customerName.isBlank() || phone.isBlank() || street.isBlank() || city.isBlank() || postalCode.isBlank() || state.isBlank()) {
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("All Fields Required");
            blankFields.setContentText("All fields are required and must not be left blank.\nPlease complete any blank/missing fields and try again.");
            blankFields.showAndWait();
            return false;
        }
        else if((country.getCountryID() == 1 || country.getCountryID() == 3) && state.length() != 2) {
            Alert stateLengthError = new Alert(Alert.AlertType.ERROR);
            stateLengthError.setTitle("State/Province Error");
            stateLengthError.setContentText("Please enter the 2 character abbreviation for the State/Province.");
            stateLengthError.showAndWait();
            return false;
        }
        else if(country.getCountryID() == 2 && state.length() != 3) {
            Alert stateLengthError = new Alert(Alert.AlertType.ERROR);
            stateLengthError.setTitle("State/Province Error");
            stateLengthError.setContentText("Please enter the 3 character abbreviation for the UK State/Province.");
            stateLengthError.showAndWait();
            return false;
        }
        // If U.S is selected, postal codes must be 5 characters in length.
        else if(country.getCountryID() == 1 && postalCode.length() != 5) {
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
        //Builds the address from the street, city, and state fields for easier viewing in the table instead of only showing the street info.
        address = street + " " + city + ", " + state;
        return true;
    }

    /** This method saves the entered customer data and stores a new customer in the database.
     * @param actionEvent When the Save button is activated on the Add Customer screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    @FXML
    private void saveAddedCustomer(ActionEvent actionEvent) throws IOException{

        //Checks the entered data and if valid, adds the new customer to the database and returns to the Customers Menu screen.
        if(validateCustomerData(actionEvent)) {

            try {
                customerDAO.addNewCustomer(customerName, address, postalCode, phone, LoginScreenController.userName, LoginScreenController.userName, division.getDivisionID());
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
                Stage stage = (Stage) (saveCustomerBtn.getScene().getWindow());
                stage.setTitle("Customers Menu");
                stage.setScene(new Scene(root,1200 ,700));
                stage.show();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** This method cancels adding the customer to the database and returns to the Customers Menu.
     * @param actionEvent When the Cancel button is activated on the Add Customer screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    @FXML
    private void toCustomersMenu(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel adding new customer?");
        cancelAlert.setTitle("Cancel Adding Customer");
        Optional<ButtonType> result = cancelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersMenu.fxml")));
            Stage stage = (Stage) (cancelAddCustomerBtn.getScene().getWindow());
            stage.setTitle("Customers Menu");
            stage.setScene(new Scene(root,1200 ,700));
            stage.show();
        }
    }
}
