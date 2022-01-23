package model;

public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Contact constructor, used for instantiating Contact objects.
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the Contact's ID.
     * @return contactID Integer.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets Contact's ID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the Contact's name.
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the Contact's name.
     * @param contactName String
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the Contact's email.
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the Contact's email.
     * @param contactEmail The email to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
