package DAO;

import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Division;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class DivisionDAOImpl implements DivisionDAO {
    private Connection connection = Main.connection;
    private PreparedStatement pst;
    private ResultSet rs;
    private ObservableList<Division> allFLDs = FXCollections.observableArrayList();

    /** This is the DivisionDAOImpl constructor.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public DivisionDAOImpl() throws SQLException {
    }

    /** This method gets all first level divisions from the database.
     * First, the method queries the database for all first_level_divisions.
     * Second, the method gets the database data and builds each row into fld objects.
     * Third, the method adds these objects to the allFLDs ObservableList.
     * @return The list of all fld objects from the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public ObservableList<Division> getAllFLDs() throws SQLException {
        String getAllFLDsFromDB = "SELECT * FROM first_level_divisions";
        DBQuery.setPreparedStatement(connection, getAllFLDsFromDB);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();

        try {
            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createTime = rs.getTime("Create_Date").toLocalTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("Country_ID");

                Division fld = new Division(divisionID, divisionName, createDate, createTime, createdBy, lastUpdate, lastUpdatedBy, countryID);
                allFLDs.add(fld);
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allFLDs;
    }

    /** This method queries the database for a first level division based on its ID.
     * After the query it builds a first level division object with the matching ID data and returns the fld object.
     * @param divisionID The first level division's ID.
     * @return The Division object - fld - built from the matching ID's database data.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public Division getFLDByID(int divisionID) throws SQLException {
        Division fld = null;
        String getFLDByIDFromDB = "SELECT * FROM first_level_divisions WHERE Division_ID=" + divisionID;
        DBQuery.setPreparedStatement(connection, getFLDByIDFromDB);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            String divisionName = rs.getString("Division");
            LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
            LocalTime createTime = rs.getTime("Create_Date").toLocalTime();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");

            fld = new Division(divisionID, divisionName, createDate, createTime, createdBy, lastUpdate, lastUpdatedBy,countryID);
        }
        return fld;
    }
}
