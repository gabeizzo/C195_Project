package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the MonthAndTypeReport class.
 * This class defines the methods for querying the database for appointments based on their month, type, and number of occurrences.
 */
public class MonthAndTypeReport {
    Connection connection = Main.connection;

    /** This method queries the MySQL database for appointments based on their month and type and returns an ObservabeList with the results.
     *  This list is then displayed in the Appointments By Month And Type screen's tableview.
     * @param apptType The type of the appointment.
     * @return An ObservableList of the
     */
    public ObservableList<MonthAndTypeData> apptMonthAndTypeData(String apptType) {
        String getApptTypeFromDB = "SELECT MONTHNAME(Start) as MONTH, TYPE, COUNT(*) as COUNT FROM appointments WHERE TYPE=? GROUP BY MONTHNAME(Start) ORDER BY MONTHNAME(Start) ASC";
        ObservableList<MonthAndTypeData> apptMonthTypeData = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(connection, getApptTypeFromDB);
            //Database Connection and Query
            PreparedStatement pst = DBQuery.getPreparedStatement();
            pst.setString(1, apptType);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String apptMonth = rs.getString("MONTH");
                int numOfAppts = rs.getInt("COUNT");
                MonthAndTypeData apptData = new MonthAndTypeData(apptMonth, apptType, numOfAppts);
                apptMonthTypeData.add(apptData);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return apptMonthTypeData;
    }

}
