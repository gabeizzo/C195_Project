package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Country {
    private int countryID;
    private String countryName;
    private LocalDate createDate;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Country(int countryID, String countryName, LocalDate createDate, Timestamp lastUpdate, String lastUpdateBy){
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
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

    /** Gets the timestamp of
     * @return
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

}
