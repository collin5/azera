package com.andela.hackathon.azera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.hackathon.azera.main.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.scanlibrary.ProgressDialogFragment;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
	private static final String TAG = "Login";
	private GoogleApiClient mGoogleApiClient;
	private int RC_SIGN_IN = 200;
	private TextView mStatusText;
	private FirebaseAuth mAuth;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

				mAuth = FirebaseAuth.getInstance();
				mStatusText = (TextView) findViewById(R.id.txt_status_text);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

				mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
					.build();

			SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);

			signInButton.setOnClickListener(this);

    }

		@Override
		public void onStart() {
			super.onStart();
			mGoogleApiClient.connect();
			// Check if user is signed in (non-null) and update UI accordingly.
			FirebaseUser currentUser = mAuth.getCurrentUser();

			// send user to MainActivity if authenticated

			if (currentUser !=null){
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
			updateUI(true);
		}

		@Override
		public void onStop() {
			super.onStop();
			mGoogleApiClient.disconnect();
		}

		@Override public void onClick(View view) {
    	if (view.getId() == R.id.sign_in_button) {
    		signIn();
			}
		}

		public void signIn() {
    	Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
			startActivityForResult(signInIntent, RC_SIGN_IN);
		}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			handleSignInResult(result);
		}
	}

	private void handleSignInResult(GoogleSignInResult result) {
		Log.d(TAG, "handleSignInResult:" + result.isSuccess());
		if (result.isSuccess()) {
			// Signed in successfully, show authenticated UI.
			GoogleSignInAccount acct = result.getSignInAccount();

			mStatusText.setText(acct.getDisplayName());
			String email = acct.getEmail();

			if (Objects.equals(email.substring(email.indexOf("@") + 1), "andela.com")) {
				firebaseAuthWithGoogle(acct);

				Auth.GoogleSignInApi.signOut(mGoogleApiClient);
				Toast.makeText(LoginActivity.this, "Only Andela emails can be used!",
						Toast.LENGTH_SHORT).show();
				updateUI(false);
			}

		} else {
			// Sign in failed, show error page
			mStatusText.setText(R.string.login_error);
			updateUI(false);
		}
	}

	private void updateUI(boolean status) {
		if (status) {
			mStatusText.setText("Going to change UI");
			Toast.makeText(LoginActivity.this, "Authentication successful",
					Toast.LENGTH_SHORT).show();
		} else {
			mStatusText.setText("Failed");
		}
	}

	private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
		final ConstraintLayout layout = findViewById(R.id.layout_Signin);
		layout.setVisibility(View.GONE);
		final ProgressDialog dialog = ProgressDialog.show(this, "Please wait","we are logging you in ...");
		dialog.setCancelable(true);
		dialog.show();
		Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInWithCredential:success");
							FirebaseUser user = mAuth.getCurrentUser();
							createUserObj(user);
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							LoginActivity.this.startActivity(intent);
						} else {
							// If sign in fails, display a message to the user.
							Log.w(TAG, "signInWithCredential:failure", task.getException());
							Toast.makeText(LoginActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							updateUI(false);
						}
						dialog.hide();
					}
				});
	}

	private void createUserObj(FirebaseUser user) {

	}

}
