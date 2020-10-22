package afm.drc.online_payments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import afm.drc.dunamix.R;

public class OnlinePaymentsActivity extends AppCompatActivity {
    Button tithe, youth, sister, others;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinepayment_main);

        tithe = findViewById(R.id.tithe);
        sister = findViewById(R.id.sister);
        others = findViewById(R.id.others);
        youth = findViewById(R.id.youth);

        tithe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnlinePaymentsActivity.this, Tithe.class);
                startActivity(intent);
            }
        });

        youth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnlinePaymentsActivity.this, Youth.class);
                startActivity(intent);
            }
        });

        sister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnlinePaymentsActivity.this, Sister.class);
                startActivity(intent);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnlinePaymentsActivity.this, Others.class);
                startActivity(intent);
            }
        });
    }
}