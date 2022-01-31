package DAO;

import Utilities.DBQuery;
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

    private final String selectAllContacts = "SELECT * FROM contacts";
    private final PreparedStatement pst;
    private final ResultSet rs;
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    Connection connection = Main.connection;

    /** This is the ContactDAOImpl constructor which is used to create instances of UserDAOImpl(user data access object implementations).
     * @throws SQLException
     */
    public ContactDAOImpl() throws SQLException {
        DBQuery.setPreparedStatement(connection, selectAllContacts);
        pst = DBQuery.getPreparedStatement();
        rs = pst.executeQuery();
    }

    /** Gets the ObservableList of all Contact objects.
     * @return All contacts in the list.
     */
    public ObservableList<Contact> getAllContacts() {
        String contactName;
        String contactEmail;
        int contactID;

        try {
            while(rs.next()) {
                contactID = rs.getInt("Contact_ID");
                contactName = rs.getString("Contact_Name");
                contactEmail = rs.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                contacts.add(contact);
            }
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return contacts;
    }
    /** Gets the contact from the database based on their contact ID.
     * @param contactId The contact's ID.
     * @return The contact whose ID matches the query.
     * @throws SQLException
     */
    public Contact getContact(int contactId) throws SQLException {
        String selectContact = "SELECT * FROM contacts WHERE Contact_ID=" + contactId;
        DBQuery.setPreparedStatement(connection,selectContact);
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
