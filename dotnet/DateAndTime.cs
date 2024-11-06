/* DateAndTime.cs

Introduction: Just as in the date_and_time.ts file for TypeScript in the nodejs folder of this pw_starter_kit folder,
This file contains a function and an enum that can be used to generate text that can be used to show the current date
and time to be used as input for a UI test, or it can be used for files to be saved (screenshots, logs, etc.)

*/
using System;

namespace PlaywrightTests;

public class DateAndTime
{
    public enum Usage
    {
        filename,
        text,
    }

    // Return a string that has the latest date & time, but different strings depending on what it is used for
    public static string displayCurrentDateAndTime(Usage usage_type)
    {
        // Get the current date & time
        DateTime currentDate = DateTime.Now;

        int currentMonth = currentDate.Month;
        int currentDay = currentDate.Day;
        int currentYear = currentDate.Year;
        int currentHour = currentDate.Hour;
        int currentMinute = currentDate.Minute;
        int currentSecond = currentDate.Second;

        if(usage_type == Usage.filename)
        {
            return(appendZeroCharacter(currentYear) + appendZeroCharacter(currentMonth) + appendZeroCharacter(currentDay) + "_"
                + appendZeroCharacter(currentHour) + appendZeroCharacter(currentMinute) + appendZeroCharacter(currentSecond));
        }
        else
        {
            return(appendZeroCharacter(currentYear) + "/" + appendZeroCharacter(currentMonth) + "/" + appendZeroCharacter(currentDay) + " "
                + appendZeroCharacter(currentHour) + ":" + appendZeroCharacter(currentMinute) + ":" + appendZeroCharacter(currentSecond));
        }
    }   

    // Method to add a "0" character in front for values that are 0-9
    private static string appendZeroCharacter(int value)
    {
        if(value < 10)
        {
            return("0" + value.ToString());
        }
        else
        {
            return(value.ToString());
        }
    }
}