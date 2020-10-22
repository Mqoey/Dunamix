package afm.drc.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import afm.drc.dunamix.R;

public class Conversation extends AppCompatActivity {

    Button sendChat;
    EditText chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        sendChat = findViewById(R.id.chat_send);
        chat = findViewById(R.id.chat);

        sendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    //GET VALUES
                    String messages = chat.getText().toString();

                    SpiritualTeacher s = new SpiritualTeacher(messages);

                    //upload data to mysql
                    new MyUploader(Conversation.this).upload(s, chat);

                } else {
                    Toast.makeText(Conversation.this, "PLEASE ENTER ALL FIELDS CORRECTLY ", Toast.LENGTH_LONG).show();
                }
            }
        });


        RecyclerView recyclerView = findViewById(R.id.chat_recycler);
        List<ChatModel> albumList = new ArrayList<>();
        ChatAdapter adapter = new ChatAdapter(albumList);
        new DataRetriever(Conversation.this).retrieve(recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /*
    Our data object. THE POJO CLASS
     */
    static class SpiritualTeacher{
        private String message;
        public SpiritualTeacher(String message) {
            this.message = message;
        }
        public String getMessage() {return message;}
    }
    /*
    CLASS TO UPLOAD BOTH IMAGES AND TEXT
     */
    public static class MyUploader {
        private static final String DATA_UPLOAD_URL="http://dunamixapp.000webhostapp.com/send.php";

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

                AndroidNetworking.upload(DATA_UPLOAD_URL)
                        .addMultipartParameter("messages",s.getMessage())
                        .addMultipartParameter("name", "Mqoe")
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
                                            EditText message = (EditText) inputViews[0];

                                            message.setText("");

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
                            }
                            @Override
                            public void onError(ANError error) {
                                error.printStackTrace();
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
        String message=chat.getText().toString();
        return !message.equals("");
    }

    public static class DataRetriever {

        private static final String PHP_MYSQL_SITE_URL="http://dunamixapp.000webhostapp.com/chats.php";
        //INSTANCE FIELDS
        private final Context c;
        private ChatAdapter adapter ;

        public DataRetriever(Context c) { this.c = c; }
        /*
        RETRIEVE/SELECT/REFRESH
         */
        public void retrieve(final RecyclerView recyclerView)
        {
            final ArrayList<ChatModel> pictures = new ArrayList<>();

            AndroidNetworking.get(PHP_MYSQL_SITE_URL)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jo;
                            ChatModel picture;
                            try
                            {
                                for(int i=0;i<response.length();i++)
                                {
                                    jo=response.getJSONObject(i);
                                    int id=jo.getInt("id");
                                    String name=jo.getString("name");
                                    String messages=jo.getString("messages");
                                    String time=jo.getString("time");

                                    picture= new ChatModel(name, messages, time, id);
                                    pictures.add(picture);
                                }
                                adapter = new ChatAdapter(pictures);
                                recyclerView.setAdapter(adapter);

                            }catch (JSONException e)
                            {
                                Toast.makeText(c, "GOOD RESPONSE BUT JAVA CAN'T PARSE JSON IT RECEIVED. "+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : "+anError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}