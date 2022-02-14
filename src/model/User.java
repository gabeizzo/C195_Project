package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This is the User class.
 * This class holds the methods for building User objects and getting and setting User attributes.
 */
public class User {
    private int userID;
    private String userName;
    private String password;
    private LocalDateTime createDateTime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /** This is the User object constructor.
     * The User constructor is used to initialize User objects prior to use.
     * @param userID The user's ID.
     * @param userName The user's username.
     * @param password The user's password.
     * @param createDateTime The date and time the user was created.
     * @param createdBy The name of the person who created the user.
     * @param lastUpdate The time that the user was last updated.
     * @param lastUpdatedBy The name of the person who last updated the user.
     */
    public User(int userID, String userName, String password, LocalDateTime createDateTime, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDateTime = createDateTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the user's ID.
     * @return The user's ID.
     */
    public int getUserID() {
        return userID;
    }

    /** Sets the user's ID.
     * @param userID The userID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the user's username.
     * @return The user's username.
     */
    public String getUserName() {
        return userName;
    }

    /** Sets the user's username.
     * @param userName The user's username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** This method is used to override the default Object class's toString method.
     * This resolves the display issues in the combo boxes when pulling up user names.
     * Instead of it displaying the package.class.hashcode it will display the user's name.
     * @return The user's username.
     */
    @Override
    public String toString() {
        return userName;
    }

    /** Gets the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /** Sets the user's password.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Gets the date and time that the user data was created.
     * @return The date and time the user data was created.
     */
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    /** Sets the date and time that the user data was created.
     * @param createDateTime The date and time that the user data was created to set.
     */
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    /** Gets the name of the person who created the user data.
     * @return The name of the person who created the user data.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Sets the name of the person who created the user data.
     * @param createdBy The name of the person who created the user data.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Gets the date and time the user data was last updated.
     * @return The date and time the user was last updated.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** Sets the date and time the user was last updated.
     * @param lastUpdate The date and time the user was last updated to set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Gets the name of the person who last updated the user data.
     * @return String lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Sets the name of the person who last updated the user data.
     * @param lastUpdatedBy String
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}
