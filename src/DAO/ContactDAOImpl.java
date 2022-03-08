package DAO;

import utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This is the ContactDAOImpl class.
 * This class implements the ContactDAO interface for accessing contact data from the database to be used in the application.
 */
public class ContactDAOImpl {
    //For database queries
    Connection connection = Main.connection;

    //List of all contacts
    ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /** Gets the ObservableList of all Contact objects.
     * @return All contacts in the database.
     */
    public ObservableList<Contact> getAllContactsFromDB() throws SQLException {
        int contactID;
        String contactName;
        String contactEmail;

        String allContactsFromDB = "SELECT * FROM contacts";
        DBQuery.setPreparedStatement(connection, allContactsFromDB);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        ResultSet rs = pst.executeQuery();

        try {
            while(rs.next()) {
                contactID = rs.getInt("Contact_ID");
                contactName = rs.getString("Contact_Name");
                contactEmail = rs.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                allContacts.add(contact);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }
    /** Gets the contact from the database based on their contact ID.
     * @param contactId The contact's ID.
     * @return The contact whose ID matches the query.
     * @throws SQLException Thrown if there is a MySQL database access error.
     */
    public Contact getContactByID(int contactId) throws SQLException {
        String getContactByIDFromDB = "SELECT * FROM contacts WHERE Contact_ID=" + contactId;
        DBQuery.setPreparedStatement(connection,getContactByIDFromDB);
        PreparedStatement pst = DBQuery.getPreparedStatement();
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            return new Contact(contactID, contactName, contactEmail);
        }
        return null;
    }
}
