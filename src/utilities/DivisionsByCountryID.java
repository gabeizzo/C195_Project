package utilities;

import DAO.DivisionDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import java.sql.SQLException;

/** This is the DivisionsByCountryID class which holds the method for checking which first level divisions are in each country based on their ID's.
 */
public class DivisionsByCountryID {
    private final DivisionDAOImpl fldDAO = new DivisionDAOImpl();
    private final ObservableList<Division> allFLDs = fldDAO.getAllFLDs();

    /** DivisionsByCountryID constructor.
     * @throws SQLException Thrown if a database access error occurs.
     */
    public DivisionsByCountryID() throws SQLException {
    }

    /** This method checks the first level division's Country ID and adds it to the list of countryDivisions if its ID matches.
     * @param id The country ID of the first level division.
     * @return The list of divisions by country.
     * @throws SQLException Thrown if a database access error occurs.
     */
    public ObservableList<Division> divisionsByCountryID(int id) throws SQLException {
        ObservableList<Division> countryDivisions = FXCollections.observableArrayList();
        for(Division fld : allFLDs){
            if(fld.getCountryID() == id){
                countryDivisions.add(fld);
            }
        }
        return countryDivisions;
    }
}
