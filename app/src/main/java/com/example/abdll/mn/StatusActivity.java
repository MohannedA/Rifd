package com.example.abdll.mn;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class StatusActivity extends AppCompatActivity {

    TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        final int orderTimeInMin = 3;
        final int timeInMils = orderTimeInMin*1000*60;
        new CountDownTimer(timeInMils, 1000) {

            public void onTick(long millisUntilFinished) {
                long min = millisUntilFinished/60000;
                long sec = millisUntilFinished/1000/orderTimeInMin;
                timerTextView.setText(min  + ":" + sec );
            }

            public void onFinish() {
                timerTextView.setText("done!");
            }
        }.start();
    }


}
