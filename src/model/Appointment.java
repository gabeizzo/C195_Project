package model;

import DAO.ContactDAOImpl;
import DAO.UserDAOImpl;
import Utilities.ConvertTime;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
        private int appointmentID;
        private String title;
        private String description;
        private String location;
        private String type;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private LocalDateTime createDate;
        private String createdBy;
        private LocalDateTime lastUpdate;
        private String lastUpdatedBy;
        private int customerID;
        private int userID;
        private int contactID;
        private Contact contact;
        private User user;

        /** Appointment constructor. Responsible for instancing an object of the Appointment model representation.
         *  Holds information for a single appointment.
         *  @param appointmentID String id of appointment
         *  @param title String title of appointment
         *  @param description String description of appointment
         *  @param location String location of appointment
         *  @param type String type of appointment
         *  @param startDateTime LocalDateTime startDateTime of appointment
         *  @param endDateTime LocalDateTime endDateTime of appointment
         *  @param createDate LocalDateType of appointment
         *  @param createdBy String documenting how created the appointment
         *  @param lastUpdate LocalDateTime of lastUpdate of appointment
         *  @param lastUpdatedBy String of who lastUpdated this appointment
         *  @param customerID Integer customerID
         *  @param contactID Integer contactID
         *  @param userID Integer userId */
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
         * @return integer appointmentID
         */
        public int getAppointmentID() {
            return appointmentID;
        }

        /** Sets the appointment ID.
         * @param appointmentID integer appointmentID.
         */
        public void setAppointmentID(int appointmentID) {
            this.appointmentID = appointmentID;
        }

        /** Gets the appointment title.
         * @return Title of the appointment.
         */
        public String getTitle() {
            return title;
        }

        /** Sets the appointment title.
         * @param title The title to set.
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /** Gets the appointment description.
         * @return The appointment description
         */
        public String getDescription() {
            return description;
        }

        /** Sets the appointment description.
         * @param description The description to set.
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /** Gets the appointment location.
         * @return The appointment location.
         */
        public String getLocation() {
            return location;
        }

        /** Sets the appointment location.
         * @param location The appointment location.
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /** Gets the appointment type.
         * @return The appointment type.
         */
        public String getType() {
            return type;
        }

        /** Sets the appointment type.
         * @param type The appointment type.
         */
        public void setType(String type) {
            this.type = type;
        }

        /** Gets the start date and time of the appointment.
         * @return The date and time the appointment starts.
         */
        public LocalDateTime getStartDateTime() {
            return startDateTime;
        }

        /** getStartDateFormatted. Returns formatted string of startDateTime.
         * @return String startDateTime
         */
        /*public String getStartDateFormatted() {
            return ConvertTime.formatDate(startDateTime);
        }*/

        /** Gets the start time of the appointment.
         * @return The start time of the appointment.
         */
        public LocalTime getStartTime() {
            return startDateTime.toLocalTime();
        }

        /** getStartTimeFormatted. Get formatted String representation of start time.
         * @return String formatted start time.
         */
        /*public String getStartTimeFormatted() {
            return ConvertTime.formatTime(startDateTime);
        }
        */

        /** Sets the start date and time of the appointment.
         * @param startDateTime LocalDateTime startDateTime.
         */
        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        /** Gets the end date and time of appointment.
         * @return LocalDateTime endDateTime
         */
        public LocalDateTime getEndDateTime() {
            return endDateTime;
        }

        /** Gets the end date of appointment.
         * @return LocalDate endDateTime converted to local date and time.
         */
        public LocalDate getEndDate() {
            return endDateTime.toLocalDate();
        }

        /** Gets the end time of the appointment.
         * @return LocalTime endTime.
         */
        public LocalTime getEndTime() {
            return endDateTime.toLocalTime();
        }

        /** getEndTimeFormatted. Returns formatted String of appointment end time.
         * @return String endDateTime.
         */
       /*
          public String getEndTimeFormatted() {
            return ConvertTime.formatTime(endDateTime);
         }
         */

        /** setEndDateTime. Set endDate and time for appointment.
         * @param endDateTime LocalDateTime endDateTime.
         */
        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        /** getCreateDate. Returns Creation Date of appointment.
         * @return LocalDateTime createDate.
         */
        public LocalDateTime getCreateDate() {
            return createDate;
        }

        /** setCreateDate. Sets createDate of appointment.
         * @param createDate LocalDateTime createDate.
         */
        public void setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
        }

        /** getCreatedBy. Returns appointment's created by string.
         * @return String createdBy
         */
        public String getCreatedBy() {
            return createdBy;
        }

        /**
         * setCreatedBy. Sets appointments created by member.
         * @param createdBy String createdBy
         */
        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        /** Sets the date and time of the most recent update to the appointment information.
         * @return LocalDateTime lastUpdate
         */
        public LocalDateTime getLastUpdate() {
            return lastUpdate;
        }

        /** Sets the date and time of the most recent update to the appointment information.
         * @param lastUpdate LocalDateTime lastUpdate
         */
        public void setLastUpdate(LocalDateTime lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        /** Gets the name of the person who last updated the appointment.
         * @return String lastUpdateBy
         */
        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        /** Sets the user's name who last updated the appointment information.
         * @param lastUpdatedBy String lastUpdateBy
         */
        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        /** Gets the appointment's customer ID.
         * @return int customerID
         */
        public int getCustomerID() {
            return customerID;
        }

        /** Sets the appointment's customer ID.
         * @param customerID Integer.
         */
        public void setCustomerID(int customerID) {
            this.customerID = customerID;
        }

        /** Gets the appointment's user ID.
         * @return int userID
         */
        public int getUserID() {
            return userID;
        }

        /** Sets the appointment's user ID.
         * @param userID Integer.
         */
        public void setUserID(int userID) {
            this.userID = userID;
        }

        /** Gets the appointment's contact ID.
         * @return int contactID.
         */
        public int getContactID() {
            return contactID;
        }

        /** Sets the appointment's contact ID.
         * @param contactID Integer.
         */
        public void setContactID(int contactID) {
            this.contactID = contactID;
        }

}
