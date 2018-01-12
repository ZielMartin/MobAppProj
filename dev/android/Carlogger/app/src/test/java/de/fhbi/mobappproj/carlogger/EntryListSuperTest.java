package de.fhbi.mobappproj.carlogger;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import de.fhbi.mobappproj.carlogger.DataClasses.list.AllEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.FuelEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.FuelEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.OtherCostEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.OtherCostEntryList;
import de.fhbi.mobappproj.carlogger.DataClasses.entry.RepairEntry;
import de.fhbi.mobappproj.carlogger.DataClasses.list.RepairEntryList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Johannes on 11.01.2018.
 */

public class EntryListSuperTest {


    double totalCost = 1431.7793;
    double totalFuel = 175;
    double totalRepair = 223.75;
    double totalOther = 1033.0293;

    Calendar today;




    @Before
    public void setUp(){

        AllEntryList.getInstance().clear();

        //cost: 75
        FuelEntry fe1 = new FuelEntry();
        fe1.setAmount(50);
        fe1.setCostPerLitre(1.5);
        fe1.setKm(0);
        fe1.setFull(false);

        //cost: 40
        FuelEntry fe2 = new FuelEntry();
        fe2.setAmount(40);
        fe2.setCostPerLitre(1);
        fe2.setKm(100);
        fe2.setFull(true);

        //cost: 60
        FuelEntry fe3 = new FuelEntry();
        fe3.setAmount(60);
        fe3.setCostPerLitre(1);
        fe3.setKm(500);
        fe3.setFull(true);

        //cost: 100
        RepairEntry re1 = new RepairEntry();
        re1.setPartCost(50);
        re1.setLaborCost(50);
        re1.setDescription("asd");

        //cost: 75.75
        RepairEntry re2 = new RepairEntry();
        re2.setPartCost(40.52);
        re2.setLaborCost(35.23);
        re2.setDescription("sdf");

        //cost: 48
        RepairEntry re3 = new RepairEntry();
        re3.setPartCost(23);
        re3.setLaborCost(25);
        re3.setDescription("dfg");

        OtherCostEntry oe1 = new OtherCostEntry();
        oe1.setCost(24.56);

        OtherCostEntry oe2 = new OtherCostEntry();
        oe2.setCost(923.6423);

        OtherCostEntry oe3 = new OtherCostEntry();
        oe3.setCost(84.827);

        Calendar calendar = Calendar.getInstance();

        calendar.set(2017, 01, 01);
        fe1.editCreateTimeCalendar(calendar);


        calendar = Calendar.getInstance();
        calendar.set(2017, 01, 05);
        re1.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 01, 15);
        fe2.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 02, 01);
        fe3.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 02, 12);
        re2.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 03, 05);
        re3.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 03, 06);
        oe1.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 05, 14);
        oe2.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 05, 23);
        oe3.editCreateTimeCalendar(calendar);

        calendar = Calendar.getInstance();
        calendar.set(2017, 06, 25);
        today = calendar;



    }


    @Test
    public void totalCost() throws Exception {
        assertEquals(totalCost, AllEntryList.getInstance().getAllCosts());
    }

    @Test
    public void totalCostFuel() throws Exception {
        assertEquals(totalFuel, FuelEntryList.getInstance().getAllCosts());
    }

    @Test
    public void totalCostRepair() throws Exception {
        assertEquals(totalRepair, RepairEntryList.getInstance().getAllCosts());
    }

    @Test
    public void totalCostOther() throws Exception {
        assertEquals(totalOther, OtherCostEntryList.getInstance().getAllCosts());
    }

    @Test
    public void costPerMonthAll() throws Exception {
        assertEquals(totalCost/5, AllEntryList.getInstance().getCostPerMonth(today));
    }

    @Test
    public void costPerMonthFuel() throws Exception {
        assertEquals(totalFuel/5, FuelEntryList.getInstance().getCostPerMonth(today));
    }

    @Test
    public void costPerMonthRepair() throws Exception {
        assertEquals(totalRepair/5, RepairEntryList.getInstance().getCostPerMonth(today));
    }

    @Test
    public void costPerMonthOther() throws Exception {
        assertEquals(totalOther/3, OtherCostEntryList.getInstance().getCostPerMonth(today));
    }

    @Test
    public void costTimeAll() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, 1,1);
        Calendar end = Calendar.getInstance();
        end.set(2017, 2, 31);

        assertEquals(350.75, AllEntryList.getInstance().getCostTime(start, end));
    }

    @Test
    public void costTimeFuel() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, 1,1);
        Calendar end = Calendar.getInstance();
        end.set(2017, 2, 31);

        assertEquals(175.0, FuelEntryList.getInstance().getCostTime(start, end));
    }

    @Test
    public void costTimeRepair() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, 2,1);
        Calendar end = Calendar.getInstance();
        end.set(2017, 5, 3);

        assertEquals(123.75, RepairEntryList.getInstance().getCostTime(start, end));
    }

    @Test
    public void costTimeOther() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, 2,1);
        Calendar end = Calendar.getInstance();
        end.set(2017, 3, 3);

        assertEquals(0.0, OtherCostEntryList.getInstance().getCostTime(start, end));
    }
}
