package main;

import Utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


public class Main extends Application {
    public static Connection connection;

    /** This method displays the app's Login screen when the application is started.
     LoginScreen.fxml gets loaded and shows the login window.
     @param stage The stage to display.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        stage.setTitle("Appointment+ Login Screen");
        stage.setScene(new Scene(root,600 ,400));
        stage.show();
    }
    /** This method is the starting point for the application and launches the application.
     * The main method establishes the MySQL database connection, launches the application, and closes the connection on exit.
     * @param args The arguments for the main method.
     */
    public static void main(String[] args) throws SQLException {

        connection = DBConnection.getConnection();

        launch(args);

        DBConnection.closeConnection();
    }
}
