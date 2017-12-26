package de.fhbi.mobappproj.carlogger.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import de.fhbi.mobappproj.carlogger.AddMenu;
import de.fhbi.mobappproj.carlogger.fragments.FuelFragment;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.fragments.OtherCostFragment;
import de.fhbi.mobappproj.carlogger.fragments.ReminderFragment;
import de.fhbi.mobappproj.carlogger.fragments.RepairFragment;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "MainActivity";

    // Choose authentication providers
    //TODO: fix Rejecting re-init on previously-failed class java.lang.Class<com.firebase.ui.auth.provider.TwitterProvider>: java.lang.NoClassDefFoundError: Failed resolution of: Lcom/twitter/sdk/android/core/Callback
    private static List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());


    //Plus-Button for entry adding
    private AddMenu menuAdd;





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.i(TAG, "user logged in: " + user.getDisplayName());
                // ...
            } else {
                // Sign in failed, check response for error code
                if (response != null)
                    Log.i(TAG, "login failed. response: " + response.toString());
                else Log.i(TAG, "got no response");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        menuAdd = new AddMenu(findViewById(R.id.fabAdd), this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void signIn() {


        // Create and launch sign-in intent
        Intent signinIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        startActivityForResult(signinIntent, RC_SIGN_IN);
    }

    private void signOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthUI authUI = AuthUI.getInstance();

        Task logoutTask = authUI.signOut(this);

        logoutTask.addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.i(TAG, "user logged out: " + user.getDisplayName());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fuel) {
            // Handle the fuel action
            changeFragmentTo(new FuelFragment());
        } else if (id == R.id.nav_otherCosts) {
            changeFragmentTo(new OtherCostFragment());
        } else if (id == R.id.nav_reminder) {
            changeFragmentTo(new ReminderFragment());
        } else if (id == R.id.nav_repair_service) {
            changeFragmentTo(new RepairFragment());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.loginlogout) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                signIn();
                item.setTitle("Logout");
            } else {
                signOut();
                item.setTitle("Login");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragmentTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.MainFrame, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        Log.i(TAG, "fragment switched to " + fragment.getClass());
    }




    @Override
    public void onClick(View view) {

    }
}
