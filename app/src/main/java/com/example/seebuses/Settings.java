package com.example.seebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;



public class Settings extends AppCompatActivity {
    private Switch isWeekend;
////////////////////////////////
    private View bus36View;
    private View troll14View;
    private ImageButton busIB36;
    private ImageButton trollIB14;
    private TextView bus36Text;
    private TextView troll14Text;
    private boolean isSwitchChecked = false;

////////////
    private final String transportURL = "https://igis-transport.ru/";


    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bus36View = getLayoutInflater().inflate(R.layout.activity_main, null);
        troll14View = getLayoutInflater().inflate(R.layout.activity_main, null);

        busIB36 = bus36View.findViewById(R.id.busIB36);
        bus36Text = bus36View.findViewById(R.id.bus36name);

        troll14Text = troll14View.findViewById(R.id.troll14name);
        trollIB14 = troll14View.findViewById(R.id.trollIB14);

        isWeekend = findViewById(R.id.switchIsWeekend);

        if(isSwitchChecked) {
            isWeekend.setChecked(true);
        } else isWeekend.setChecked(false);

    }

    public void goMain(View view) {
        Intent changePage = new Intent(this, MainActivity.class);
        startActivity(changePage);
    }

    public void reset(View view) {
       //group1
        RadioButton rd12 = findViewById(R.id.Buses);
        RadioButton rd36 = findViewById(R.id.trams);
        RadioButton rd27 = findViewById(R.id.trolls);
        if(rd12.isChecked()) {
            rd12.setChecked(false);
        }
        if( rd36.isChecked() ) {
            rd36.setChecked(false);
        }
        if(rd27.isChecked()) {
            rd27.setChecked(false);
        }
        //group2
        RadioButton rdtram = findViewById(R.id.tram_button);
        RadioButton rdtroll = findViewById(R.id.troll_number);
        RadioButton rdbus = findViewById(R.id.bus_button);
        if(rdtram.isChecked()) {
            rdtram.setChecked(false);
        }
        if( rdtroll.isChecked() ) {
            rdtroll.setChecked(false);
        }
        if(rdbus.isChecked()) {
            rdbus.setChecked(false);
        }
    }
    public boolean applyChanges(View view) {
        if(isWeekend.isChecked()) {

            Toast.makeText(this, "Apply changes", Toast.LENGTH_LONG).show();
            MainActivity.globalVisibleState = true;
            isSwitchChecked = true;

        } else if ((busIB36.getVisibility() == View.GONE && trollIB14.getVisibility() == View.GONE) && !isWeekend.isChecked()) {
            isSwitchChecked = false;
            MainActivity.globalVisibleState = false;

        } else {
            Toast.makeText(this, "Changed", Toast.LENGTH_LONG).show();
        }
        return MainActivity.globalVisibleState;
    }

}