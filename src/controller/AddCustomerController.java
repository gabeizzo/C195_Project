package controller;

import DAO.CountryDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.FirstLvlDivisionDAOImpl;
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
import model.FirstLvlDivision;
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
    private String customerName;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String state;
    private String address;
    private Country selectedCountry;
    private FirstLvlDivision selectedDivision;

    private FirstLvlDivisionDAOImpl fldDAO = new FirstLvlDivisionDAOImpl();
    private CountryDAOImpl countryDAO = new CountryDAOImpl();
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private DivisionsByCountryID divisionsByCountry = new DivisionsByCountryID();

    private ObservableList<FirstLvlDivision> allFLDs = fldDAO.getAllFLDs();
    private ObservableList<FirstLvlDivision> countryDivisions = FXCollections.observableArrayList();
    private ObservableList<Country> allCountries = countryDAO.getAllCountriesFromDB();

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
    private ComboBox<FirstLvlDivision> customerDivisionCB;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Button cancelAddCustomerBtn;


    /** This is the AddCustomerController constructor which initializes objects of this type.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public AddCustomerController() throws SQLException {
    }


    /** This method initializes the Add Customer screen.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populates the country combo box and selects the first country in the list
        customerCountryCB.setItems(allCountries);
        customerCountryCB.getSelectionModel().selectFirst();

        //Filters divisions by the selected country
        Country country = customerCountryCB.getSelectionModel().getSelectedItem();
        try {
            countryDivisions = divisionsByCountry.divisionsByCountryID(country.getCountryID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Populates the First Level Division combo box with divisions based on the country's ID.
        customerDivisionCB.setItems(countryDivisions);
        customerDivisionCB.getSelectionModel().selectFirst();
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
        selectedCountry = customerCountryCB.getSelectionModel().getSelectedItem();
        selectedDivision = customerDivisionCB.getSelectionModel().getSelectedItem();
    }
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
        getCustomerData();
        if(customerName.isBlank() || phone.isBlank() || street.isBlank() || city.isBlank() || postalCode.isBlank() || state.isBlank()
                || selectedCountry == null || selectedDivision == null) {
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("All Fields Required");
            blankFields.setContentText("All fields are required and must not be left blank.\nPlease complete any blank/missing fields and try again.");
            blankFields.showAndWait();
            return false;
            //Displays an error dialog if postal code is longer than the maximum of 8 digits for UK addresses.
        } else if(postalCode.length() > 8 || postalCode.length() < 5) {
            Alert postalCodeError = new Alert(Alert.AlertType.ERROR);
            postalCodeError.setTitle("Postal Code Error");
            postalCodeError.setContentText("""
                    Valid postal codes should be a minimum character length of 5(U.S, no spaces), 6-7(Canada, including a space), or 6-8(UK, including a space).

                    Postal codes must not be longer than 8 characters max including spaces.""");
            postalCodeError.showAndWait();
            return false;
        }
        address = street + " " + city + ", " + state;
        return true;
    }

    /** This method saves the entered customer data and stores a new customer in the database.
     * @param actionEvent When the Save button is activated on the Add Customer screen.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    @FXML
    private void saveAddedCustomer(ActionEvent actionEvent) throws IOException{

        if(validateCustomerData(actionEvent)) {

            try {
                customerDAO.addNewCustomer(customerName, address, postalCode, phone, LoginScreenController.userName, LoginScreenController.userName, selectedDivision.getDivisionID());
                Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
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
