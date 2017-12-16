package de.fhbi.mobappproj.carlogger.DataClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * call these methods with the Date of the last entry where AutoEntry is true to get a list of dates till now
 */
public class AutoEntryDates {

    public enum AutoEntry {
        DAILY , WEEKLY, MONTHLY, YEARLY, EVERYTWOMONTH, EVERYTHREEMONTH
    }

    public static int DAILYINT = 1, WEEKLYINT = 7, MONTHLYINT = 30, YEARLYINT = 356, EVERYTWOMONTH = 60, EVERYTHREEMONTH = 90;

    public static ArrayList<Calendar> getList(Calendar lastEntryCalendar, AutoEntry entry){
        switch (entry){
            case DAILY:
                return getIntervallList(lastEntryCalendar,DAILYINT);

            case WEEKLY:
                return getIntervallList(lastEntryCalendar,WEEKLYINT);

            case MONTHLY:
                return  getIntervallList(lastEntryCalendar, MONTHLYINT);

            case YEARLY:
                return getIntervallList(lastEntryCalendar,YEARLYINT);

            case EVERYTWOMONTH:
                return getIntervallList(lastEntryCalendar, EVERYTWOMONTH);

            case EVERYTHREEMONTH:
                return getIntervallList(lastEntryCalendar, EVERYTHREEMONTH);

            default:
                return null;
        }
    }



    //intervall in days
    private static ArrayList<Calendar> getIntervallList(Calendar lastEntryCalendar, int intervall) {
        ArrayList<Calendar> list = new ArrayList<Calendar>();
        Calendar cur = Calendar.getInstance();

        long end = cur.getTimeInMillis();
        long start = lastEntryCalendar.getTimeInMillis();
        long days = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));

        for(int i = 1; i <= days; i+=intervall){
            Calendar newCal = (Calendar) lastEntryCalendar.clone();
            newCal.add(Calendar.DAY_OF_MONTH,i);
            list.add(newCal);
        }

        return list;
    }


}
