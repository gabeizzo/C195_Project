package DAO;

import javafx.collections.ObservableList;
import model.User;
import java.sql.SQLException;

    /** This is the UserDAO interface.
     * This class holds the methods for accessing the User data stored in the MySQL database.
     */
    public interface UserDAO {

        /** Gets all users from the database.
         * @return The list of all users.
         */
        public ObservableList<User> getAllUsers();

        /** Gets a user from the database based on their user ID.
         * @param userID The ID of the user.
         * @return The user whose ID matches what is queried.
         * @throws SQLException Thrown if there is a database access error.
         */
        public User getUserByID(int userID) throws SQLException;

        /** Gets a user from the database based on their username.
         * @param userName the name of the user.
         * @return The name of the user whose name matches what is queried.
         * @throws SQLException Thrown if there is a database access error.
         */
        public User getUserByName(String userName) throws SQLException;

}
