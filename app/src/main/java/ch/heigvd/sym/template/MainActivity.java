/**
 * @authors: Henrik AKESSON & Amel DUSSIER
 */
package ch.heigvd.sym.template;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = MainActivity.class.getSimpleName();

    // Just for test purposes : please destroy !
	private static final String validEmail      = "toto@tutu.com";
	private static final String validPassword   = "tata";
    // GUI elements
	private EditText email      = null;
	private EditText password = null;
	private Button   signIn     = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// Show the welcome screen / login authentication dialog
		setContentView(R.layout.authent);

		// Link to GUI elements
        this.email      = (EditText) findViewById(R.id.email);
        this.signIn     = (Button)   findViewById(R.id.buttOk);
		this.password = (EditText) findViewById(R.id.passwd);
		// Then program action associated to "Ok" button
		signIn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				String mail = email.getText().toString();
				String pass = password.getText().toString(); //TODO read password from EditText
				if (isValid(mail, pass)) {
					Toast.makeText(MainActivity.this, getResources().getString(R.string.goodLogin), Toast.LENGTH_LONG).show();
					Intent intent = new Intent(MainActivity.this, ImageActivity.class);
					intent.putExtra("Email", mail);
					intent.putExtra("Password", pass);
					startActivity(intent);

//					finish();
				}
			}

		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	protected boolean isValid(String mail, String passwd) {

		Context context = getApplicationContext();
		CharSequence text = "Credentials validated, welcome!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		String[][] credentials = {{"admin@admin.com", "admin"},{"henrik@henrik.com", "passHenrik"},{"test@test.com", "test"}};
		//Check that password is in this format: str@str.str
		if(mail == null || passwd == null) {

			toast.setText("Please enter an email address and a password");
			toast.show();

		} else if(!(mail.contains("@")&&mail.contains("."))) {
			toast.setText("Invalid email syntax");
			toast.show();
		} else {
			Boolean exists = false;
			for(int i = 0; i < credentials.length; i++) {
				if(mail.equals(credentials[i][0]) && passwd.equals(credentials[i][1]))
					exists = true;
			}
			if(exists) {
				//Handle success

				return true;
			} else {
				showErrorDialog(mail, passwd);
				email.setText(null);
				password.setText(null);
			}
		}

		// Return true if combination valid, false otherwise
		return false;
	}

	protected void showErrorDialog(String mail, String passwd) {

		AlertDialog.Builder alertbd = new AlertDialog.Builder(this);
        alertbd.setIcon(android.R.drawable.ic_dialog_alert);
		alertbd.setTitle(R.string.wronglogin);
	    alertbd.setMessage(R.string.wrong);
	    alertbd.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            // we do nothing...
                // dialog close automatically
	        }
	     });
	    alertbd.show();
	}

}
