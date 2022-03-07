package DAO;

import utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Country;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/** CountryDAO Implementation class of CountryDAO interface. This class allows for the retrieval of all country records from the database.  */

public class CountryDAOImpl implements CountryDAO {

    //For database queries
    private Connection connection = Main.connection;
    private PreparedStatement pst;
    private ResultSet rs;

    //All countries list
    private ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /** CountryDAOImpl constructor method. Instantiates the class and creates a prepared query and resultSet data structure */
    public CountryDAOImpl() throws SQLException {
    }

    /** This method retrieves all Countries stored in the database.
     * The countries are then added to the allCountries Observable list.
     * @return The allCountries ObservableList which contains all Countries in the database.
     */
    @Override
    public ObservableList<Country> getAllCountriesFromDB() throws SQLException {
        String allCountriesFromDB = "SELECT * FROM countries";
        DBQuery.setPreparedStatement(connection, allCountriesFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();

        try{
            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createTime = rs.getTime("Create_Date").toLocalTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                Country country = new Country(countryID, countryName, createDate, createTime,lastUpdate, createdBy);
                allCountries.add(country);
            }
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allCountries;
    }

    /** Gets a Country from the database based on its ID.
     * @param countryID The country's ID number.
     * @return The Country that has a matching ID to the query statement.
     * @throws SQLException throws SQLException in case a database error occurs.
     */
    @Override
    public Country getCountryByID(int countryID) throws SQLException {
        String getCountryByIDFromDB = "SELECT * FROM countries WHERE Country_ID=" + countryID;
        DBQuery.setPreparedStatement(connection, getCountryByIDFromDB);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        ResultSet rs = pst.executeQuery();
        Country country = null;

     try {
         while (rs.next()) {
             String countryName = rs.getString("Country");
             LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
             LocalTime createTime = rs.getTime("Create_Date").toLocalTime();
             Timestamp lastUpdate = rs.getTimestamp("Last_Update");
             String createdBy = rs.getString("Created_By");

             country = new Country(countryID, countryName, createDate, createTime, lastUpdate, createdBy);
         }
     }
     catch (SQLException e) {
         System.out.println("Error: " + e.getMessage());
         e.printStackTrace();
     }
        return country;
    }
}
