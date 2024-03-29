package utilities;

import javafx.scene.control.ComboBox;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;

/** This is the ConvertTime class which is used to change times and dates accordingly throughout the application.
 * This class holds the methods for adjusting and formatting times from local time to EST/UTC.
 */
public class ConvertTime {
    /** Converts a localDateTime format to month(MMMM), day(dd) and year(yyyy).
     * @param localDT The local date time to be formatted.
     * @return The formatted date to display.
     */
    public static String dateFormatted(LocalDateTime localDT){
        DateTimeFormatter displayDate = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return displayDate.format(localDT);

    }
    /** Converts a localDateTime format to display in 12 hour AM/PM format.
     * @param localDT The local date time to be formatted.
     * @return The formatted time to display.
     */
    public static String timeFormatted(LocalDateTime localDT) {
        DateTimeFormatter displayTime = DateTimeFormatter.ofPattern("h:mm a");
        return displayTime.format(localDT);
    }
    /** Converts a LocalTime format to display in 12 hour AM/PM format.
     * @param localTime The local time to be formatted.
     * @return The formatted local time to display.
     */
    public static String timeFormatted(LocalTime localTime) {
        DateTimeFormatter displayLT = DateTimeFormatter.ofPattern("hh:mm a");
        return displayLT.format(localTime);
    }
    /** Converts a ZonedDateTime format to display in 12 hour AM/PM format.
     * @param zonedDT The zoned date time to format.
     * @return The formatted zoned date time to display.
     */
    public static String timeFormatted(ZonedDateTime zonedDT) {
        DateTimeFormatter displayZonedDT = DateTimeFormatter.ofPattern("hh:mm a");
        return displayZonedDT.format(zonedDT);
    }
    /** Fills the appointment time slot combo box with times.
     * @param localTimeCB The local time combo box.
     * @param apptStartTime The appointment's start time.
     * @param apptEndTime The appointment's end time.
     */
    public static void displayValidTimes(ComboBox<LocalTime> localTimeCB, LocalTime apptStartTime, LocalTime apptEndTime) {
        while(apptStartTime.isBefore(apptEndTime.plusSeconds(1))) {
            localTimeCB.getItems().add(apptStartTime);
            apptStartTime = apptStartTime.plusMinutes(15);
        }
    }

    /** Converts local time to EST.
     * @param localDateTime The time to convert.
     * @return The local time converted to EST.
     */
    public static LocalDateTime localToEST(LocalDateTime localDateTime) {
        //Establish EST(Eastern) timezone. Can also use America/Eastern if desired.
        ZoneId EST = ZoneId.of("America/New_York");
        ZoneId localZoneID = ZoneId.systemDefault();

        //Convert local time to EST
        ZonedDateTime currLocalTime = localDateTime.atZone(localZoneID);
        ZonedDateTime currEST = currLocalTime.withZoneSameInstant(EST);
        return currEST.toLocalDateTime();
    }

    /** Compares local time to EST business hours(8AM-10PM).
     * If the time is between 8AM-10PM, true.
     * If the time is not during business hours(8AM-10PM), false.
     * @param localDateTime The local time to compare to EST.
     * @return true or false, if time is between 8AM-10PM or not.
     */
    public static boolean isApptWithinBusinessHrs(LocalDateTime localDateTime) {
        //Start one minute before 8AM to allow appointments to start at 8AM EST.
        //If set to 8AM appointments start time will not work for 8AM.
        LocalTime startTime = LocalTime.of(7,59);
        ZoneId ESTZoneID = ZoneId.of("America/New_York");
        ZonedDateTime ESTStartTime = ZonedDateTime.of(localDateTime.toLocalDate(), startTime, ESTZoneID);

        //End one minute after 10PM to allow appointments to end at 10PM Eastern.
        LocalTime endTime = LocalTime.of(22, 1);
        ZonedDateTime ESTEndTime = ZonedDateTime.of(localDateTime.toLocalDate(), endTime, ESTZoneID);

        LocalDateTime ESTtoCompare = localToEST(localDateTime);
        return ESTtoCompare.isAfter(ChronoLocalDateTime.from(ESTStartTime)) && ESTtoCompare.isBefore(ChronoLocalDateTime.from(ESTEndTime));
    }
}
