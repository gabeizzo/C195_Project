package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** This is the DBQuery class.
 * This class holds the methods for getting and setting prepared statements used to pull data from the database.
 */
public class DBQuery {
    private static PreparedStatement pst; // Statement reference

    /** Sets a prepared statement via connecting to the database and taking in sql statements.
     * @param connection The database connection.
     * @param sqlStatement The SQL query statement.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException{
            pst = connection.prepareStatement(sqlStatement);
    }

    /** Gets the prepared statement for querying the database.
     * @return The prepared SQL statement.
     */
    public static PreparedStatement getPreparedStatement(){
        return pst;
    }
}
