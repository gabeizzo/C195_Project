package utilities;


/** This is the MonthAndTypeData class which is used to gather appointment data for the AppointmentsByMonthAndType screen.
 * The data gathered is the month the appointment occurred, the type of appointment, and the number of those appointments.
 */
public class MonthAndTypeData {
    private String apptMonth;
    private String apptType;
    private int numOfAppts;

    /** This is the MonthAndTypeData constructor used to initialize MonthAndTypeData objects.
     * @param apptMonth The month the appointment occurred.
     * @param apptType The type of appointment.
     * @param numOfAppts The total number of appointments of that type that occurred in any given month.
     */
    public MonthAndTypeData(String apptMonth, String apptType, int numOfAppts) {
        this.apptMonth = apptMonth;
        this.apptType = apptType;
        this.numOfAppts = numOfAppts;
    }

    /** Gets the appointment's month.
     * @return The month the appointment occurred during.
     */
    public String getApptMonth() {
        return apptMonth;
    }

    /** Sets the appointment's month.
     * @param apptMonth The appointment month to set.
     */
    public void setApptMonth(String apptMonth) {
        this.apptMonth = apptMonth;
    }

    /** Gets the appointment's type.
     * @return The appointment's type.
     */
    public String getApptType() {
        return apptType;
    }

    /** Sets the appointment's type.
     * @param apptType The appointment's type to set.
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /** Gets the number of appointments for a month and type.
     * @return The number of appointments for a given month and type.
     */
    public int getNumOfAppts() {
        return numOfAppts;
    }

    /** Sets the number of appointments for a month and type.
     * @param numOfAppts The number of appointments to set.
     */
    public void setNumOfAppts(int numOfAppts) {
        this.numOfAppts = numOfAppts;
    }
}
