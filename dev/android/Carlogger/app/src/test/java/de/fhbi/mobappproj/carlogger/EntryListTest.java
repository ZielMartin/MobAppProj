package de.fhbi.mobappproj.carlogger;

import org.junit.Before;
import org.junit.Test;

import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.ReminderEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.RepairEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.FuelEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.OtherCostEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.ReminderEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.list.RepairEntryList;
import de.fhbi.mobappproj.carlogger.listEntryAdapter.OtherCostAdapter;

import static org.junit.Assert.assertEquals;


public class EntryListTest {

    private FuelEntry fe1, fe2;

    private RepairEntry re1;

    private OtherCostEntry oe1, oe2, oe3, oe4;



    @Before
    public void setUp(){

        AllEntryList.getInstance().clear();

        fe1 = new FuelEntry();
        fe1.setAmount(20);
        fe1.setCostPerLitre(0.5);
        fe1.setFull(true);
        fe1.setKm(100);

        fe2 = new FuelEntry();
        fe2.setAmount(50.2);
        fe2.setCostPerLitre(1.5);
        fe2.setFull(false);
        fe2.setKm(100);

        re1 = new RepairEntry();
        re1.setPartCost(20);
        re1.setLaborCost(50);
        re1.setDescription("test");

        oe1 = new OtherCostEntry();
        oe1.setDescription("test");
        oe1.setCost(50.99);

        oe2 = new OtherCostEntry();
        oe2.setDescription("test");
        oe2.setCost(50.99);

        oe3 = new OtherCostEntry();
        oe3.setDescription("test");
        oe3.setCost(50.99);

        oe4 = new OtherCostEntry();
        oe4.setDescription("test");
        oe4.setCost(50.99);

    }

    @Test
    public void testSizeFuel() throws Exception {
        assertEquals(2, FuelEntryList.getInstance().getAllEntries().size());
    }

    @Test
    public void testSizeRepair() throws Exception {
        assertEquals(1, RepairEntryList.getInstance().getAllEntries().size());
    }

    @Test
    public void testSizeOther() throws Exception {
        assertEquals(4, OtherCostEntryList.getInstance().getAllEntries().size());
    }

    @Test
    public void testSizeAll() throws Exception {
        assertEquals(7, AllEntryList.getInstance().getAllEntries().size());
    }

    @Test
    public void testEntriesFuel() throws Exception {
        assertEquals(fe1, FuelEntryList.getInstance().getAllEntries().get(0));
        assertEquals(fe2, FuelEntryList.getInstance().getAllEntries().get(1));
    }

    @Test
    public void testEntriesRepair() throws Exception {
        assertEquals(re1, RepairEntryList.getInstance().getAllEntries().get(0));
    }



    @Test
    public void testEntriesOther() throws Exception {
        assertEquals(oe1, OtherCostEntryList.getInstance().getAllEntries().get(0));
        assertEquals(oe2, OtherCostEntryList.getInstance().getAllEntries().get(1));
        assertEquals(oe3, OtherCostEntryList.getInstance().getAllEntries().get(2));
        assertEquals(oe4, OtherCostEntryList.getInstance().getAllEntries().get(3));
    }
}