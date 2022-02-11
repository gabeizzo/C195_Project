package DAO;

import Utilities.DBQuery;
import controller.LoginScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Appointment;
import java.sql.*;
import java.time.LocalDateTime;

/** This is the Appointment Data Access Object concrete class which implements the AppointmentDAO interface.
 * This class is responsible for accessing data from the database.
 */
public class AppointmentDAOImpl implements AppointmentDAO{
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection connection = Main.connection;
    public static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    public static ObservableList<Appointment> currMonthAppts = FXCollections.observableArrayList();
    public static ObservableList<Appointment> apptsByType = FXCollections.observableArrayList();
    public static ObservableList<Appointment> apptsByContactID = FXCollections.observableArrayList();
    public static ObservableList<Appointment> apptsByUserID = FXCollections.observableArrayList();

    /** The AppointmentDAOImpl constructor.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public AppointmentDAOImpl() throws SQLException {
        //empty class constructor
    }

    /** Gets the list of all appointments in the MySQL db.
     * @return The list of all appointments stored in the db.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Appointment> getAllApptsFromDB() throws SQLException {

        //Query the database to get all appointments (used with the View All radio button).
        String allApptsFromDB = "SELECT * FROM appointments";
        DBQuery.setPreparedStatement(connection, allApptsFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();

        try {
            while(rs.next()) {
                Appointment appt = getApptData();
                allAppts.add(appt);
            }
        } catch (SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allAppts;
    }

    /** Gets an appointment based on the appointment's ID.
     * @param appointmentID The appointment's id.
     * @return The appointment with a matching appointment ID.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public Appointment getApptByID(int appointmentID) throws SQLException {

        // Queries the MySQL database and selects all appointments that match a specified appointment ID.
        String selectByApptIDFromDB = "SELECT * FROM appointments WHERE Appointment_ID =" + appointmentID;
        DBQuery.setPreparedStatement(connection, selectByApptIDFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();

        try {
            while(rs.next()) {
                return getApptData();
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /** Gets a list of appointments based on their type.
     * @param apptType The appointment's type.
     * @return The list of appointments based on the appointment type.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Appointment> getApptsByType(String apptType) throws SQLException {
        String selectByTypeFromDB = "SELECT * FROM appointments WHERE type = ?";
        DBQuery.setPreparedStatement(connection, selectByTypeFromDB);
        pst = DBQuery.getPreparedStatement();
        pst.setString(1,apptType);
        rs = pst.executeQuery();

        try {
            while(rs.next()) {
                Appointment appt = getApptData();
                apptsByType.add(appt);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return apptsByType;
    }

    /** Gets the appointment data from the database.
     * @return An Appointment object.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    private Appointment getApptData() throws SQLException{
        int apptID = rs.getInt("Appointment_ID");
        String apptTitle = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String apptType = rs.getString("Type");
        LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
        LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
        LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int customerID = rs.getInt("Customer_ID");
        int userID = rs.getInt("User_ID");
        int contactID = rs.getInt("Contact_ID");

        return new Appointment(apptID, apptTitle, description, location, apptType,
                startTime, endTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
    }

    /** Gets the list of current month's appointments to be displayed when the View Current Month radio button is selected.
     * @return The list of all current month's appointment to be displayed.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Appointment> getCurrMonthAppts() throws SQLException {
        // Query DB for current month's appointments. To be displayed when the currMonth radio button is selected.
        String currMonthApptsFromDB = "SELECT * FROM appointments WHERE MONTH(start) = MONTH(current_date()) AND YEAR(start) = YEAR(current_date())";
        DBQuery.setPreparedStatement(connection, currMonthApptsFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();

        try {
            while(rs.next()) {
                Appointment appt = getApptData();
                currMonthAppts.add(appt);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return currMonthAppts;
    }

    /** Gets the list of appointments from the database based on a contact's id.
     * @param contactID The contact id associated with the appointment(s).
     * @return The list of appointments associated with a given contact id.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Appointment> getApptsByContactID(int contactID) throws SQLException {
        // Query the database for all appointments that match a contact's id.
        // Displays in ascending order based on the start date/time.
        String apptsByContactIDFromDB = "SELECT * FROM appointments WHERE Contact_ID = ? ORDER BY start ASC";
        DBQuery.setPreparedStatement(connection, apptsByContactIDFromDB);
        pst = DBQuery.getPreparedStatement();
        pst.setInt(1, contactID);
        rs = pst.executeQuery();

        // get results and construct appointment and add to overall list.
        while (rs.next()) {
            Appointment appt = getApptData();
            apptsByContactID.add(appt);
        }
        return apptsByContactID;
    }

    /** Gets the list of appointments based on a user id.
     * @param userID The user ID associated with the appointment(s).
     * @return The list of appointments based on a user id in start time ascending order.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Appointment> getApptsByUserID(int userID) throws SQLException {
        // Query DB for all appointments that match a user id listed in ascending order.
        String apptsByUserIDFromDB = "SELECT * FROM appointments WHERE User_ID = ? ORDER BY Start ASC";
        DBQuery.setPreparedStatement(connection, apptsByUserIDFromDB);
        pst = DBQuery.getPreparedStatement();
        pst.setInt(1, userID);
        rs = pst.executeQuery();
        while (rs.next()) {
            Appointment appointment = getApptData();
            apptsByUserID.add(appointment);
        }
        return apptsByUserID;
    }

    /** Adds an appointment to the database
     * @param apptTitle The appt title to add.
     * @param description The appt description to add.
     * @param location The appt location to add.
     * @param apptType The appt type to add.
     * @param startDateTime The appt start date/time to add.
     * @param endDateTime The appt end date/time to add.
     * @param createDate The appt create date.
     * @param createdBy The user who created the appt.
     * @param lastUpdate The user who last updated the appt.
     * @param lastUpdatedBy The user who last updated the appt.
     * @param customerID The customer ID the appointment is with.
     * @param userID The user ID associated with the appt.
     * @param contactID The contact ID associated with the appt.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void addAppt(String apptTitle, String description, String location, String apptType, LocalDateTime startDateTime,
                        LocalDateTime endDateTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                        String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

        //Attempt to add/insert appointment data into the MuSQL database
        String insertApptIntoDB = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatement(connection, insertApptIntoDB);
        pst = DBQuery.getPreparedStatement();
        pst.setString(1, apptTitle);
        pst.setString(2, description);
        pst.setString(3, location);
        pst.setString(4, apptType);
        pst.setTimestamp(5, Timestamp.valueOf(startDateTime));
        pst.setTimestamp(6, Timestamp.valueOf(endDateTime));
        pst.setTimestamp(7, Timestamp.valueOf(createDate));
        pst.setString(8, createdBy);
        pst.setTimestamp(9, Timestamp.valueOf(lastUpdate));
        pst.setString(10, lastUpdatedBy);
        pst.setInt(11, customerID);
        pst.setInt(12, userID);
        pst.setInt(13, contactID);
        pst.execute();

        //Checks if appointment updated successfully or not and puts out a message to the console.
        if (pst.getUpdateCount() > 0) {
            System.out.println("Inserted appointment into database successfully!");
        }
        else {
            System.out.println("Attempt to insert appointment failed.");
        }
    }

    /** Modifies a selected appointment's data and changes the values in the database when saved.
     * @param apptID The appt ID - cannot be modified, auto-generated. Text field disabled.
     * @param apptTitle The appt title to modify.
     * @param description The appt description to modify.
     * @param location The appt location to modify.
     * @param apptType The appt type to modify.
     * @param start The start time of the appt to modify.
     * @param end The end time of the appointment to modify.
     * @param customerID The customer ID to modify.
     * @param userID The user ID to modify.
     * @param contactID The ID of the contact to modify.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void modifyAppt(int apptID, String apptTitle, String description, String location, String apptType, LocalDateTime start,
                           LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {

        //Modifies existing appointment in the database and sets the update time and last updated by the user logged in.
        String modifyApptInDB = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, Contact_ID = ?, WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(connection, modifyApptInDB);
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = LoginScreenController.userName;

        try{
            PreparedStatement pst = DBQuery.getPreparedStatement();
            pst.setString(1, apptTitle);
            pst.setString(2, description);
            pst.setString(3, location);
            pst.setString(4, apptType);
            pst.setTimestamp(5, Timestamp.valueOf(start));
            pst.setTimestamp(6, Timestamp.valueOf(end));
            pst.setTimestamp(7, Timestamp.valueOf(lastUpdate));
            pst.setString(8, lastUpdatedBy);
            pst.setInt(9, customerID);
            pst.setInt(10, userID);
            pst.setInt(11, contactID);
            pst.setInt(12, apptID);
            pst.execute();
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /** Deletes a selected appointment from the database.
     * @param appointmentID The ID of the appointment being deleted.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void deleteApptFromDB(int appointmentID) throws SQLException {
        String deleteApptInDB = "DELETE FROM appointments WHERE Appointment_ID=" + appointmentID;
        DBQuery.setPreparedStatement(connection, deleteApptInDB);

        try {
            PreparedStatement pst = DBQuery.getPreparedStatement();
            pst.execute();

            if (pst.getUpdateCount() > 0) {
                System.out.println("Deleted appointment from database successfully!");
            }
            else {
                System.out.println("Attempt to delete appointment failed.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** Deletes a customer's appointments if/when the customer gets deleted from the system due to foreign key dependencies.
     * @param customerID The customer ID associated with the appt.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public void deleteCustomerAppts(int customerID) throws SQLException {
        String deleteCustomerApptsFromDB = "DELETE FROM appointments WHERE Customer_ID=" + customerID;
        DBQuery.setPreparedStatement(connection, deleteCustomerApptsFromDB);

        try{
            PreparedStatement pst = DBQuery.getPreparedStatement();
            pst.execute();

            if (pst.getUpdateCount() > 0) {
                System.out.println("Deleted customer's associated appointments from the database successfully!");
            }
            else {
                System.out.println("Attempt to delete customer associated appointments from the database failed.");
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
