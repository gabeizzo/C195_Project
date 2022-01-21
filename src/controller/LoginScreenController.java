package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public PasswordField passwordTxt;
    public TextField userNameTxt;
    public Label localTimeZoneLbl;
    public Button resetButton;
    public Button loginButton;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void resetFields(ActionEvent actionEvent) {
    }

    public void exitApplication(ActionEvent actionEvent) {
    }

    public void toMainMenu(ActionEvent actionEvent) {
    }
}
