package DAO;

import utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.User;
import java.sql.*;
import java.time.LocalDateTime;

/** This is the UserDAOImpl class which implements the UserDAO interface.
 * This class gets the users and their data from the MySQL database.
 */
public class UserDAOImpl implements UserDAO{
    //For database queries
    Connection connection = Main.connection;
    private final ResultSet rs;

    //List of all users
    private final ObservableList<User> allUsers = FXCollections.observableArrayList();

    /** This is the user data access object implementation constructor used to create instances of UserDAOImpl.
     * @throws SQLException throws SQLException in case a database error occurs.
     */
    public UserDAOImpl() throws SQLException {
        String selectAllUsers = "Select * FROM users";
        DBQuery.setPreparedStatement(connection, selectAllUsers);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();
    }
    /** Gets all user data from the MySQL database.
     * @return The list of all users.
     */
    @Override
    public ObservableList<User> getAllUsers() {
        int userID;
        String userName;
        String password;
        LocalDateTime createDateTime;
        String createdBy;
        Timestamp lastUpdate;
        String lastUpdatedBy;
        // Gets the result set data and creates a user object, then add the user to the allUsers ObservableList.
        try {
            while(rs.next()) {
                userID = rs.getInt("User_ID");
                userName = rs.getString("User_Name");
                password = rs.getString("Password");
                createDateTime = rs.getTimestamp("Create_Date").toLocalDateTime();
                createdBy = rs.getString("Created_By");
                lastUpdate = rs.getTimestamp("Last_Update");
                lastUpdatedBy = rs.getString("Last_Updated_By");

                User user = new User(userID, userName, password, createDateTime, createdBy, lastUpdate, lastUpdatedBy);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
    /** Gets a user from the database based on their ID.
     * @param userID The ID of the user to be queried.
     * @return The user whose ID matches what is stored in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public User getUserByID(int userID) throws SQLException {
        // Query the database for users based on their ID.
        String findUserByID = "SELECT * FROM users WHERE User_ID=" + userID;
        DBQuery.setPreparedStatement(connection, findUserByID);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            LocalDateTime createDateTime = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            return new User(userID, userName, password, createDateTime, createdBy, lastUpdate, lastUpdatedBy);
        }
        return null;
    }
    /** Gets a user from the database based on their name.
     * @param userName The name of the user.
     * @return The user whose name matches the usernames in the database.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    @Override
    public User getUserByName(String userName) throws SQLException {
        String getUserByNameFromDB = "SELECT * FROM users WHERE User_Name=" + userName;
        DBQuery.setPreparedStatement(connection, getUserByNameFromDB);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            int userID = rs.getInt("User_ID");
            String password = rs.getString("Password");
            LocalDateTime createDateTime = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            return new User(userID, userName, password, createDateTime, createdBy, lastUpdate, lastUpdatedBy);
        }
        return null;
    }
}
