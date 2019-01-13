package com.example.abdll.mn;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Define GPS Tracker.
    private GpsTracker gpsTracker;
    private RequestQueue mRequestQueue;
    private final String TAG = "MainActivity";


    // Variables
    double lon = 0, lat = 0;
    int countFilledEditTexts = 0;
    Button confirmButton;
    EditText IDEditText, phoneNumberEditText, descEditText;

    // Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get location permission.
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // Define UI properties.
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setEnabled(false);
        IDEditText= (EditText) findViewById(R.id.IDEditText) ;
        phoneNumberEditText= (EditText)findViewById(R.id.phoneNumberEditText) ;
        descEditText= (EditText)findViewById(R.id.DescEditText);

        setUpEditTexts();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Log.d("Test", String.format("%s %s %s %f %f", IDEditText.getText(),
                        phoneNumberEditText.getText(),
                        descEditText.getText(),
                        lon, lat));
                // Add the request to the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
                Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });





    }


    // Instantiate the RequestQueue.
    String url ="http://10.0.2.2:8000/data/post.php";

    // Request a string response fro=m the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    Log.d(TAG, "Response is: "+ response);
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "That didn't work!");
        }


    }
    ) {
        @Override
        protected Map<String, String> getParams()
        {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("ID", String.valueOf(IDEditText.getText()));
            params.put("phoneNumber", String.valueOf(phoneNumberEditText.getText()));
            params.put("desc", String.valueOf(descEditText.getText()));
            params.put("lon", lon+"");
            params.put("lat", lat+"");

            //params.put("domain", "http://itsalif.info");

            return params;
        }
    };

    /**
     * To get GPS location.
     */
    private void getLocation(){
        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            lon = longitude;
            lat = latitude;
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    /**
     * To set up the from edit texts.
     */
    private void setUpEditTexts() {
        IDEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    confirmButton.setEnabled(false);
                }

                checkifEditTextsEmpty();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    confirmButton.setEnabled(false);
                }

                checkifEditTextsEmpty();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        descEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    confirmButton.setEnabled(false);
                }

                checkifEditTextsEmpty();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    /**
     * To check if edit texts are empty.
     */
    private void checkifEditTextsEmpty() {
        // Check that all edit texts are empty.
        if (IDEditText.getText().length() != 0 &&
                phoneNumberEditText.getText().length() != 0 &&
                descEditText.getText().length() != 0) {
            confirmButton.setEnabled(true);
        }
    }

}