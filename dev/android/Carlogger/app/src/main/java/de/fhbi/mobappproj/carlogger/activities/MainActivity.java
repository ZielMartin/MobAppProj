package de.fhbi.mobappproj.carlogger.activities;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import de.fhbi.mobappproj.carlogger.AddMenu;
import de.fhbi.mobappproj.carlogger.R;
import de.fhbi.mobappproj.carlogger.fragments.AllFragment;
import de.fhbi.mobappproj.carlogger.fragments.FuelFragment;
import de.fhbi.mobappproj.carlogger.fragments.OtherCostFragment;
import de.fhbi.mobappproj.carlogger.fragments.ReminderFragment;
import de.fhbi.mobappproj.carlogger.fragments.RepairFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static final int RC_LOGIN_ACTIVITY = 555;

    //Plus-Button for entry adding
    private AddMenu menuAdd;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FragmentManager fragmentManager = getFragmentManager();
    private boolean pressedBackAlready = false;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN_ACTIVITY) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            Log.w(TAG, "unknown requestCode");
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem logoutLogin = navigationView.getMenu().findItem(R.id.loginlogout);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser.isAnonymous())
            logoutLogin.setTitle(getString(R.string.login));
        else
            logoutLogin.setTitle(getString(R.string.logout));

        View header = navigationView.getHeaderView(0);
        Button button_chooseCar = header.findViewById(R.id.button_chooseCar);
        
        button_chooseCar.setOnClickListener(view -> {
            Log.i(TAG, "chooseCar");
        });

        changeFragmentTo(new AllFragment());
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() == 0) {
            if (pressedBackAlready) {
                super.onBackPressed();
                return;
            }
            this.pressedBackAlready = true;
            Toast.makeText(this, R.string.pressBackAgain, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> pressedBackAlready = false, 2000);

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
            this.setTitle(R.string.fuelFragment_title);
        } else if (id == R.id.nav_otherCosts) {
            changeFragmentTo(new OtherCostFragment());
            this.setTitle(R.string.otherCostFragment_title);
        } else if (id == R.id.nav_reminder) {
            changeFragmentTo(new ReminderFragment());
            this.setTitle(R.string.reminder_fragment_title);
        } else if (id == R.id.nav_repair_service) {
            changeFragmentTo(new RepairFragment());
            this.setTitle(R.string.repairFragment_title);
        } else if (id == R.id.nav_all) {
            changeFragmentTo(new AllFragment());
            this.setTitle(R.string.nav_all);
        } else if (id == R.id.loginlogout) {
            if (firebaseAuth.getCurrentUser() == null || firebaseAuth.getCurrentUser().isAnonymous()) {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                this.startActivityForResult(loginIntent, RC_LOGIN_ACTIVITY);
            } else {
                firebaseAuth.signOut();

                firebaseAuth.signInAnonymously().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        NavigationView navigationView = findViewById(R.id.nav_view);
                        MenuItem logoutItem = navigationView.getMenu().findItem(R.id.loginlogout);
                        logoutItem.setTitle(getString(R.string.login));
                        Log.d(TAG, "logged in user anonymously");
                    } else {

                    }
                });
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragmentTo(Fragment fragment) {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.MainFrame, fragment);
//        fragmentTransaction.addToBackStack(null);


        fragmentTransaction.commit();
        Log.v(TAG, "fragment switched to " + fragment.getClass());
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "linkWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();
                    } else {
                        Log.w(TAG, "linkWithCredential:failure (probably already linked)", task.getException());
//                        Toast.makeText(MainActivity.this, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
                    }
                });

        if (firebaseAuth.getCurrentUser() != null)
            firebaseAuth.signOut();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            NavigationView navigationView = findViewById(R.id.nav_view);
                            MenuItem loginItem = navigationView.getMenu().findItem(R.id.loginlogout);
                            loginItem.setTitle(getString(R.string.logout));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }
}
