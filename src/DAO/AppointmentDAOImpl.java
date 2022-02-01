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

    public AppointmentDAOImpl() throws SQLException {
    }
    @Override
    public ObservableList<Appointment> getAllAppts() throws SQLException {
        // set up a query to the database and get results
        DBQuery.setPreparedStatement(connection, allApptsFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();
        // create an appointment from the information and add to an overall list
        try {
            while(rs.next()) {
                Appointment appointment = getAppointmentInformation();
                allAppointments.add(appointment);
            }
        } catch (SQLException e ) {
            System.out.println("There was an error: " + e.getMessage());
            e.printStackTrace();
        }
        return allAppointments;
    }

    private Appointment getAppointmentInformation() throws SQLException{
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

}
