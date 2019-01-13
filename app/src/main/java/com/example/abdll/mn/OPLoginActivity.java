package com.example.abdll.mn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OPLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_login);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(OPLoginActivity.this, OPActivity.class);
        startActivity(intent);
    }
}
