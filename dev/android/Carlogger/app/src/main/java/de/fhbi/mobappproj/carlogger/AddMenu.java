package de.fhbi.mobappproj.carlogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;

import de.fhbi.mobappproj.carlogger.activities.AddActivities.FuelAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.ReminderAddActivity;
import de.fhbi.mobappproj.carlogger.activities.MainActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.OtherCostAddActivity;
import de.fhbi.mobappproj.carlogger.activities.AddActivities.RepairAddActivity;

/**
 * Created by Johannes on 06.12.2017.
 * extracted from MainActivity
 *
 * initialisation and handling for Plus-Button Add-Menuy
 */

public class AddMenu implements View.OnClickListener {

    private static final String TAG = "AddMenu";

    private com.github.clans.fab.FloatingActionButton fab_addFuel;
    private com.github.clans.fab.FloatingActionButton fab_repairService;
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
        setButtonColor();
        addMenuButtons();

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
                menu.showMenuButton(true);
            }
        }, ANIMATIONDELAY);
    }

    private void setOnClickListener() {
        fab_addFuel.setOnClickListener(this);
        fab_repairService.setOnClickListener(this);
        fab_addReminder.setOnClickListener(this);
        fab_addOtherCost.setOnClickListener(this);
    }

    private void addMenuButtons() {
        fab_addFuel = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addFuel);
        fab_repairService = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addRepairService);
        fab_addReminder = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addReminder);
        fab_addOtherCost = (com.github.clans.fab.FloatingActionButton) caller.findViewById(R.id.fab_addOtherCost);
    }

    private void setButtonColor() {
        //addButton setup should be done in xml (app_bar_main)
        menu.setMenuButtonColorNormalResId(R.color.colorPrimary);
        menu.setMenuButtonColorPressedResId(R.color.colorAccent);
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