package afm.drc.online_payments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import afm.drc.dunamix.R;


/**
 * Created by mqondisi on 7/27/20.
 */

public class Others extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherpayments_main);

        Button send = (Button) findViewById(R.id.send2);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            } else {
//If the app doesn’t have the CALL_PHONE permission, request it//
                requestPermission();
            }
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    final EditText amount = (EditText) findViewById(R.id.amo);
                    final EditText mechant = (EditText) findViewById(R.id.merchant);
                    String pay = amount.getText().toString();
                    String mechanty = mechant.getText().toString();
                    if (!TextUtils.isEmpty(pay)) {
                        String dial = "tel:*151*2*2*"+mechanty+"*" + pay + "%23";
                        if (ActivityCompat.checkSelfPermission(Others.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }
                    else Toast.makeText(Others.this,
                            "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkPermission() {
        int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        return CallPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Others.this, new String[]
                {
                        Manifest.permission.CALL_PHONE
                }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                Button callButton = findViewById(R.id.send);
                if (grantResults.length > 0) {
                    boolean CallPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (CallPermission) {
                        Toast.makeText(Others.this,
                                "Permission accepted", Toast.LENGTH_LONG).show();
//If the permission is denied….//
                    } else {
                        Toast.makeText(Others.this,
//...display the following toast...//
                                "Permission denied", Toast.LENGTH_LONG).show();
//...and disable the call button.//
                        callButton.setEnabled(false);
                    }
                    break;
                }
        }
    }
}