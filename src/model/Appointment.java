package model;

import DAO.ContactDAOImpl;
import DAO.UserDAOImpl;
import Utilities.ConvertTime;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
        private int customerID;
        private int userID;
        private int contactID;
        private int appointmentID;
        private String title;
        private String description;
        private String location;
        private String type;
        private String createdBy;
        private String lastUpdatedBy;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdate;
        private Contact contact;
        private User user;

        /** The appointment constructor, used to construct Appointment objects.
         *  @param appointmentID The id of the appointment.
         *  @param title The title of the appointment.
         *  @param description The description of the appointment.
         *  @param location The location of the appointment.
         *  @param type The type of the appointment.
         *  @param startDateTime The start date/time of the appointment.
         *  @param endDateTime The end date/time of the appointment.
         *  @param createDate The date the appointment was created.
         *  @param createdBy The user who created the appointment.
         *  @param lastUpdate The date/time that the appointment was last updated.
         *  @param lastUpdatedBy The user who last updated this appointment
         *  @param customerID The customer's ID that the appointment is with.
         *  @param contactID The contact ID for the appointment.
         *  @param userID The user ID who created the appointment.
         *  */
        public Appointment(int appointmentID, String title, String description, String location, String type,
                           LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy,
                           LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
            this.appointmentID = appointmentID;
            this.title = title;
            this.description = description;
            this.location = location;
            this.type = type;
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
            this.createDate = createDate;
            this.createdBy = createdBy;
            this.lastUpdate = lastUpdate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.customerID = customerID;
            this.userID = userID;
            this.contactID = contactID;
        }

        /** Gets the appointment ID.
         * @return The appointment ID.
         */
        public int getAppointmentID() {
            return appointmentID;
        }

        /** Sets the appointment ID.
         * @param appointmentID The appointment ID to set.
         */
        public void setAppointmentID(int appointmentID) {
            this.appointmentID = appointmentID;
        }

        /** Gets the appointment title.
         * @return The appointment's title.
         */
        public String getTitle() {
            return title;
        }

        /** Sets the appointment title.
         * @param title The appointment title to set.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /** Gets the appointment's description.
         * @return The appointment's description.
         */
        public String getDescription() {
            return description;
        }

        /** Sets the appointment's description.
         * @param description The appointment description to set.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /** Gets the appointment location.
         * @return The appointment's location.
         */
        public String getLocation() {
            return location;
        }

        /** Sets the appointment location.
         * @param location The appointment's location to set.
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /** Gets the appointment type.
         * @return The appointment's type.
         */
        public String getType() {
            return type;
        }

        /** Sets the appointment type.
         * @param type The appointment's type to set.
         */
        public void setType(String type) {
            this.type = type;
        }

        /** Gets the start date and time of the appointment.
         * @return The date and time that the appointment starts.
         */
        public LocalDateTime getStartDateTime() {
            return startDateTime;
        }

        /** Gets the appointment's start date and formats it to MMMM/DD/YYYY.
         * @return The appointment's start date.
         */
        public String getStartDateFormatted() {
            return ConvertTime.dateFormatted(startDateTime);
        }

        /** Gets the start time of the appointment.
         * @return The start time of the appointment.
         */
        public LocalTime getStartTime() {
            return startDateTime.toLocalTime();
        }

        /** Gets the start time of the appointment and formats it to hh:mm AM/PM.
         * @return String formatted start time.
         */
        public String getStartTimeFormatted() {
            return ConvertTime.timeFormatted(startDateTime);
        }


        /** Sets the start date/time of the appointment.
         * @param startDateTime The start date/time of the appointment.
         */
        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        /** Gets the end date and time of appointment.
         * @return The end time of the appointment.
         */
        public LocalDateTime getEndDateTime() {
            return endDateTime;
        }

        /** Gets the end date of appointment as a local date.
         * @return The end date of the appointment.
         */
        public LocalDate getEndDate() {
            return endDateTime.toLocalDate();
        }

        /** Gets the end time of the appointment in local time.
         * @return The end time of the appointment in local time.
         */
        public LocalTime getEndTime() {
            return endDateTime.toLocalTime();
        }

        /** Gets the appointment's end time in a 12hr AM/PM format.
         * @return The end time of the appointment.
         */
          public String getEndTimeFormatted() {
            return ConvertTime.timeFormatted(endDateTime);
         }

        /** Sets the appointment's end date and time.
         * @param endDateTime The end date and time of the appointment.
         */
        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        /** Gets the appointment's creation date.
         * @return The appointment's creation date.
         */
        public LocalDateTime getCreateDate() {
            return createDate;
        }

        /** Sets the appointment's creation date.
         * @param createDate The date the appointment was created to set.
         */
        public void setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
        }

        /** Gets the user who created the appointment.
         * @return The user who created the appointment.
         */
        public String getCreatedBy() {
            return createdBy;
        }

        /** Sets user who created the appointment.
         * @param createdBy The user who created the appointment to set.
         */
        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        /** Sets the date and time of the most recent update to the appointment information.
         * @return The date and time of the appointments last update.
         */
        public LocalDateTime getLastUpdate() {
            return lastUpdate;
        }

        /** Sets the date and time of the most recent update to the appointment information.
         * @param lastUpdate The date and time of the appointment info's last update.
         */
        public void setLastUpdate(LocalDateTime lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        /** Gets the name of the person who last updated the appointment.
         * @return The name of the person who last updated the appointment.
         */
        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        /** Sets the user's name who last updated the appointment information.
         * @param lastUpdatedBy The username who last updated the appointment information.
         */
        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        /** Gets the appointment's customer ID.
         * @return The customer ID associated with the appointment.
         */
        public int getCustomerID() {
            return customerID;
        }

        /** Sets the appointment's customer ID.
         * @param customerID The customer ID associated with the appointment.
         */
        public void setCustomerID(int customerID) {
            this.customerID = customerID;
        }

        /** Gets the appointment's user ID.
         * @return The user ID associated with the appointment.
         */
        public int getUserID() {
            return userID;
        }

        /** Sets the appointment's user ID.
         * @param userID The user ID associated with the appointment to set.
         */
        public void setUserID(int userID) {
            this.userID = userID;
        }

        /** Gets the appointment's contact ID.
         * @return The contact ID associated with the appointment.
         */
        public int getContactID() {
            return contactID;
        }

        /** Sets the appointment's contact ID.
         * @param contactID The contact ID associated with the appointment to set.
         */
        public void setContactID(int contactID) {
            this.contactID = contactID;
        }

    /** Gets the contact associated with the appointment.
     * @return The contact associated with the appointment.
     * @throws SQLException
     */
    public Contact getContact() throws SQLException {
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        this.contact = contactDAO.getContact(getContactID());
        return this.contact;
    }

    /** Gets the appointment's contact name.
     * @return The contact name the appointment is with.
     * @throws SQLException
     */
    public String getContactName() throws SQLException {
        Contact contact = getContact();
        return contact.getContactName();
    }

    /** Gets the user who is associated with the appointment.
     * @return The user associated with the appointment.
     * @throws SQLException
     */
    public User getUser() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl();
        this.user = userDAO.getUser(getUserID());
        return this.user;
    }
}
