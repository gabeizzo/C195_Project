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

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

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

    @Override
    public String toString(){
        return countryName;
    }
}
