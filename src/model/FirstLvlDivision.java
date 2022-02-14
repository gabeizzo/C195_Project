package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/** This is the FirstLvlDivision(FLD for short) class.
 * This class holds the methods for building FLD objects and getting and setting FLD attributes.
 */
public class FirstLvlDivision {
    private int divisionID;
    private String divisionName;
    private LocalDate createDate;
    private LocalTime createTime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private int countryID;

    /** This is the FirstLvlDivision constructor.
     * The FirstLvlDivision constructor is used to initialize FirstLvlDivision objects.
     * @param divisionID The first level division's ID.
     * @param divisionName The division's name.
     * @param createDate The division's create date.
     * @param createTime The division's create time.
     * @param createdBy The person who created the division data.
     * @param lastUpdate The division's last update time.
     * @param lastUpdateBy The person who last updated the division data.
     * @param countryID The country ID the FLD belongs to.
     */
    public FirstLvlDivision(int divisionID, String divisionName, LocalDate createDate, LocalTime createTime, String createdBy, Timestamp lastUpdate, String lastUpdateBy, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.countryID = countryID;
    }

    /** Gets the first level division's ID.
     * @return The division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** Sets the first level division's ID.
     * @param divisionID The division ID to set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** Gets the first level division's name.
     * @return The division's name.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /** This method overrides the Object class's default toString method.
     * This fixes the issue of displaying the package.class.hashcode instead of division name in combo boxes throughout the application.
     * @return The division's name.
     */
    @Override
    public String toString() {
        return divisionName;
    }

    /** Sets the first level division's name.
     * @param divisionName The division's name to set.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** Gets the first level division's create date.
     * @return The date that the fld info was created.
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /** Sets the first level division's creation date.
     * @param createDate The FLD's create date.
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /** Gets the first level division's creation time.
     * @return The time that the fld info was created.
     */
    public LocalTime getCreateTime() {
        return createTime;
    }

    /** Sets the first level division's creation time.
     * @param createTime The time that the fld info was created.
     */
    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    /** Gets the first level division's created by user info.
     * @return The user who created the fld info.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /** Sets the first level division's created by info.
     * @param createdBy The user who created the fld info.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** Gets the timestamp of the first level division's last update.
     * @return The time that the fld info was last updated.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** Sets the first level division's last update info.
     * @param lastUpdate The time that the fld info was last updated.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Gets the first level division's last updated by user info.
     * @return The user who last updated the division data.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** Sets the first level division's last updated by info.
     * @param lastUpdateBy The user who last updated the division data.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /** Gets the first level division's corresponding country ID.
     * @return The country ID of the division.
     */
    public int getCountryID() {
        return countryID;
    }

    /** Sets the first level division's corresponding countryID.
     * @param countryID The country ID for the division.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

}