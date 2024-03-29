package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/** This is the Country class.
 * This class holds the methods for building Country objects and getting and setting Country attributes.
 */
public class Country {
    private int countryID;
    private String countryName;
    private LocalDate createDate;
    private LocalTime createTime;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    /** This is the Country constructor.
     * The Country constructor is used to initialize Country objects.
     * @param countryID The country's ID.
     * @param countryName The country's name.
     * @param createDate The country's create date.
     * @param createTime The country's create time.
     * @param lastUpdate The date and time the country was last updated.
     * @param lastUpdateBy The user who last updated the country data.
     */
    public Country(int countryID, String countryName, LocalDate createDate, LocalTime createTime, Timestamp lastUpdate, String lastUpdateBy){
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /** Gets the country's ID.
     * @return The country's ID.
     */
    public int getCountryID() {
        return countryID;
    }

    /** Sets the country's ID.
     * @param countryID The country ID to set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Gets the country's name.
     * @return The country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /** This method overrides the Object class's default toString method.
     * This resolves the issue of displaying the package.class.hashcode instead of the country name when populating combo boxes.
     * @return The country's name.
     */
    @Override
    public String toString(){
        return countryName;
    }

    /** Sets the country's name.
     * @param countryName The country name to set.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** Gets the country's create date.
     * @return The country's create date.
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /** Sets the country's create date.
     * @param createDate The country's create date to set.
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /** Gets the time the Country was created.
     * @return The time the Country object was created.
     */
    public LocalTime getCreateTime() {
        return createTime;
    }

    /** Sets the time the Country was created.
     * @param createTime The time the Country was created.
     */
    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }
    /** Gets the timestamp of the country's last update.
     * @return The time of the country's last update.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /** Sets the timestamp of the country's last update.
     * @param lastUpdate The time of the country's last update.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** Gets the user who last updated the country info.
     * @return The user who last updated the country's info.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** Sets the user who last updated the country info.
     * @param lastUpdateBy The user who last updated the country's info.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

}
