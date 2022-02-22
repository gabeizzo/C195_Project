package controller;

import DAO.AppointmentDAOImpl;
import DAO.UserDAOImpl;
import Utilities.DBQuery;
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
import main.Main;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LoginScreenController implements Initializable {
    private String passwordInput;
    private String usernameInput;
    public static String userName;
    private final ArrayList<String> allUserNames = new ArrayList<>();
    private final ArrayList<String> allPasswords = new ArrayList<>();
    private ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private final Connection connection = Main.connection;

    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField userNameTxt;
    @FXML
    private Label localTimeZoneLabel;
    @FXML
    private Button resetButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label appTitleLabel;
    @FXML
    private Label timeZoneID;

    /** Initializes the login screen with either English or French language depending on the system settings.
     * Initializes the zone information to reflect the user's system default.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameTxt.clear();
        passwordTxt.clear();

        ZoneId localTimezone = ZoneId.of(TimeZone.getDefault().getID());
        timeZoneID.setText(localTimezone.toString());

        // Translates login screen to French if the operating system's Language Pack is set to French
        try {
            resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                appTitleLabel.setText(resourceBundle.getString("appTitle"));
                localTimeZoneLabel.setText(resourceBundle.getString("localTimeZoneLabel"));
                userNameTxt.setPromptText(resourceBundle.getString("enterUserName"));
                passwordTxt.setPromptText(resourceBundle.getString("enterPassword"));
                userNameLabel.setText(resourceBundle.getString("userName"));
                passwordLabel.setText(resourceBundle.getString("password"));
                resetButton.setText(resourceBundle.getString("reset"));
                loginButton.setText(resourceBundle.getString("login"));
                exitButton.setText(resourceBundle.getString("exit"));

            }
        } catch(Exception e) {
            // Ignore to continue displaying in French language
        }
    }

    public void resetFields(ActionEvent actionEvent) throws IOException{
        userNameTxt.clear();
        passwordTxt.clear();
    }

    /** Tests to see if the logged-in User has an appointment within 15mins.
     * If the User's system default is using French language pack, displays messages in French, otherwise in English.
     * @param actionEvent Where the User successfully logs in to the application.
     */
    private void testForApptsIn15mins(ActionEvent actionEvent) {
        try {
            AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
            UserDAOImpl userDAO = new UserDAOImpl();
            allAppts = apptDAO.getAllApptsFromDB();
            boolean apptWithin15mins = false;
            int apptID = -1;
            String apptDate ="";
            String apptTime = "";

            // check to see if an appointment occurs within 15 minutes of current time
            LocalTime currentTime = LocalTime.now();
            // search through all appointments in the database to see if an appointment is within 15 minutes
            for(Appointment appt : allAppts) {
                LocalTime startTime = appt.getStartDateTime().toLocalTime();
                long minutesBetweenNowAndAppt = ChronoUnit.MINUTES.between(currentTime, startTime);
                //if the current day matches today and the time is within 15 minutes
                if (appt.getStartDateTime().toLocalDate().equals(LocalDate.now())) {
                    if(minutesBetweenNowAndAppt > -1 && minutesBetweenNowAndAppt <= 15) {
                        apptWithin15mins = true;
                        apptID = appt.getApptID();
                        apptDate = appt.getStartDateFormatted();
                        apptTime = appt.getStartTimeFormatted();
                        break;
                    }
                }
            }
            // If there is an appointment set an alert with the appointment information, otherwise alert says no appointments
            if(apptWithin15mins) {
                //Add french alert notifying of upcoming appointment
                try {
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
                    // If system is in French
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        Alert apptAlertFrench = new Alert(Alert.AlertType.INFORMATION);
                            apptAlertFrench.setTitle(resourceBundle.getString("apptIn15MinsTitle"));
                            apptAlertFrench.setContentText(resourceBundle.getString("apptIn15MinsMessage") +"\n" + apptID + "\n" + resourceBundle.getString("date") + apptDate + " " + resourceBundle.getString("time") + apptTime);
                            apptAlertFrench.showAndWait();
                    }
                    // If language is not in french show english alert of upcoming appointment
                } catch (MissingResourceException e){
                    Alert apptAlertEnglish = new Alert(Alert.AlertType.INFORMATION);
                    apptAlertEnglish.setTitle("Appointments");
                    apptAlertEnglish.setContentText("You have an upcoming appointment within the next 15 minutes!\nAppointment ID: " + apptID +"\nDate: " + apptDate + "\nTime: " + apptTime);
                    apptAlertEnglish.showAndWait();
                }
            }
            else {
                // check if the system language is in French if it is set a no upcoming appointments alert, if not show it in English
                try {
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        Alert apptAlertFrench = new Alert(Alert.AlertType.INFORMATION);
                        apptAlertFrench.setTitle(resourceBundle.getString("noApptsTitle"));
                        apptAlertFrench.setContentText(resourceBundle.getString("noApptsMessage"));
                        apptAlertFrench.showAndWait();
                    }
                }
                catch (MissingResourceException e) {
                    Alert apptAlertEnglish = new Alert(Alert.AlertType.INFORMATION);
                    apptAlertEnglish.setTitle("No Appointments");
                    apptAlertEnglish.setContentText("You do not have any upcoming appointments within the next 15 minutes.");
                    apptAlertEnglish.showAndWait();
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** This method checks to see if the Username entered is blank or not and displays an error message if Username is blank.
     * If the system settings are set to French language pack, the error message will display in French, otherwise English.
     * @param  actionEvent The event of when a user enters a blank username.
     */
    private void blankUsernameError(ActionEvent actionEvent) throws IOException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());

            // If system default is in French, alerts will appear in French
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(resourceBundle.getString("blankUsernameTitle"));
                alertFrench.setContentText(resourceBundle.getString("blankUsernameMessage"));
                alertFrench.showAndWait();
            }
        }
        catch (MissingResourceException e) {
            Alert alertEnglish = new Alert(Alert.AlertType.ERROR);
            alertEnglish.setTitle("Blank Username Error");
            alertEnglish.setContentText("Username must not be left blank.");
            alertEnglish.showAndWait();
        }
    }
    /** This method checks to see if the Password entered is blank or not and displays an error message if Password is blank.
     * If the system settings are set to French language pack, the error message will display in French, otherwise English.
     * @param  actionEvent The event of when a user enters a blank password.
     */
    private void blankPasswordError(ActionEvent actionEvent) throws IOException {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            // If operating system is set to French language
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(resourceBundle.getString("blankPasswordTitle"));
                alertFrench.setContentText(resourceBundle.getString("blankPasswordMessage"));
                alertFrench.showAndWait();
            }
            // If French resource bundle is not in use, Alert displays in English by default
        } catch (MissingResourceException e) {
            Alert alertEnglish = new Alert(Alert.AlertType.ERROR);
            alertEnglish.setTitle("Blank Password Error");
            alertEnglish.setContentText("Password must not be left blank.");
            alertEnglish.showAndWait();
        }
    }

    /** This method checks if the entered Username and/or Password are invalid. If the system is in French it displays a warning in french
     * If the system settings are set to French language pack, the error message will display in French, otherwise English.
     * @param  actionEvent The event of when a user enters an invalid login.
     */
    private void invalidLogin(ActionEvent actionEvent) throws IOException {
        try {
            // If operating system is set to French language
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(resourceBundle.getString("invalidLoginTitle"));
                alertFrench.setContentText(resourceBundle.getString("invalidLoginMessage"));
                alertFrench.showAndWait();
            }
        } catch (MissingResourceException e) {
            Alert alertEnglish = new Alert(Alert.AlertType.ERROR);
            alertEnglish.setTitle("Invalid Login");
            alertEnglish.setContentText("Username and/or Password are invalid.");
            alertEnglish.showAndWait();
        }
    }
    /** Validates the login info with what is in the database.
     * @return True if login info is correct. False if incorrect.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    private boolean validLogin() throws SQLException{
        String getUserNameAndPWFromDB = "SELECT User_Name, Password FROM users";

        try { //Checks to validate the entered username matches a username stored in the database.
            DBQuery.setPreparedStatement(connection, getUserNameAndPWFromDB);
            PreparedStatement pst = DBQuery.getPreparedStatement();
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                allUserNames.add(rs.getString("User_Name"));
            }
        }
        catch (SQLException e) { //If there is an error accessing the database
            System.out.println("Error authenticating username: " + e.getMessage());
            e.printStackTrace();
        }

        try{ //Checks to validate the entered username matches a username stored in the database.
            PreparedStatement pst = DBQuery.getPreparedStatement();
            DBQuery.setPreparedStatement(connection, getUserNameAndPWFromDB);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                allPasswords.add(rs.getString("Password"));
            }
        }
        catch (SQLException e) { //If there is an error accessing the database
            System.out.println("Error authenticating password: " + e.getMessage());
            e.printStackTrace();
        }

        //Gathers the text input into the Username and Password text fields and trims off any additional blank space
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        //Checks the input against the only two users in the database, test(0) and admin(1)
        return usernameInput.equals(allUserNames.get(0)) && passwordInput.equals(allPasswords.get(0))
                || usernameInput.equals(allUserNames.get(1)) && passwordInput.equals(allPasswords.get(1));

    }

    /** This method records successful and failed login attempts to login_activity.txt.
     * @param loginSuccessful True if the user login is successful, false if login attempt fails.
     * @param user The user who attempted to log in.
     */
    private void recordLoginActivity(boolean loginSuccessful, String user) throws IOException {
        String loginRecord = "login_activity.txt";
        FileWriter fw = new FileWriter(loginRecord, true);
        PrintWriter pw = new PrintWriter(fw);
        ZoneId loginZone = ZoneId.of(TimeZone.getDefault().getID());

        //Prints a message to the file depending on the circumstance.
        if(loginSuccessful) {
            pw.println("Login Activity | Valid Username and Password: (" + user + ") | Login attempt SUCCESSFUL at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | Network IP Location: (" + loginZone + ")");
        } else if(user.isBlank()) {
            pw.println("Login Activity | Invalid/Blank Username: (" + user + ") | Login attempt FAILED at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | Network IP Location: (" + loginZone + ")");
        } else {
            pw.println("Login Activity | Invalid Username or Password: (" + user + ") | Login attempt FAILED at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | Network IP Location: (" + loginZone + ")");
        }
        pw.close();
    }

    /** This method handles the login button and on successful login, then loads the Main Menu.
     * @param actionEvent The event where a user clicks on the Login button.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException{
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        //If login is valid, prints to the console, logs the activity and loads the main menu.
        if(validLogin()) {
            System.out.println("Login successful!");
            recordLoginActivity(true, usernameInput);
            userName = usernameInput;
            testForApptsIn15mins(actionEvent);

            //Loads the Main Menu
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
            Stage stage = (Stage) (loginButton.getScene().getWindow());
            stage.setTitle("Appointment+ Main Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
        //If username is blank, displays an error message and logs the activity.
        else if(usernameInput.isBlank()){
            blankUsernameError(actionEvent);
            recordLoginActivity(false, usernameInput);
        }
        //If password is blank, displays an error message and logs the activity.
        else if(passwordInput.isBlank()){
            blankPasswordError(actionEvent);
            recordLoginActivity(false,usernameInput);
        }
        //If login is invalid, displays an error message and logs the activity.
        else{
            invalidLogin(actionEvent);
            recordLoginActivity(false, usernameInput);
        }
    }

    /** This method exits the application when the Exit button is clicked.
     * If the user's system is set to French, a confirmation dialog box displays a confirmation in French.
     * Otherwise, the confirmation pop-up will display in English.
     * If "OK" is selected, the application closes, if "Cancel/Annuler(if French)" the dialog box closes and application remains open.
     * @param actionEvent When the Exit/Sortir button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void exitApplication(ActionEvent actionEvent) throws IOException {

        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());

            //Displays the Exit/Sortir confirmation box in French if operating system is set to French.
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.CONFIRMATION);
                alertFrench.setTitle(resourceBundle.getString("exitTitle"));
                alertFrench.setContentText(resourceBundle.getString("exitMessage"));

                //If user selects OK the application quits. If Annuler(Cancel), the dialog box closes and application stays open.
                Optional<ButtonType> result = alertFrench.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.exit(0);
                }
            }
        }
        //If not set to French, resource bundle defaults and the confirmation box displays in English.
        catch (MissingResourceException e) {
            Alert alertEnglish = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            alertEnglish.setTitle("Exit Application");

            Optional<ButtonType> result = alertEnglish.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }
    }
}
