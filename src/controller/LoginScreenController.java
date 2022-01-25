package controller;

import DAO.AppointmentDAOImpl;
import DAO.UserDAOImpl;
import Utilities.DBConnection;
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
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginScreenController implements Initializable {
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
    private String passwordInput;
    private String usernameInput;
    private ArrayList<String> allUserNames = new ArrayList<>();
    private ArrayList<String> allPasswords = new ArrayList<>();
    private String selectFromDB = "SELECT User_Name, Password FROM users";
    private final String filename = "login_activity.txt";
    private AppointmentDAOImpl AppointmentDAO;
    private UserDAOImpl UserDAO;
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameTxt.clear();
        passwordTxt.clear();

        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        timeZoneID.setText(localZoneID.toString());

        // Translates login screen if system default is French
        try {
            rb = ResourceBundle.getBundle("Utilities/Nationality_fr", Locale.getDefault());
            //Check to figure out what language the machine is using
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
            // Leave this blank so english warning doesn't convey
        }
    }

    public void resetFields(ActionEvent actionEvent) throws IOException{
        userNameTxt.clear();
        passwordTxt.clear();
    }

    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException{
        usernameInput = userNameTxt.getText().toLowerCase().trim();
        passwordInput = passwordTxt.getText().trim();

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) (loginButton.getScene().getWindow());
        stage.setTitle("Appointment Scheduler Main Menu");
        stage.setScene(new Scene(root,1200 ,700));
        stage.show();
    }
    public void exitApplication(ActionEvent actionEvent) throws IOException{
        System.exit(0);
    }

}
