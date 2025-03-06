package utils;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The `Time` class provides methods to manage time-related operations and generate time options for ComboBoxes.
 * It includes methods to generate lists of start and end times in 15-minute intervals based on a specified time range.
 */
public class Time {
    private static ObservableList<LocalTime> startLocalTime = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endLocalTime = FXCollections.observableArrayList();

    /**
     * Generates time options for ComboBoxes, starting from 8:00 AM and ending at 10:00 PM, in 15-minute intervals.
     * The times are adjusted to the system's default time zone.
     * The generated start times and end times are stored in observable lists.
     */
    private static void timeForComboBox() {
        ZonedDateTime easternStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        LocalTime localstart = easternStart.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
        LocalTime localEnd = localstart.plusHours(14);

        while (localstart.isBefore(localEnd)) {
            startLocalTime.add(localstart);
            localstart = localstart.plusMinutes(15);
            endLocalTime.add(localstart);
        }
    }

    /**
     * Retrieves the list of start times for ComboBoxes.
     * If the list is empty, it generates the list using the `timeForComboBox` method.
     *
     * @return An observable list of start times.
     */
    public static ObservableList<LocalTime> startTime() {
        if (startLocalTime.isEmpty()) {
            timeForComboBox();
        }
        return startLocalTime;
    }

    /**
     * Retrieves the list of end times for ComboBoxes.
     * If the list is empty, it generates the list using the `timeForComboBox` method.
     *
     * @return An observable list of end times.
     */
    public static ObservableList<LocalTime> endTime() {
        if (endLocalTime.isEmpty()) {
            timeForComboBox();
        }
        return endLocalTime;
    }
}
