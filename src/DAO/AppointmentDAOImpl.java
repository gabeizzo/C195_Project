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
    private Connection connection = Main.connection;
    private PreparedStatement pst;
    private ResultSet rs;
    private String allApptsFromDB = "SELECT * FROM appointments";
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

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
                appointments.add(appointment);
            }
        } catch (SQLException e ) {
            System.out.println("There was an error: " + e.getMessage());
            e.printStackTrace();
        }
        return appointments;
    }

    private Appointment getAppointmentInformation() throws SQLException{
        int appointmentID = rs.getInt("Appointment_ID");
        String title = rs.getString("Title");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String type = rs.getString("Type");
        LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
        LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
        LocalDateTime creatDate = rs.getTimestamp("Create_Date").toLocalDateTime();
        String createdBy = rs.getString("Created_By");
        LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
        String lastUpdatedBy = rs.getString("Last_Updated_By");
        int customerID = rs.getInt("Customer_ID");
        int userID = rs.getInt("User_ID");
        int contactID = rs.getInt("Contact_ID");

        Appointment appointment = new Appointment(appointmentID, title, description, location, type,
                start, end, creatDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
        return appointment;
    }

}
