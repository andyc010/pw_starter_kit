package support;

import java.time.LocalDateTime;

public class DateAndTime {

    public enum Usage {
        FILENAME,
        TEXT;
    }

    public static String displayCurrentDateAndTime(Usage usage_type) {
        LocalDateTime currentDateAndTime = LocalDateTime.now();

        Integer currentMonth = currentDateAndTime.getMonthValue();
        Integer currentDate = currentDateAndTime.getDayOfMonth();
        Integer currentYear = currentDateAndTime.getYear();
        Integer currentHour = currentDateAndTime.getHour();
        Integer currentMinute = currentDateAndTime.getMinute();
        Integer currentSecond = currentDateAndTime.getSecond();

        if(usage_type == Usage.FILENAME) {
            return(appendZeroCharacter(currentYear) + appendZeroCharacter(currentMonth) + appendZeroCharacter(currentDate) + '_'
                + appendZeroCharacter(currentHour) + appendZeroCharacter(currentMinute) + appendZeroCharacter(currentSecond));
        } else {
            return(appendZeroCharacter(currentYear) + "/" + appendZeroCharacter(currentMonth) + "/" + appendZeroCharacter(currentDate) + " "
                + appendZeroCharacter(currentHour) + ":" + appendZeroCharacter(currentMinute) + ":" + appendZeroCharacter(currentSecond));
        }
    }

    private static String appendZeroCharacter(Integer value) {
        if(value < 10) {
            return("0" + value.toString());
        } else {
            return(value.toString());
        }
    }
}