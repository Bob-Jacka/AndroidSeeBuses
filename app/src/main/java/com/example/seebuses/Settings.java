package com.example.seebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;



public class Settings extends AppCompatActivity {
    private Button bus36;
    private Button troll14;
    private Switch isWeekend;
    private String city;
    private String transport;
    private String transportNumber;
    private String transportURL = "https://igis-transport.ru/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        troll14 = findViewById(R.id.troll14);
        isWeekend = findViewById(R.id.switchIsWeekend);
    }

    public void goMain(View view) {
        Intent changePage = new Intent(this, MainActivity.class);
        startActivity(changePage);
    }

    public void reset(View view) {
       //group1
        RadioButton rd12 = findViewById(R.id.number12);
        RadioButton rd36 = findViewById(R.id.number36);
        RadioButton rd27 = findViewById(R.id.number27);
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

    public void changeCity(String newCity) {
        this.city = newCity;
    }

    public void changeTransport(String newTransport) {
        this.transport = newTransport;
    }

    public void changeTransportNumber(String newTransportNumber) {
        this.transportNumber = newTransportNumber;
    }

    public void applyChanges(View view) {
        if(isWeekend.isChecked()) {
            bus36.setVisibility(View.INVISIBLE);
            troll14.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Apply changes", Toast.LENGTH_SHORT).show();

        } else if ((bus36.getVisibility() == View.INVISIBLE && troll14.getVisibility() == View.INVISIBLE) && !isWeekend.isChecked()) {
            bus36.setVisibility(View.VISIBLE);
            troll14.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(this, "Is not working", Toast.LENGTH_SHORT).show();
        }
    }
}