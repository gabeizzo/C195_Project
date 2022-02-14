package model;

/** This is the Contact class.
 * This class holds the methods for building Contact objects and getting and setting Contact attributes.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /** This is the Contact constructor which is used to initialize Contact objects.
     * @param contactID The contact's id.
     * @param contactName The contact's name.
     * @param contactEmail The contact's email.
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /** Gets the contact's ID.
     * @return The contact's ID.
     */
    public int getContactID() {
        return contactID;
    }

    /** Sets the contact's ID.
     * @param contactID The contact's ID to set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** Gets the contact's name.
     * @return The contact's name.
     */
    public String getContactName() {
        return contactName;
    }

    /** Sets the contact's name.
     * @param contactName The contact's name to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** This method is used to override the default Object class's toString method.
     * This resolves the display issues in the combo boxes when pulling up contact names.
     * Instead of it displaying the package.class.hashcode it will display the contact name.
     * @return The contact name.
     */
    @Override
    public String toString() {
        return this.getContactName();
    }

    /** Gets the contact's email address.
     * @return The contact's email address.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /** Sets the Contact's email address.
     * @param contactEmail The contact's email address to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}
