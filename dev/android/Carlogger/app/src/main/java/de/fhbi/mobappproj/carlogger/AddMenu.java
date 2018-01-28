package de.fhbi.mobappproj.carlogger;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.OtherCostAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.ReminderAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.RepairAddActivity;
import de.fhbi.mobappproj.carlogger.activities.MainActivity;
import de.fhbi.mobappproj.carlogger.dataAccess.entryAccess.CarAccess;

/**
 * Created by Johannes on 06.12.2017.
 * extracted from MainActivity
 * <p>
 * initialisation and handling for Plus-Button Add-Menuy
 */


public class AddMenu implements View.OnClickListener {
    public enum FragmentType {REMINDER, REPAIR, FUEL, OTHER, DEFAULT}


    private static final String TAG = "AddMenu";

    private com.github.clans.fab.FloatingActionButton fab_addFuel;
    private com.github.clans.fab.FloatingActionButton fab_addRepairService;
    private com.github.clans.fab.FloatingActionButton fab_addReminder;
    private com.github.clans.fab.FloatingActionButton fab_addOtherCost;

    private Handler mUiHandler = new Handler();
    private FloatingActionMenu menu;
    private MainActivity caller;

    private static int ANIMATIONDELAY = 400;


    public AddMenu(View view, MainActivity caller) {
        this.caller = caller;
        menu = (FloatingActionMenu) view;
        init();

    }

    private void init() {
        addMenuButtons();
        setButtonColor();
        menu.setClosedOnTouchOutside(true);
        menu.hideMenuButton(false);

        setOnClickListener();
        configureAnimation();


    }

    private void configureAnimation() {
        //animation
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CarAccess.getInstance().getCurrentCar() != null)
                    menu.showMenuButton(true);
                else {
                    Toast.makeText(caller, "Bitte zuerst ein Auto wählen/erstellen", Toast.LENGTH_LONG);
                }
            }
        }, ANIMATIONDELAY);
    }

    private void setOnClickListener() {
        fab_addFuel.setOnClickListener(this);
        fab_addRepairService.setOnClickListener(this);
        fab_addReminder.setOnClickListener(this);
        fab_addOtherCost.setOnClickListener(this);
    }

    private void addMenuButtons() {
        fab_addFuel = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addFuel);
        fab_addRepairService = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addRepairService);
        fab_addReminder = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addReminder);
        fab_addOtherCost = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addOtherCost);
    }

    public void setButtonColor() {
        //addButton setup should be done in xml (app_bar_main)

        fab_addFuel.setColorNormal(caller.getResources().getColor(R.color.colorPrimaryFuel));
        fab_addFuel.setColorPressed(caller.getResources().getColor(R.color.colorAccentFuel));
        fab_addRepairService.setColorNormal(caller.getResources().getColor(R.color.colorPrimaryRepair));
        fab_addRepairService.setColorPressed(caller.getResources().getColor(R.color.colorAccentRepair));
        fab_addReminder.setColorNormal(caller.getResources().getColor(R.color.colorPrimaryReminder));
        fab_addReminder.setColorPressed(caller.getResources().getColor(R.color.colorAccentReminder));
        fab_addOtherCost.setColorNormal(caller.getResources().getColor(R.color.colorPrimaryOther));
        fab_addOtherCost.setColorPressed(caller.getResources().getColor(R.color.colorAccentOther));

        FragmentManager fm = caller.getFragmentManager();
        Fragment curFragment = fm.findFragmentByTag(MainActivity.FRAGMENTTAG);

        if (curFragment != null) {
            switch (curFragment.getClass().getSimpleName()) {
                case "FuelFragment":
                    setColors(R.color.colorPrimaryFuel, R.color.colorAccentFuel);
                    break;
                case "OtherFragment":
                    setColors(R.color.colorPrimaryOther, R.color.colorAccentOther);
                    break;
                case "RepairFragment":
                    setColors(R.color.colorPrimaryRepair, R.color.colorAccentRepair);
                    break;
                case "ReminderFragment":
                    setColors(R.color.colorPrimaryReminder, R.color.colorAccentReminder);
                    break;
                default:
                    setColors(R.color.colorPrimary, R.color.colorAccent);
                    break;
            }
        } else {
            setColors(R.color.colorPrimary, R.color.colorAccent);
        }

    }

    public void setColors(int primary, int accent) {
        menu.setMenuButtonColorNormalResId(primary);
        menu.setMenuButtonColorPressedResId(accent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_addReminder:
                view.getContext().startActivity(new Intent(view.getContext(), ReminderAddActivity.class));
                menu.close(false);
                break;
            case R.id.fab_addOtherCost:
                view.getContext().startActivity(new Intent(view.getContext(), OtherCostAddActivity.class));
                menu.close(false);
                break;
            case R.id.fab_addFuel:
                view.getContext().startActivity(new Intent(view.getContext(), FuelAddActivity.class));
                menu.close(false);
                break;
            case R.id.fab_addRepairService:
                view.getContext().startActivity(new Intent(view.getContext(), RepairAddActivity.class));
                menu.close(false);
                break;
        }
    }


}