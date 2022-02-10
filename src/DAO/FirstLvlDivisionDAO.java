package DAO;

import javafx.collections.ObservableList;
import model.FirstLvlDivision;
import java.sql.SQLException;

/** This is the FirstLvlDivision interface which defines the methods for accessing FirstLvlDivision database objects.
 */
public interface FirstLvlDivisionDAO {

    /** Gets the list of all first level divisions from the database.
     * @return An ObservableList of all FirstLvlDivision objects from the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<FirstLvlDivision> getAllFLDs() throws SQLException;

    /** Gets a first level division based on its ID.
     * @param divisionID The first level division's ID.
     * @return The division that has a matching ID in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public FirstLvlDivision getFLDByID(int divisionID) throws SQLException;
}

