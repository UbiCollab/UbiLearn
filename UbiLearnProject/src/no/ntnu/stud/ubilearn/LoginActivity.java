package no.ntnu.stud.ubilearn;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.security.auth.callback.Callback;

import no.ntnu.stud.ubilearn.parse.CallbackTest;
import no.ntnu.stud.ubilearn.parse.SyncContent;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	protected static final String InputMethodManager = null;
	private static boolean isInit = false;
	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private ParseUser user;
	private Activity pointerHax;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pointerHax = this;
		//initializes Parse with its keys 
		if (!isInit) {			
			Parse.initialize(this, "LSFbjJtg93wCMZGCbVibMVL2cSnl0mq7OTaEqm7W", "TuxF6aioePOXrBROvhbwrBJ2Z4kOb5PGMoXyU8lo");
			isInit = true;
		}
		setContentView(R.layout.activity_login);
		user = new ParseUser();
		//checks if the user is already logged in
		if (ParseUser.getCurrentUser() != null) {
			startMain(null);
		}
		
		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		
		
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							login(false);
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
						login(false);
					}
				});
		findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
				login(true);
			}
		});
	}
	
	//used by skip button
	public void skip(View view){
		mEmail = "test@test.com";
		mPassword = "test";
		mLoginStatusMessageView.setText(R.string.loggInn);
		showProgress(true);
		attemptLogin();
//		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
//		ParseUser.logInInBackground("test@test.com", "test", new LogInCallback() {
//			
//			@Override
//			public void done(ParseUser user, ParseException e) {
//				if (e == null) {
//					Log.v("Login", "skip with user");
//					startMain(null);
//				}else{
//					showProgress(false);
//					Toast.makeText(pointerHax, "Could not login", Toast.LENGTH_LONG).show();
//				}
//			}
//		});
//		showProgress(true);
	}
	
	public void startMain(View view) 
	{
	    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If singUp is true, signup will be performed instead of login. 
	 */
	public void login(boolean signUp) {

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			if (!signUp) {
				attemptLogin();							
			}else {
				attemptSignup();
			}
		}
	}

	private void attemptLogin() {
		
		mLoginStatusMessageView.setText(R.string.loggInn);
		showProgress(true);
		ParseUser.logInInBackground(mEmail, mPassword, new LogInCallback() {
			
			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					showProgress(false);
					mLoginStatusMessageView.setText(R.string.newContent);
					showProgress(true);
					SyncContent.fetchDataBeforeLogin(pointerHax, new CallbackTest() {
						
						@Override
						public void done(Exception e) {
							if (e == null) {
								startMain(null);								
							}else{
								Log.v("Login", e.getMessage());
							}
						}
					});
//					SyncContent.calculateLastUpdate();
//					SyncContent.fetchCasePatient(pointerHax);
//					SyncContent.fetchTrainingProgress();
//					SyncContent.fetchQuizesAfterUpdate(pointerHax);
//					SyncContent.updateExerciseImages(pointerHax);
//					startMain(null);
				}else{
					showProgress(false);
					Toast.makeText(pointerHax, "Kunne ikke logge inn", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void attemptSignup() {
		mLoginStatusMessageView.setText(R.string.registering);
		showProgress(true);
		user.setUsername(mEmail);
		user.setEmail(mEmail);
		user.setPassword(mPassword);
		user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				if (e == null) {
					showProgress(false);
					Toast.makeText(pointerHax, "Registrasjon velykket", Toast.LENGTH_LONG).show();
					//startMain(null);
				}else{
					showProgress(false);
					Toast.makeText(pointerHax, "Registrasjon mislykket", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}
