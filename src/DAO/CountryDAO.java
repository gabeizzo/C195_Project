package DAO;

import javafx.collections.ObservableList;
import model.Country;
import java.sql.SQLException;

/** This is the Country data access object interface.
 * This interface defines the methods to be implemented for accessing Country objects stored in the database.
 */
public interface CountryDAO {
    /** Gets the list of all countries stored in the database.
     * @return The ObservableList of all countries in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    ObservableList<Country> getAllCountriesFromDB() throws SQLException;

    /** Gets the Country from the database that has a matching ID number.
     * @param countryID The country's ID number.
     * @return The country that has a matching ID in the Database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    Country getCountryByID(int countryID) throws SQLException;

    }