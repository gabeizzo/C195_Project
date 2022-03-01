package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** This is the DBConnection abstract class.
 * This class establishes the connection to the MySQL database and is required for accessing the data stored in the database.
 */
public abstract class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference: Uses Connector/J 8.0.28
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    /** This method opens the connection to the database.
     * @return The established connection to the database.
     */
    public static Connection getConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("| Database connection successful! |");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /** This method closes the connection to the database.
     * This method is called when exiting the application.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("| Database connection closed. |");
        }
        catch(SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
