package afm.drc.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import afm.drc.dunamix.R;

public class ChatLogin extends Fragment {
    TextView create_user;
    Button lgnButton;
    EditText lgnEmail, lgnPassword;
    ProgressBar lgnProgressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        create_user = root.findViewById(R.id.create_user);
        lgnEmail = root.findViewById(R.id.lgnEmail);
        lgnPassword=root.findViewById(R.id.lgnPassword);
        lgnProgressBar=root.findViewById(R.id.lgnProgressBar);
        lgnButton=root.findViewById(R.id.lgnButton);

        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateUser.class);
                startActivity(intent);
            }
        });

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    //GET VALUES
                    String email = lgnEmail.getText().toString();
                    String password = lgnPassword.getText().toString();

                    ChatLogin.SpiritualTeacher s = new ChatLogin.SpiritualTeacher(email,password);

                    //upload data to mysql
                    new ChatLogin.MyUploader(getContext()).upload(s, lgnEmail, lgnPassword);

                } else {
                    Toast.makeText(getContext(), "PLEASE ENTER ALL FIELDS CORRECTLY ", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }

    /*
    Our data object. THE POGO CLASS
     */
    static class SpiritualTeacher{
        private String email, password;
        public SpiritualTeacher(String email, String password) {
            this.email = email;
            this.password = password;
        }
        public String getEmail() {return email;}
        public String getPassword() {return password;}
    }
    /*
    CLASS TO UPLOAD BOTH IMAGES AND TEXT
     */
    public class MyUploader {
        private static final String DATA_UPLOAD_URL="http://dunamixapp.000webhostapp.com/login.php";

        //INSTANCE FIELDS
        private final Context c;
        public MyUploader(Context c) {this.c = c;}
        /*
        SAVE/INSERT
         */
        public void upload(ChatLogin.SpiritualTeacher s, final View...inputViews)
        {
            if(s == null){Toast.makeText(c, "No Data To Save", Toast.LENGTH_SHORT).show();}
            else {

                lgnProgressBar.setVisibility(View.VISIBLE);

                AndroidNetworking.upload(DATA_UPLOAD_URL)
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
                                            //Login
                                            Intent intent = new Intent(getContext(), Conversation.class);
                                            startActivity(intent);

                                            //RESET VIEWS
                                            Toast.makeText(c, "PHP SERVER RESPONSE : " + responseString, Toast.LENGTH_LONG).show();
                                            EditText email = (EditText) inputViews[0];
                                            EditText password = (EditText) inputViews[1];

                                            email.setText("");
                                            password.setText("");

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
                                lgnProgressBar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError(ANError error) {
                                error.printStackTrace();
                                lgnProgressBar.setVisibility(View.GONE);
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
        String email=lgnEmail.getText().toString();
        String password=lgnPassword.getText().toString();
        return !(email.equals("") | password.equals(""));
    }
}