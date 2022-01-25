package Utilities;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {
    private static Statement statement; // Statement reference

    //Create Statement Object
    public static void setStatement(Connection conn){
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Return Statement Object
    public static Statement getStatement(){
        return statement;
    }
}
