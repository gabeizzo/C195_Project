package DAO;

import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Appointment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentDAOImpl implements AppointmentDAO{
    private String allApptsFromDB = "SELECT * FROM appointments";
    private PreparedStatement pst;
    private ResultSet rs;
    private Connection connection = Main.connection;
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> currMonthAppts = FXCollections.observableArrayList();

    /** The AppointmentDAOImpl constructor.
     * @throws SQLException
     */
    public AppointmentDAOImpl() throws SQLException {
    }

    @Override
    public ObservableList<Appointment> getAllAppts() throws SQLException {
        //Query DB
        DBQuery.setPreparedStatement(connection, allApptsFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();

        try {
            while(rs.next()) {
                Appointment appt = getApptData();
                allAppointments.add(appt);
            }
        } catch (SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allAppointments;
    }

    @Override
    public Appointment getAppt(int appointmentID) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Appointment> getApptByType(String apptType) throws SQLException {
        return null;
    }

    private Appointment getApptData() throws SQLException{
        int appointmentID = rs.getInt("Appointment_ID");
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

        return new Appointment(appointmentID, apptTitle, description, location, apptType,
                startTime, endTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
    }

    /** Gets the list of current month's appts to be displayed when the View Current Month radio button is selected.
     * @return The list of all current month's appointment to be displayed.
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getCurrMonthAppts() throws SQLException {
        // Query DB for current month's appointments to be displayed when the corresponding radio button is selected.
        String selectCurrMonthAppts = "Select * from appointments WHERE MONTH(start)=MONTH(current_date()) AND YEAR(start)=year(current_date())";
        DBQuery.setPreparedStatement(connection, selectCurrMonthAppts);
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

    @Override
    public ObservableList<Appointment> getApptByContactID(int contactID) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Appointment> getApptByUserID(int userID) throws SQLException {
        return null;
    }

    @Override
    public void addAppt(String apptTitle, String description, String location, String apptType, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

    }

    @Override
    public void modifyAppt(int apptID, String apptTitle, String description, String location, String apptType, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {

    }

    @Override
    public void deleteAppt(int appointmentID) throws SQLException {

    }

    @Override
    public void deleteCustLinkedAppts(int customerID) throws SQLException {

    }


}
