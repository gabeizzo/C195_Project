package main;

import Utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /** This method displays the Main Menu screen when the application is started.
     MainMenu.fxml gets loaded and shows the Appointments main menu.
     @param stage The stage to display.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Appointment Scheduler Login Screen");
        stage.setScene(new Scene(root,600 ,400));
        stage.show();
    }



    public static void main(String[] args) {
        DBConnection.openConnection();
        launch(args);
    }
}
