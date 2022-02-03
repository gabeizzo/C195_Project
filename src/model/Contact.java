package model;

public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /** Contact constructor, used for instantiating Contact objects.
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /** Gets the Contact's ID.
     * @return contactID Integer.
     */
    public int getContactID() {
        return contactID;
    }

    /** Sets Contact's ID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** Gets the Contact's name.
     * @return The contact's name.
     */
    public String getContactName() {
        return contactName;
    }

    /** Sets the Contact's name.
     * @param contactName The contact's name
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

    /** Gets the Contact's email.
     * @return The contact's email.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /** Sets the Contact's email.
     * @param contactEmail The email to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}
