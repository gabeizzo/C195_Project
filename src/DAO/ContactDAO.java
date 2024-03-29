
package DAO;

import javafx.collections.ObservableList;
import model.Contact;
import java.sql.SQLException;

/** This is the ContactDAO interface.
 * This interface gets implemented by ContactDAOImpl and holds the methods for accessing Contact data in the database
 */
public interface ContactDAO {

    /** Gets the list of all contacts in the database.
     * @return The list of contacts stored in the db.
     */
    ObservableList<Contact> getAllContactsFromDB();

    /** Gets a contact based on their contact ID.
     * @param contactId The contact's unique ID.
     * @return The contact whose ID is a match in the database.
     * @throws SQLException Thrown if there is a database access error.
     */
    Contact getContactByID(int contactId) throws SQLException;
}
