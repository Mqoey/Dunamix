package afm.drc.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import afm.drc.dunamix.R;

public class CreateUser extends AppCompatActivity {

    TextView signIn;
    EditText regEmail, regName, regPassword;
    Button regUser;
    ProgressBar regProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        regEmail=findViewById(R.id.regEmail);
        regPassword=findViewById(R.id.regPassword);
        regName=findViewById(R.id.regName);
        regUser = findViewById(R.id.regUser);
        regProgressBar=findViewById(R.id.regProgressBar);
        signIn = findViewById(R.id.regSignIn);

        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    //GET VALUES
                    String name = regName.getText().toString();
                    String email = regEmail.getText().toString();
                    String password = regPassword.getText().toString();

                    SpiritualTeacher s = new SpiritualTeacher(name, email,password);

                    //upload data to mysql
                    new MyUploader(CreateUser.this).upload(s, regName, regEmail, regPassword);

                } else {
                    Toast.makeText(CreateUser.this, "PLEASE ENTER ALL FIELDS CORRECTLY ", Toast.LENGTH_LONG).show();
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Fragment fragment = null;
                fragment = new ChatLogin();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment).commit();*/

                finish();
            }
        });
    }

    /*
    Our data object. THE POJO CLASS
     */
    static class SpiritualTeacher{
        private String name, email, password;
        public SpiritualTeacher(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
        public String getName() {return name;}
        public String getEmail() {return email;}
        public String getPassword() {return password;}
    }
    /*
    CLASS TO UPLOAD BOTH IMAGES AND TEXT
     */
    public class MyUploader {
        private static final String DATA_UPLOAD_URL="http://dunamixapp.000webhostapp.com/register.php";

        //INSTANCE FIELDS
        private final Context c;
        public MyUploader(Context c) {this.c = c;}
        /*
        SAVE/INSERT
         */
        public void upload(SpiritualTeacher s, final View...inputViews)
        {
            if(s == null){Toast.makeText(c, "No Data To Save", Toast.LENGTH_SHORT).show();}
            else {

                regProgressBar.setVisibility(View.VISIBLE);

                AndroidNetworking.upload(DATA_UPLOAD_URL)
                        .addMultipartParameter("name",s.getName())
                        .addMultipartParameter("email",s.getEmail())
                        .addMultipartParameter("password",s.getPassword())
                        .setTag("MYSQL_UPLOAD")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response != null) {
                                    try{
                                        //SHOW RESPONSE FROM SERVER
                                        String responseString = response.get("message").toString();

                                        if (responseString.equalsIgnoreCase("Success")) {
                                            //RESET VIEWS
                                            Toast.makeText(c, "PHP SERVER RESPONSE : " + responseString, Toast.LENGTH_LONG).show();
                                            EditText name = (EditText) inputViews[0];
                                            EditText email = (EditText) inputViews[1];
                                            EditText password = (EditText) inputViews[2];
                                            EditText password2 = (EditText) inputViews[3];

                                            name.setText("");
                                            email.setText("");
                                            password.setText("");
                                            password2.setText("");

                                        } else {
                                            Toast.makeText(c, "PHP WASN'T SUCCESSFUL. ", Toast.LENGTH_LONG).show();
                                        }
                                    }catch(Exception e)
                                    {
                                        e.printStackTrace();
                                        Toast.makeText(c, "JSONException "+e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(c, "NULL RESPONSE. ", Toast.LENGTH_LONG).show();
                                }
                                regProgressBar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError(ANError error) {
                                error.printStackTrace();
                                regProgressBar.setVisibility(View.GONE);
                                Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : \n"+error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }

    /*
    Perform basic data validation
     */
    private boolean validateData()
    {
        String name=regName.getText().toString();
        String email=regEmail.getText().toString();
        String password=regPassword.getText().toString();
        return !name.equals("") && !(email.equals("") | password.equals(""));
    }
}