package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the MonthAndTypeReport class.
 * This class defines the methods for querying the database for appointments based on their month, type, and occurrence.
 */
public class MonthAndTypeReport {
    private PreparedStatement pst;
    private ResultSet rs;
    Connection connection = Main.connection;

    /** This is the MonthAndTypeReport constructor used to initialize MonthAndTypeReport objects.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public MonthAndTypeReport() throws SQLException {
    }

    public ObservableList<MonthAndTypeData> monthTypeInformation(String apptType) {
        String getApptTypeFromDB = "SELECT MONTHNAME(Start) as MONTH, type, COUNT(*) as COUNT FROM appointments WHERE TYPE=? GROUP BY MONTHNAME(Start)";
        ObservableList<MonthAndTypeData> apptMonthTypeData = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(connection, getApptTypeFromDB);
            pst = DBQuery.getPreparedStatement();
            pst.setString(1, apptType);
            rs = pst.executeQuery();

            while (rs.next()) {
                String apptMonth = rs.getString("MONTH");
                int numOfAppts = rs.getInt("COUNT");
                MonthAndTypeData apptData = new MonthAndTypeData(apptMonth, apptType, numOfAppts);
                apptMonthTypeData.add(apptData);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return apptMonthTypeData;
    }

}
