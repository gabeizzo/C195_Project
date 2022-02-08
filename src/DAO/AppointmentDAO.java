package DAO;

import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** This is the AppointmentDAO interface.
 * This interface defines the standard operations to be performed on Appointment model objects.
 */
public interface AppointmentDAO {

    /** Gets all appointments from the database to be displayed in the schedule table.
     * @return The list of all appointments from the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<Appointment> getAllAppts() throws SQLException;

    /** Gets appointment based on the user id.
     * @param appointmentID The appointment's id that corresponds to the user's id.
     * @return The appointment associated with the user id.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public Appointment getAppt(int appointmentID) throws SQLException;

    /** Adds an appointment to the schedule and stores it in the database.
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
    public void addAppt(String apptTitle, String description, String location, String apptType,
                        LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy,
                        LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException;

    /** Modifies a selected appt and updates the appt. data stored in the database.
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
    public void modifyAppt(int apptID, String apptTitle, String description, String location, String apptType,
                           LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException;

    /** Deletes an appointment based on the appointment ID.
     * @param appointmentID The ID of the appointment being deleted.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void deleteAppt(int appointmentID) throws SQLException;

    /** Deletes appointments that match the customer ID being deleted.
     * @param customerID The customer ID associated with the appt.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public void deleteCustLinkedAppts(int customerID) throws SQLException;

    /** Gets a list of appts by type.
     * Used as part of displaying the reports screen showing appts by Month and Type.
     * @param apptType The appointment's type.
     * @return The list of all appointments by type.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<Appointment> getApptsByType(String apptType) throws SQLException;

    /** Gets the list of current month's appointments.
     * This method is used when View Current Month's radio button is selected.
     * @return The list of the current month's appointments.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<Appointment> getCurrMonthAppts() throws SQLException;

    /** Gets a list of appointments for a contact id.
     * @param contactID The contact id associated with the appointment(s).
     * @return The list of appointments for a contact id.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<Appointment> getApptsByContactID(int contactID) throws SQLException;

    /** Gets the list of appointments for a user id.
     * @param userID The user ID associated with the appointment(s).
     * @return The list of appointments for a user id.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<Appointment> getApptsByUserID(int userID) throws SQLException;

}