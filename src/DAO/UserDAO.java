package DAO;

import javafx.collections.ObservableList;
import model.User;
import java.sql.SQLException;

    /** This is the UserDAO interface.
     * This class holds the methods for accessing the User objects stored in the MySQL database.
     */
    public interface UserDAO {

        /** Gets all users from the database.
         * @return The list of all users.
         */
        public ObservableList<User> getAllUsers();

        /** Gets a user from the database based on their user ID.
         * @param userID The ID of the user.
         * @return The user whose ID matches.
         * @throws SQLException
         */
        public User getUser(int userID) throws SQLException;

        /** Gets a user from the database based on their username.
         * @param userName the name of the user.
         * @return The name of the user.
         * @throws SQLException
         */
        public User getUser(String userName) throws SQLException;

}
