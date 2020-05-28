package task.scheduler.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static String parseLocalDate(LocalDate localDate) {
        return DateTimeFormatter.ofPattern("MM/dd/YYYY").format(localDate);
    }

    public static LocalDate getLocalDate(String localDate) {
        return LocalDate.parse(localDate, DateTimeFormatter.ofPattern("MM/dd/uuuu"));
    }
}
