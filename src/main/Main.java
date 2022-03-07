/** @author Gabriel Izzo<gizzo@wgu.edu>
 * @version 1.0
 * @Javadoc Javadoc located in the project folder in a separate folder titled javadoc. Example file path: \C195_Project\javadoc<b/>
 */

package main;

import utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the Main class.
 * This class is the starting point of the application.
 * It opens the connection to the MySQL database and loads the Login screen at start, then closes the database connection on close.
 */
public class Main extends Application {
    public static Connection connection;

    /** This method displays the app's Login screen when the application is started.
     * LoginScreen.fxml gets loaded and shows the login window in either English or French depending on the OS language in use.
     * @param stage The stage to display.
     */
    @Override
    public void start(Stage stage) throws MissingResourceException, IOException {
        try {
            // Used for changing the stage title to French as all other stage elements are set to French by the LoginScreenController.
            ResourceBundle rb = ResourceBundle.getBundle("utilities/Nationality", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr")) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
            stage.setTitle("Connexion"); //sets the title to French word for Login
            stage.setScene(new Scene(root,600 ,400));
            stage.show();
            }
        }
        //If system is set to English
        catch (MissingResourceException e) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
            stage.setTitle("Login");
            stage.setScene(new Scene(root,600 ,400));
            stage.show();
        }
    }
        /** This is the main method. It is the starting point for the application and launches the application.
     * The main method establishes the MySQL database connection, launches the application, and closes the connection on exit.
     * @param args The arguments for the main method.
     */
    public static void main(String[] args) throws SQLException {

        connection = DBConnection.getConnection();

        launch(args);

        DBConnection.closeConnection();
    }
}
