package com.andela.hackathon.azera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

			GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
					.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
					.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
					.build();

    }
}
