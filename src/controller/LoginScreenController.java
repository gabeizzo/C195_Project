package controller;

import DAO.AppointmentDAOImpl;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.TimeZoneLambda;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import model.Appointment;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/** This is the LoginScreenController class.
 * This class defines the methods used for login related actions and validation.
 * It also is responsible for displaying login content in French or English depending on the system's current Language settings.
 */
public class LoginScreenController implements Initializable {

    //Usernames and Passwords
    public static String userName;
    private String passwordInput;
    private String usernameInput;
    private final ArrayList<String> allUserNames = new ArrayList<>();
    private final ArrayList<String> allPasswords = new ArrayList<>();

    //GUI fx:id's
    @FXML
    private Label dateTimeLbl;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField userNameTxt;
    @FXML
    private Label timeZoneTitle;
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
    private Label timeZoneLbl;

    //Connects to the database for queries
    private final Connection connection = Main.connection;


    /** Initializes the login screen with either English or French language depending on the system settings.
     * Initializes the zone information using a lambda to reflect the user's system default.
     * Initializes the animated digital clock using an event handler interface lambda.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameTxt.clear();
        passwordTxt.clear();

        //Displays the animated digital clock
        displayClock();

        //TimeZoneLambda getZone displays the time zone on the login screen based on the user's system default.
        TimeZoneLambda getZone = () -> timeZoneLbl.setText(ZoneId.of(TimeZone.getDefault().getID()).toString());
        getZone.showZone();

        // Translates login screen to French if the operating system's Language Pack is set to French
        try {
            resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                appTitleLabel.setText(resourceBundle.getString("appTitle"));
                timeZoneTitle.setText(resourceBundle.getString("timeZoneTitle"));
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

    /** displayClock uses the EventHandler interface with a lambda to initialize and shows an animated digital clock on the login screen.
     */
    private void displayClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTimeLbl.setText(LocalDateTime.now().format(DTF));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /** This method resets the Username and Password fields and clears any existing input.
     * @param actionEvent When the Reset button on the login screen is activated.
     */
    public void resetFields(ActionEvent actionEvent) {
        userNameTxt.clear();
        passwordTxt.clear();
    }

    /** Tests to see if the logged-in User has an appointment within 15mins.
     * If the User's system default is using French language pack, displays messages in French, otherwise in English.
     * @param actionEvent Where the User successfully logs in to the application.
     */
    private void testForApptsIn15mins(ActionEvent actionEvent) {
        try {
            boolean apptWithin15mins = false;
            int apptID = -1;
            String apptDate ="";
            String apptTime = "";
            LocalTime currTime = LocalTime.now();
            AppointmentDAOImpl apptDAO = new AppointmentDAOImpl();
            ObservableList<Appointment> allAppts = apptDAO.getAllApptsFromDB();


            // search through all appointments in the database to see if an appointment is within 15 minutes
            for(Appointment a : allAppts) {
                LocalTime startTime = a.getStartDateTime().toLocalTime();
                long minutesBetweenNowAndAppt = ChronoUnit.MINUTES.between(currTime, startTime);

                //if the current day matches today and the time is within 0-15 minutes apptWithin15mins is true.
                if (a.getStartDateTime().toLocalDate().equals(LocalDate.now())) {
                    if(minutesBetweenNowAndAppt >= 0 && minutesBetweenNowAndAppt <= 15) {
                        apptWithin15mins = true;
                        apptID = a.getApptID();
                        apptDate = a.getStartDateFormatted();
                        apptTime = a.getStartTimeFormatted();
                        break;
                    }
                }
            }
            // If there is an appointment set an alert with the appointment information, otherwise alert says no appointments within the next fifteen minutes.
            if(apptWithin15mins) {

                //Checks if system is in French or not and displays alert notifying of upcoming appointment
                try {
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());
                    // If system is in French
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        Alert apptAlertFrench = new Alert(Alert.AlertType.INFORMATION);
                            apptAlertFrench.setTitle(resourceBundle.getString("apptIn15MinsTitle"));
                            apptAlertFrench.setContentText(resourceBundle.getString("apptIn15MinsMessage") +"\n" + apptID + "\n" + resourceBundle.getString("date") + apptDate + " " + resourceBundle.getString("time") + apptTime);
                            apptAlertFrench.showAndWait();
                    }
                    // If language is not in French show English alert of upcoming appointment
                } catch (MissingResourceException e){
                    Alert apptAlertEnglish = new Alert(Alert.AlertType.INFORMATION);
                    apptAlertEnglish.setTitle("Appointments");
                    apptAlertEnglish.setContentText("You have an upcoming appointment within the next 15 minutes!\nAppointment ID: " + apptID +"\nDate: " + apptDate + "\nTime: " + apptTime);
                    apptAlertEnglish.showAndWait();
                }
            }
            else {
                // checks if the system language is in French or English and displays a no upcoming appointments alert
                try {
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());
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
            e.printStackTrace();
        }
    }

    /** This method checks to see if the Username entered is blank or not and displays an error message if Username is blank.
     * If the system settings are set to French language pack, the error message will display in French, otherwise English.
     * @param  actionEvent The event of when a user enters a blank username.
     */
    private void blankUsernameError(ActionEvent actionEvent) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());

            // If system default is in French, alerts will appear in French
            if (Locale.getDefault().getLanguage().equals("fr")) {
                System.out.println("| Erreur : Le nom d'utilisateur est vide. Veuillez saisir un nom d'utilisateur valide et réessayer. |");
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(resourceBundle.getString("blankUsernameTitle"));
                alertFrench.setContentText(resourceBundle.getString("blankUsernameMessage"));
                alertFrench.showAndWait();
            }
        }
        catch (MissingResourceException e) {
            System.out.println("| Error: Username is blank. Please enter a valid username and try again. |");
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
    private void blankPasswordError(ActionEvent actionEvent) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());
            // If operating system is set to French language
            if (Locale.getDefault().getLanguage().equals("fr")) {
                System.out.println("| Erreur : Le mot de passe est vide. Veuillez saisir un mot de passe valide et réessayer. |");
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(resourceBundle.getString("blankPasswordTitle"));
                alertFrench.setContentText(resourceBundle.getString("blankPasswordMessage"));
                alertFrench.showAndWait();
            }
        }
        // If French resource bundle is not in use, Alert displays in English by default
        catch (MissingResourceException e) {
            System.out.println("| Error: Password is blank. Please enter a valid password and try again. |");
            Alert alertEnglish = new Alert(Alert.AlertType.ERROR);
            alertEnglish.setTitle("Blank Password Error");
            alertEnglish.setContentText("Password must not be left blank.");
            alertEnglish.showAndWait();
        }
    }

    /** This method checks if the entered Username and/or Password are invalid.
     * If the system settings are set to French language pack, the error message will display in French, otherwise English.
     * @param  actionEvent The event of when a user enters an invalid login.
     */
    private void invalidLogin(ActionEvent actionEvent) {
        try {
            // If operating system is set to French language, displays error dialog in French.
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                System.out.println("| Erreur : Nom d'utilisateur/Mot de passe saisi incorrect. Veuillez saisir un nom d'utilisateur/mot de passe valide et réessayer. |");
                Alert alertFrench = new Alert(Alert.AlertType.ERROR);
                alertFrench.setTitle(resourceBundle.getString("invalidLoginTitle"));
                alertFrench.setContentText(resourceBundle.getString("invalidLoginMessage"));
                alertFrench.showAndWait();
            }
        }
        // If system is not set to French, displays error dialog in English.
        catch (MissingResourceException e) {
            System.out.println("| Error: Invalid Username/Password entered. Please enter a valid username/password and try again. |");
            Alert alertEnglish = new Alert(Alert.AlertType.ERROR);
            alertEnglish.setTitle("Invalid Login");
            alertEnglish.setContentText("Username and/or Password are invalid.");
            alertEnglish.showAndWait();
        }
    }

    /** Validates the login info with what is in the database.
     * @return validLogin is true if login info is correct, false if incorrect.
     */
    private boolean validLogin() {
        String getUserNameAndPWFromDB = "SELECT User_Name, Password FROM users";

        //Checks to validate the entered username matches a username stored in the database.
        try {
            DBQuery.setPreparedStatement(connection, getUserNameAndPWFromDB);
            PreparedStatement pst = DBQuery.getPreparedStatement();
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                allUserNames.add(rs.getString("User_Name"));
            }
        }
        //If there is an error accessing the database prints error message to the console.
        catch (SQLException e) {
            e.printStackTrace();
        }

        //Checks to validate the entered username matches a username stored in the database.
        try{
            PreparedStatement pst = DBQuery.getPreparedStatement();
            DBQuery.setPreparedStatement(connection, getUserNameAndPWFromDB);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                allPasswords.add(rs.getString("Password"));
            }
        }
        //If there is an error accessing the database print error message to the console.
        catch (SQLException e) {
            e.printStackTrace();
        }
        //Gathers the text input from the Username and Password text fields, forces lowercase and trims off any additional blank space to prevent login issues.
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        //Checks the input against the only two users currently in the database, test(0) and admin(1)
        return usernameInput.equals(allUserNames.get(0)) && passwordInput.equals(allPasswords.get(0))
                || usernameInput.equals(allUserNames.get(1)) && passwordInput.equals(allPasswords.get(1));

    }

    /** This method records successful and failed login attempts to login_activity.txt.
     * @param loginSuccessful True if the user login is successful, false if login attempt fails.
     * @param userName The user who attempted to log in.
     */
    private void recordLoginActivity(boolean loginSuccessful, String userName) throws IOException {
        String loginRecord = "login_activity.txt";
        FileWriter fw = new FileWriter(loginRecord, true);
        PrintWriter pw = new PrintWriter(fw);
        ZoneId loginZone = ZoneId.of(TimeZone.getDefault().getID());
        InetAddress ip;
        String hostname;
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();

        //Prints to login_activity.txt depending on the login activity circumstance.
        if(loginSuccessful) {
            pw.println("Login Activity | Valid Username and Password: (" + userName + ") | Login attempt SUCCESSFUL at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | Time Zone: (" + loginZone + ") | IP: " + ip + " | Host Name: " + hostname);
        }
        else if(userName.isBlank()) {
            pw.println("Login Activity | Invalid/Blank Username: (" + userName + ") | Login attempt FAILED at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | Time Zone: (" + loginZone + ") | IP: " + ip + " | Host Name: " + hostname);
        }
        else {
            pw.println("Login Activity | Invalid Username or Password: (" + userName + ") | Login attempt FAILED at: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | TimeZone: (" + loginZone + ") | IP: " + ip + " | Host Name: " + hostname);
        }
        pw.close();
    }

    /** This method handles the login button and on successful login, then loads the Main Menu.
     * @param actionEvent The event where a user clicks on the Login button.
     * @throws IOException Thrown if there is a failure during reading, writing, and searching file or directory operations.
     */
    public void loginToMainMenu(ActionEvent actionEvent) throws IOException {

        //Gets the username and password entered into the text-fields and trims any additional blank space off of the username to assist with validation.
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        //If login is valid, prints a success message to the console, logs the activity and loads the main menu.
        if(validLogin()) {

            //Records the login activity to login_activity.txt
            recordLoginActivity(true, usernameInput);
            userName = usernameInput;

            //Prints success message to the console.
            System.out.println("| Login successful. Welcome to Appointment+," + userName +"! |");

            //Test to see if there are any appointments within 15mins of login
            testForApptsIn15mins(actionEvent);

            //Loads the Main Menu screen
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
            Stage stage = (Stage) (loginButton.getScene().getWindow());
            stage.setTitle("Appointment+ Main Menu");
            stage.setScene(new Scene(root, 1200, 700));
            stage.show();
        }
        //If username is blank, prints to error message the console, displays an error message and logs the activity.
        else if(usernameInput.isBlank()){
            blankUsernameError(actionEvent);
            recordLoginActivity(false, usernameInput);
        }
        //If password is blank, prints error message to the console, displays an error message and logs the activity.
        else if(passwordInput.isBlank()){
            blankPasswordError(actionEvent);
            recordLoginActivity(false,usernameInput);
        }
        //If login is invalid, prints error message to the console, displays an error message and logs the activity.
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
     */
    public void exitApplication(ActionEvent actionEvent) {

        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Language", Locale.getDefault());

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
        //If language is not set to French, resource bundle defaults and the confirmation box displays in English.
        catch (MissingResourceException e) {
            Alert alertEnglish = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            alertEnglish.setTitle("Exit Application");

            Optional<ButtonType> result = alertEnglish.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBConnection.closeConnection();
                System.out.println("| Thank you for using Appointment+! Goodbye. |");
                System.exit(0);
            }
        }
    }
}
