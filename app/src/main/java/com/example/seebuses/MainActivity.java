package com.example.seebuses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String transportURI = "https://igis-transport.ru/";
    static boolean globalVisibleState = false;
    private ImageButton busIB36;
    private ImageButton trollIB14;
    private TextView bus36Text;
    private TextView troll14Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busIB36 = findViewById(R.id.busIB36);
        trollIB14 = findViewById(R.id.trollIB14);
        bus36Text = findViewById(R.id.bus36);
        troll14Text = findViewById(R.id.troll14);

        if(globalVisibleState) {
            busIB36.setVisibility(View.GONE);
            bus36Text.setVisibility(View.GONE);

            trollIB14.setVisibility(View.GONE);
            troll14Text.setVisibility(View.GONE);
        } else {
            busIB36.setVisibility(View.VISIBLE);
            bus36Text.setVisibility(View.VISIBLE);

            trollIB14.setVisibility(View.VISIBLE);
            troll14Text.setVisibility(View.VISIBLE);
        }
    }

    public void goSettings(View view) {
        Intent changePage = new Intent(this, Settings.class);
        startActivity(changePage);
    }
//Ultimate URL
//    public void seeTransport(View view, String transportURI) {
//        Intent openLink = new Intent(Intent.ACTION_VIEW, Uri.parse(transportURI));
//        startActivity(openLink);
//    }
//////
    public void seeTransport36(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/citybus/36?";
        goWebBrowser();
    }
    public void seeTransport12(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/citybus/12?";
        goWebBrowser();
    }
    public void seeTransport27(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/citybus/27?";
        goWebBrowser();
    }
    public void seeTransport14(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/trolleybus/14?";
        goWebBrowser();
    }
    public void seeTransport4(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/trolleybus/4?";
        goWebBrowser();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void goWebBrowser() {
        Intent goWebBrowser = new Intent(this, WebBrowser.class);
        startActivity(goWebBrowser);
    }

}