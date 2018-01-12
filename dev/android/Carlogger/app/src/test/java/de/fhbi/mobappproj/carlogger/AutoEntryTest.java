package de.fhbi.mobappproj.carlogger;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.AutoEntryDates;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Johannes on 12.01.2018.
 */

public class AutoEntryTest {

    @Before
    public void setUp(){

        AllEntryList.getInstance().clear();

        FuelEntry fe = new FuelEntry();
        fe.setCostPerLitre(1.2);
        fe.setAmount(50);
        fe.setAutoEntry(AutoEntryDates.AutoEntry.DAILY);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,0,1);

        fe.editCreateTimeCalendar(calendar);

        AllEntryList.getInstance().setAutoEntries();

    }
    @Test
    public void testOriginal() throws Exception {
        FuelEntry fe = (FuelEntry) AllEntryList.getInstance().getAllEntries().get(0);
        assertEquals(50.0, fe.getAmount());
        assertEquals(1.2, fe.getCostPerLitre());
        assertEquals(2017, fe.getCreateTimeCalendar().get(Calendar.YEAR));
        assertEquals(0, fe.getCreateTimeCalendar().get(Calendar.MONTH));
        assertEquals(1, fe.getCreateTimeCalendar().get(Calendar.DATE));
        assertEquals(false, fe.isLastEntry());
    }

    @Test
    public void testFirst() throws Exception {
        FuelEntry fe = (FuelEntry) AllEntryList.getInstance().getAllEntries().get(1);
        assertEquals(50.0, fe.getAmount());
        assertEquals(1.2, fe.getCostPerLitre());
        assertEquals(2017, fe.getCreateTimeCalendar().get(Calendar.YEAR));
        assertEquals(0, fe.getCreateTimeCalendar().get(Calendar.MONTH));
        assertEquals(2, fe.getCreateTimeCalendar().get(Calendar.DATE));
        assertEquals(false, fe.isLastEntry());
    }

    @Test
    public void testSecond() throws Exception {
        FuelEntry fe = (FuelEntry) AllEntryList.getInstance().getAllEntries().get(2);
        assertEquals(50.0, fe.getAmount());
        assertEquals(1.2, fe.getCostPerLitre());
        assertEquals(2017, fe.getCreateTimeCalendar().get(Calendar.YEAR));
        assertEquals(0, fe.getCreateTimeCalendar().get(Calendar.MONTH));
        assertEquals(3, fe.getCreateTimeCalendar().get(Calendar.DATE));
        assertEquals(false, fe.isLastEntry());
    }

    @Test
    public void testThird() throws Exception {
        FuelEntry fe = (FuelEntry) AllEntryList.getInstance().getAllEntries().get(3);
        assertEquals(50.0, fe.getAmount());
        assertEquals(1.2, fe.getCostPerLitre());
        assertEquals(2017, fe.getCreateTimeCalendar().get(Calendar.YEAR));
        assertEquals(0, fe.getCreateTimeCalendar().get(Calendar.MONTH));
        assertEquals(4, fe.getCreateTimeCalendar().get(Calendar.DATE));
        assertEquals(false, fe.isLastEntry());
    }

    @Test
    public void testLast() throws Exception {
        FuelEntry fe = (FuelEntry) AllEntryList.getInstance().getAllEntries().get(AllEntryList.getInstance().getAllEntries().size()-1);
        assertEquals(50.0, fe.getAmount());
        assertEquals(1.2, fe.getCostPerLitre());
        assertEquals(Calendar.getInstance().get(Calendar.YEAR), fe.getCreateTimeCalendar().get(Calendar.YEAR));
        assertEquals(Calendar.getInstance().get(Calendar.MONTH), fe.getCreateTimeCalendar().get(Calendar.MONTH));
        assertEquals(Calendar.getInstance().get(Calendar.DATE), fe.getCreateTimeCalendar().get(Calendar.DATE));
        assertEquals(true, fe.isLastEntry());
    }
}
