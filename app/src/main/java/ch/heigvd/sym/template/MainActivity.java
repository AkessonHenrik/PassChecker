/**
 * File     : MainActivity.java
 * Project  : TemplateActivity
 * Author   : Markus Jaton 2 juillet 2014
 * 			  Fabien Dutoit 20 septembre 2016
 *            IICT / HEIG-VD
 *                                       
 * mailto:fabien.dutoit@heig-vd.ch
 * 
 * This piece of code reads a [email_account / password ] combination.
 * It is used as a template project for the SYM module element given at HEIG-VD
 * Target audience : students IL, TS, IE [generally semester 1, third bachelor year]
 *   
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package ch.heigvd.sym.template;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
				/*
				 * There you have to check out if the email/password
				 * combination given is valid or not
				 */
				String mail = email.getText().toString();
				String pass = password.getText().toString(); //TODO read password from EditText
				if (isValid(mail, pass)) {
					/* Ok, valid combination, do something or launch another activity...
					 * The current activity could be finished, but it is not mandatory.
					 * To launch activity MyActivity.class, try something like :
					 * 
					 * 			Intent intent = new Intent(this, ch.heigvd.sym.MyActivity.class);
					 * 			intent.putExtra("emailEntered", mail);
					 *			intent.putExtra("passwordGiven", passwd);
					 *			this.startActivity(intent); 
					 *
					 * Alternately, you could also startActivityForResult if you are awaiting a result.
					 * In the latter case, you have to indicate an int parameter to identify MyActivity
					 * 
					 * If you haven't anything more to do, you may finish()...
					 * But just display a small message before quitting...
					 */
					Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();
//					finish();
				}
			}

		});
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
//		return (mail.equals(validEmail) && passwd.equals(validPassword));
		return false;
	}

	protected void showErrorDialog(String mail, String passwd) {
		/*
		 * Pop-up dialog to show error
		 */
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
