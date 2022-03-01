package DAO;

import javafx.collections.ObservableList;
import model.Division;

import java.sql.SQLException;

/** This is the Division interface which defines the methods for accessing Division database objects.
 */
public interface DivisionDAO {

    /** Gets the list of all first level divisions from the database.
     * @return An ObservableList of all Division objects from the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public ObservableList<Division> getAllFLDs() throws SQLException;

    /** Gets a first level division based on its ID.
     * @param divisionID The first level division's ID.
     * @return The division that has a matching ID in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public Division getFLDByID(int divisionID) throws SQLException;
}

