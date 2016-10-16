/**
 * @authors: Henrik AKESSON & Amel DUSSIER
 */
package ch.heigvd.sym.template;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import java.io.File;

public class ImageActivity extends AppCompatActivity {

    //IMEI needs to be shown only once, otherwise it is shown everytime the phone changes orientation
    private static boolean IMEIShown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showPicture();
        if(!IMEIShown) {
            showIMEI();
            IMEIShown = true;
        }
        System.out.println("On Start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("On pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("On resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("On Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("On Destroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("On Stop");
    }

    private void showPicture() {
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        System.out.println("Files:");
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        for(File file : f.listFiles()) {
            System.out.println("File: " + file.getName());

            File imgFile = new  File(file.getAbsolutePath());

            if(imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }

        }
    }



    protected void showIMEI() {
        String identifier = null;
        TelephonyManager tm = (TelephonyManager)ImageActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null)
            identifier = tm.getDeviceId();
        if (identifier == null || identifier .length() == 0)
            identifier = Settings.Secure.getString(ImageActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("ID = " + identifier);
        AlertDialog.Builder alertbd = new AlertDialog.Builder(this);
        alertbd.setIcon(android.R.drawable.ic_dialog_alert);
        alertbd.setTitle("IMEI");
        alertbd.setMessage(identifier);
        alertbd.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // we do nothing...
                // dialog close automatically
            }
        });
        alertbd.show();
    }
}
