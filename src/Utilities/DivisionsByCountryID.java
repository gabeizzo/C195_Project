package Utilities;

import DAO.CountryDAOImpl;
import DAO.FirstLvlDivisionDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLvlDivision;
import java.sql.SQLException;

public class DivisionsByCountryID {
    private FirstLvlDivisionDAOImpl fldDAO = new FirstLvlDivisionDAOImpl();
    private ObservableList<FirstLvlDivision> allFLDs = fldDAO.getAllFLDs();
    private CountryDAOImpl cDAO = new CountryDAOImpl();
    private ObservableList<Country> allCountries = cDAO.getAllCountriesFromDB();

    public DivisionsByCountryID() throws SQLException {
    }

    public ObservableList<FirstLvlDivision> DivisionsByCountryID(int id) throws SQLException {
        ObservableList<FirstLvlDivision> countryDivisions = FXCollections.observableArrayList();
        for(FirstLvlDivision fld : allFLDs){
            if(fld.getCountryID() == id){
                countryDivisions.add(fld);
            }
        }
        return countryDivisions;
    }
}
