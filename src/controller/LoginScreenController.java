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
    private AppointmentDAOImpl apptDAO;
    private UserDAOImpl userDAO;
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
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameTxt.clear();
        passwordTxt.clear();

        ZoneId localTimezone = ZoneId.of(TimeZone.getDefault().getID());
        timeZoneID.setText(localTimezone.toString());

        // Translates login screen to French if the operating system's Language Pack is set to French
        try {
            rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                appTitleLabel.setText(rb.getString("appTitle"));
                localTimeZoneLabel.setText(rb.getString("localTimeZoneLabel"));
                userNameTxt.setPromptText(rb.getString("enterUserName"));
                passwordTxt.setPromptText(rb.getString("enterPassword"));
                userNameLabel.setText(rb.getString("userName"));
                passwordLabel.setText(rb.getString("password"));
                resetButton.setText(rb.getString("reset"));
                loginButton.setText(rb.getString("login"));
                exitButton.setText(rb.getString("exit"));

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
            apptDAO = new AppointmentDAOImpl();
            userDAO = new UserDAOImpl();
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
                    ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
                    // If system is in French
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        Alert apptAlertFrench = new Alert(Alert.AlertType.INFORMATION);
                            apptAlertFrench.setTitle(rb.getString("apptIn15MinsTitle"));
                            apptAlertFrench.setContentText(rb.getString("apptIn15MinsMessage") +"\n" + apptID + "\n" + rb.getString("date") + apptDate + " " + rb.getString("time") + apptTime);
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
                    ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        Alert apptAlertFrench = new Alert(Alert.AlertType.INFORMATION);
                        apptAlertFrench.setTitle(rb.getString("noApptsTitle"));
                        apptAlertFrench.setContentText(rb.getString("noApptsMessage"));
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
            ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());

            // If system default is in French, alerts will appear in French
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(rb.getString("blankUsernameTitle"));
                alertFrench.setContentText(rb.getString("blankUsernameMessage"));
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
            ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            // If system is in French
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(rb.getString("blankPasswordTitle"));
                alertFrench.setContentText(rb.getString("blankPasswordMessage"));
                alertFrench.showAndWait();
            }
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
            ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(rb.getString("invalidLoginTitle"));
                alertFrench.setContentText(rb.getString("invalidLoginMessage"));
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

        try {
            DBQuery.setPreparedStatement(connection, getUserNameAndPWFromDB);
            PreparedStatement pst = DBQuery.getPreparedStatement();
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                allUserNames.add(rs.getString("User_Name"));
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating username: " + e.getMessage());
            e.printStackTrace();
        }

        try{
            PreparedStatement pst = DBQuery.getPreparedStatement();
            DBQuery.setPreparedStatement(connection, getUserNameAndPWFromDB);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                allPasswords.add(rs.getString("Password"));
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating password: " + e.getMessage());
            e.printStackTrace();
        }
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        return usernameInput.equals(allUserNames.get(0)) && passwordInput.equals(allPasswords.get(0))
                || usernameInput.equals(allUserNames.get(1)) && passwordInput.equals(allPasswords.get(1));

    }

    /** This method records successful and failed login attempts to login_activity.txt.
     * @param loginSuccessful True if the user login is successful, false if login attempt fails.
     * @param user The user who attempted to log in.
     */
    private void recordLoginActivity(boolean loginSuccessful, String user) throws IOException {
        String loginRecord = "login_activity.txt";
        FileWriter fileWriter = new FileWriter(loginRecord, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ZoneId localTimeZone = ZoneId.of(TimeZone.getDefault().getID());

        if(loginSuccessful) {
            printWriter.println("Login Actvity: Valid user:" + user + " login attempt successful: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ": Location: " + localTimeZone);
        } else if(user.isBlank()) {
            printWriter.println("Login Activity: Invalid Username: " + user + " login attempt failed: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ": Location: " + localTimeZone);
        } else {
            printWriter.println("Login Activity: Username or Password incorrect: " + user + " login attempt failed: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ": Location: " + localTimeZone);
        }
        printWriter.close();
    }

    /** This method handles the login button and on successful login, then loads the Main Menu.
     * @param actionEvent The event where a user clicks on the Login button.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException{
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        if(validLogin()) {
            System.out.println("Login successful!");
            recordLoginActivity(true, usernameInput);
            userName = usernameInput;
            testForApptsIn15mins(actionEvent);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
            Stage stage = (Stage) (loginButton.getScene().getWindow());
            stage.setTitle("Appointment Scheduler Main Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
        else if(usernameInput.isBlank()){
            blankUsernameError(actionEvent);
            recordLoginActivity(false, usernameInput);
        }
        else if(passwordInput.isBlank()){
            blankPasswordError(actionEvent);
            recordLoginActivity(false,usernameInput);
        }
        else{
            invalidLogin(actionEvent);
            recordLoginActivity(false, usernameInput);
        }
    }

    /** This method exits the application.
     * @param actionEvent When the Exit button is selected.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void exitApplication(ActionEvent actionEvent) throws IOException{

        try {
            ResourceBundle rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            // If system default is in French, alerts will appear in French
            if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alertFrench = new Alert(Alert.AlertType.CONFIRMATION);
                alertFrench.setTitle(rb.getString("exitTitle"));
                alertFrench.setContentText(rb.getString("exitMessage"));
                alertFrench.showAndWait();

                Optional<ButtonType> result = alertFrench.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    System.exit(0);
                }
            }
        }
        catch (MissingResourceException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            alert.setTitle("Exit Application");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        }
    }

}
