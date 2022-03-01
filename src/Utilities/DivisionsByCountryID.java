package Utilities;

import DAO.CountryDAOImpl;
import DAO.DivisionDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.SQLException;

public class DivisionsByCountryID {
    private DivisionDAOImpl fldDAO = new DivisionDAOImpl();
    private ObservableList<Division> allFLDs = fldDAO.getAllFLDs();
    private CountryDAOImpl cDAO = new CountryDAOImpl();
    private ObservableList<Country> allCountries = cDAO.getAllCountriesFromDB();

    public DivisionsByCountryID() throws SQLException {
    }

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
