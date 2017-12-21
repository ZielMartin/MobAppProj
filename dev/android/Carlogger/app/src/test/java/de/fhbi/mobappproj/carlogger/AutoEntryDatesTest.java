package de.fhbi.mobappproj.carlogger;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Johannes on 16.12.2017.
 */

public class AutoEntryDatesTest {

    private Calendar cur;
    private Calendar lastEntry;
    private ArrayList<Calendar> dates;



    @Before
    public void setUp(){
        cur = Calendar.getInstance();
        lastEntry = Calendar.getInstance();


    }

    @Test
    public void sizeOfListDailyOneMonth() throws Exception {
        //curDate - 1 Month
        lastEntry.add(Calendar.MONTH,-1);
        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.DAILY);
        assertTrue(dates.size()<=31 && dates.size() >= 28);

    }

    @Test
    public void sizeOfListDailyThreeMonth() throws Exception {
        //curDate - 3 Month
        lastEntry.add(Calendar.MONTH,-3);
        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.DAILY);
        assertTrue(dates.size() <= 92 && dates.size() >= 89 );

    }

    @Test
    public void entryDailyOneDay() throws Exception {
        //curDate - 1 day
        lastEntry.add(Calendar.DAY_OF_MONTH,-1);
        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.DAILY);

        Calendar entry = dates.get(0);

        //Entry should be lastEntry + 1 day
        assertEquals(lastEntry.get(Calendar.DAY_OF_MONTH) + 1, entry.get(Calendar.DAY_OF_MONTH) );
    }

    @Test
    public void entryDailyThreeDays() throws Exception {
        //curDate - 3 day
        lastEntry.add(Calendar.DAY_OF_MONTH,-3);

        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.DAILY);

        Calendar entry = dates.get(0);

        //Entry should be lastEntry + 1 day
        assertEquals(lastEntry.get(Calendar.DAY_OF_MONTH) + 1, entry.get(Calendar.DAY_OF_MONTH) );

        entry = dates.get(1);

        //Entry should be lastEntry + 2 day
        assertEquals(lastEntry.get(Calendar.DAY_OF_MONTH) + 2, entry.get(Calendar.DAY_OF_MONTH) );

        entry = dates.get(2);

        Calendar today = Calendar.getInstance();

        //last entry should be today
        assertEquals(today.get(Calendar.DAY_OF_MONTH), entry.get(Calendar.DAY_OF_MONTH) );

    }

    @Test
    public void entryWeeklyOneMonth() throws Exception {

        System.out.println("entryWeeklyOneMonth");
        //curDate - 1 Month
        lastEntry.add(Calendar.MONTH,-1);
        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.WEEKLY);

        //1st entry
        Calendar entry = dates.get(0);
        //should be one month ago
        assertEquals(cur.get(Calendar.MONTH)-1, entry.get(Calendar.MONTH));
        //last field
        entry = dates.get(dates.size()-1);
        //should be cur month
        assertEquals(cur.get(Calendar.MONTH),entry.get(Calendar.MONTH));



    }

    @Test
    public void entryWeeklyThreeMonth() throws Exception {

        System.out.println("entryWeeklyThreeMonth");
        //curDate - 3 Month
        lastEntry.add(Calendar.MONTH,-3);
        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.WEEKLY);

        //1st entry
        Calendar entry = dates.get(0);
        //should be 3 month ago
        assertEquals(cur.get(Calendar.MONTH)-3, entry.get(Calendar.MONTH));
        //last field
        entry = dates.get(dates.size()-1);
        //should be cur month
        assertEquals(cur.get(Calendar.MONTH),entry.get(Calendar.MONTH));

    }

    @Test
    public void entryYearlyTenYears() throws Exception {
        System.out.println("entryYearlyTenYears");
        //curDate - 3 Month
        lastEntry.add(Calendar.YEAR,-10);
        dates = AutoEntryDates.getList(lastEntry, AutoEntryDates.AutoEntry.YEARLY);

        //1st entry
        Calendar entry = dates.get(0);
        //should be 10 years ago
        assertEquals(cur.get(Calendar.YEAR)-10,entry.get(Calendar.YEAR));
        //5th entry
        entry = dates.get(4);
        //should be 6 years ago
        assertEquals(cur.get(Calendar.YEAR)-6,entry.get(Calendar.YEAR));
        //last entry
        entry = dates.get(dates.size()-1);
        //should be this year
        assertEquals(cur.get(Calendar.YEAR),entry.get(Calendar.YEAR));
    }


}
