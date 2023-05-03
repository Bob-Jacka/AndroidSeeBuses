package com.example.seebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String bus36url = "https://igis-transport.ru/izh/citybus/36?";
    private final String bus12url = "https://igis-transport.ru/izh/citybus/12?";
    private final String bus27url = "https://igis-transport.ru/izh/citybus/27?";
    private final String troll14url = "https://igis-transport.ru/izh/trolleybus/14?";
    private final String troll4url = "https://igis-transport.ru/izh/trolleybus/4?";
    private String transportURI = "https://igis-transport.ru/";

    private Switch isWeekend;
    private ImageButton bus36;
    private ImageButton troll14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isWeekend = findViewById(R.id.checkBox);
        bus36 = findViewById(R.id.bus36);
        troll14 = findViewById(R.id.troll14);
    }

    public void applyChanges(View view) {
        if(isWeekend.isChecked()) {
            bus36.setVisibility(View.GONE);
            troll14.setVisibility(View.GONE);
            Toast.makeText(this, "Apply changes", Toast.LENGTH_LONG).show();

        } else if ((bus36.getVisibility() == View.GONE && troll14.getVisibility() == View.GONE) && !isWeekend.isChecked()) {
            bus36.setVisibility(View.VISIBLE);
            troll14.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(this, "Is not working", Toast.LENGTH_LONG).show();
        }
    }

    public void goSettings(View view) {
        Intent changePage = new Intent(this, Settings.class);
        startActivity(changePage);
    }
//Ultimate URL
    public void seeTransport(View view, String transportURI) {
        Intent openLink = new Intent(Intent.ACTION_VIEW, Uri.parse(transportURI));
        startActivity(openLink);
    }
//////
    public void seeTransport36(View view) {
        Uri address = Uri.parse(bus36url);
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }
    public void seeTransport12(View view) {
        Uri address = Uri.parse(bus12url);
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }
    public void seeTransport27(View view) {
        Uri address = Uri.parse(bus27url);
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }
    public void seeTransport14(View view) {
        Uri address = Uri.parse(troll14url);
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }
    public void seeTransport4(View view) {
        Uri address = Uri.parse(troll4url);
        Intent openLink = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLink);
    }
}