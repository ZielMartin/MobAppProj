package de.fhbi.mobappproj.carlogger.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import de.fhbi.mobappproj.carlogger.R;

public class LoginActivity extends AppCompatActivity {

    public static final int RC_GOOGLE_SIGNIN = 555;
    private static final String TAG = "LoginActivity";
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        setTitle(R.string.loginDialog_title);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        SignInButton signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(v -> signIn(v));

        if(GoogleSignIn.getLastSignedInAccount(this) != null) {
            GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener(task -> {
               Log.i(TAG, "google user signed out");
            });
        }
    }

    private void signIn(View view) {
        Intent signInIntent = GoogleSignIn.getClient(this, gso).getSignInIntent();
        this.startActivityForResult(signInIntent, RC_GOOGLE_SIGNIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GOOGLE_SIGNIN) {
            setResult(RESULT_OK, data);
            finish();
        } else {
            Log.w(TAG, "unknown onActivityResult(requestCode)");
        }
    }
}
