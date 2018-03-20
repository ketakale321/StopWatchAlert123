package com.example.ketan.stopwatchalert;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //@Override
    EditText edit_timer;
    Button butt_start,butt_stop;
    TextView text_timer;
    int duration;
    CountDownTimer cnt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wiring
        edit_timer = findViewById(R.id.edit_time);
        butt_start = findViewById(R.id.butt_start);
        butt_stop = findViewById(R.id.butt_stop);
        text_timer = findViewById(R.id.text_timer_display);

        butt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duration = 0;
                try {
                    duration = Integer.parseInt(edit_timer.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Enter appropriate value", Toast.LENGTH_SHORT).show();
                }
                if (duration > 0) {
                    edit_timer.setText(String.valueOf(duration));
                    butt_start.setEnabled(false);
                    butt_stop.setEnabled(true);

                    cnt = new CountDownTimer(duration * 1000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            text_timer.setText(String.valueOf(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            //send sms
                            sendSMS("9850398070","Ketan Padla. Uchla re!!!");

                            text_timer.setText("Time Up!!");
                            butt_stop.setEnabled(false);
                            butt_start.setEnabled(true);

                        }
                    }.start();
                }
            }
        });

        butt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt.cancel();
                butt_stop.setEnabled(false);
                butt_start.setEnabled(true);
            }
        });
    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
