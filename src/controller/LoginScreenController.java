package controller;

import DAO.AppointmentDAOImpl;
import DAO.UserDAOImpl;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.*;

public class LoginScreenController implements Initializable {
    private AppointmentDAOImpl AppointmentDAO;
    private UserDAOImpl UserDAO;
    private String passwordInput;
    private String usernameInput;
    public static String userName;
    private final String filename = "login_activity.txt";
    private final ArrayList<String> allUserNames = new ArrayList<>();
    private final ArrayList<String> allPasswords = new ArrayList<>();
    private final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private final Connection connection = Main.connection;
    private final String selectFromDB = "SELECT User_Name, Password FROM users";

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameTxt.clear();
        passwordTxt.clear();

        ZoneId localTimezone = ZoneId.of(TimeZone.getDefault().getID());
        timeZoneID.setText(localTimezone.toString());

        // Translates login screen to French if OS's Language Pack is set to French
        try {
            rb = ResourceBundle.getBundle("Utilities/Nationality", Locale.getDefault());
            //Check if system is using the French language
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

                // set userDAOImpl
            }
        } catch(Exception e) {
            // Ignore to maintain French language
        }
    }

    public void resetFields(ActionEvent actionEvent) throws IOException{
        userNameTxt.clear();
        passwordTxt.clear();
    }

    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException{
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        if(validateLogin()){
           System.out.println("Login successful!");
           userName = usernameInput;

           Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
           Stage stage = (Stage) (loginButton.getScene().getWindow());
           stage.setTitle("Appointment Scheduler Main Menu");
           stage.setScene(new Scene(root, 1200, 700));
           stage.show();
       }
    }

    /** Validates the login info with what is in the database.
     * @return True if login info is correct. False if incorrect.
     * @throws SQLException
     */
    private boolean validateLogin() throws SQLException{

        try {
            DBQuery.setPreparedStatement(connection, selectFromDB);
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
            DBQuery.setPreparedStatement(connection, selectFromDB);
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

        if(usernameInput.equals(allUserNames.get(0)) && passwordInput.equals(allPasswords.get(0))
                || usernameInput.equals(allUserNames.get(1)) && passwordInput.equals(allPasswords.get(1))) {
            return true;
        }
        return false;
    }

    /** Exits the application.
     * @param actionEvent When the Exit button is selected.
     * @throws IOException
     */
    public void exitApplication(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }

}
