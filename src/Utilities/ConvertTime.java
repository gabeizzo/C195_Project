package Utilities;

import javafx.scene.control.ComboBox;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;

/** This is the ConvertTime class which is used to format times and dates throughout the application.
 */
public class ConvertTime {
    /** Formats a localDateTime to month(MMMM), day(dd) and year(yyyy).
     * @param localDT LocalDateTime
     * @return The date to display.
     */
    public static String dateFormatted(LocalDateTime localDT){
        DateTimeFormatter displayDate = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return displayDate.format(localDT);

    }
    /** Formats localDateTime to 12 hour AM/PM time.
     * @param localDT LocalDateTime
     * @return String localDT
     */
    public static String timeFormatted(LocalDateTime localDT) {
        DateTimeFormatter displayTime = DateTimeFormatter.ofPattern("h:mm a");
        return displayTime.format(localDT);
    }
    /** Formats LocalTime to 12 hour AM/PM time.
     * @param localTime LocalTime
     * @return String formatted localTime
     */
    public static String timeFormatted(LocalTime localTime) {
        DateTimeFormatter displayLT = DateTimeFormatter.ofPattern("hh:mm a");
        return displayLT.format(localTime);
    }
    /** Formats ZonedDateTime to 12 hour AM/PM time.
     * @param zonedDT zonedDateTime
     * @return String formatted zonedDateTime
     */
    public static String timeFormatted(ZonedDateTime zonedDT) {
        DateTimeFormatter displayZonedDT = DateTimeFormatter.ofPattern("hh:mm a");
        return displayZonedDT.format(zonedDT);
    }
    /** Fills combo box with times.
     * @param localTimeCB
     * @param apptStartTime
     * @param apptEndTime
     * @return ComboBox<LocalTime>
     */
    public static ComboBox<LocalTime> displayValidTimes(ComboBox<LocalTime> localTimeCB, LocalTime apptStartTime, LocalTime apptEndTime) {
        while(apptStartTime.isBefore(apptEndTime.plusSeconds(1))) {
            localTimeCB.getItems().add(apptStartTime);
            apptStartTime = apptEndTime.plusMinutes(15);
        }
        return localTimeCB;
    }

    /** Converts local time to EST.
     * @param convertTime LocalDateTime
     * @return The local time converted to EST.
     */
    public static LocalDateTime localToEST(LocalDateTime convertTime) {
        // Eastern Time Zone
        ZoneId EST = ZoneId.of("America/Boston");
        ZoneId localZoneID = ZoneId.systemDefault();
        // convert to eastern time
        ZonedDateTime currLocalTime = convertTime.atZone(localZoneID);
        ZonedDateTime currEST = currLocalTime.withZoneSameInstant(EST);

        return currEST.toLocalDateTime();
    }

    /** Compares local time to EST business hours(8AM-10PM). If the time is between 8AM-10PM, boolean returns true.
     * If the time is not during business hours(8AM-10PM), boolean returns false.
     * @param compareTime LocalDateTime
     * @return boolean, true or false depending on where the local hour falls between.
     */
    public static boolean compareLToESTBizHrs(LocalDateTime compareTime) {

        LocalTime startTime = LocalTime.of(7,59);
        ZoneId ESTZoneID = ZoneId.of("America/Boston");
        ZonedDateTime ESTStartTime = ZonedDateTime.of(compareTime.toLocalDate(), startTime, ESTZoneID);
        System.out.println("EST start time " + ESTStartTime + " " + ESTStartTime.toLocalDateTime());

        LocalTime endTime = LocalTime.of(22, 0);
        ZonedDateTime ESTEndTime = ZonedDateTime.of(compareTime.toLocalDate(), endTime, ESTZoneID);
        System.out.println("EST end time " + ESTEndTime + " " + ESTEndTime.toLocalDateTime());

        LocalDateTime ESTtoCompare = localToEST(compareTime);

        return ESTtoCompare.isAfter(ChronoLocalDateTime.from(ESTStartTime)) && ESTtoCompare.isBefore(ChronoLocalDateTime.from(ESTEndTime));
    }
}
