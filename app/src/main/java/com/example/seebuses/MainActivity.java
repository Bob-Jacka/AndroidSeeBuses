package com.example.seebuses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private final String transportURI = "https://igis-transport.ru/";
    static boolean globalVisibleState = false;
    private ImageButton busIB36;
    private ImageButton trollIB14;
    private TextView bus36Text;
    private TextView troll14Text;
    private GestureDetectorCompat gd;
//    private static Transport[] transports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busIB36 = findViewById(R.id.busIB36);
        trollIB14 = findViewById(R.id.trollIB14);
        bus36Text = findViewById(R.id.bus36name);
        troll14Text = findViewById(R.id.troll14name);

        LinearLayout transportBlocks = new LinearLayout(this);
        transportBlocks.findViewById(R.id.TransportBlocks);

        gd = new GestureDetectorCompat(this, this);
        gd.setIsLongpressEnabled(true);
        gd.setOnDoubleTapListener(new GestureDetector.SimpleOnGestureListener());
        gd.setOnDoubleTapListener(this);

//        registerForContextMenu(transportBlocks);

        if (globalVisibleState) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {
        Toast.makeText(MainActivity.this, "Don't long touch me", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent motionEvent) {
        Toast.makeText(MainActivity.this, "Don't touch me", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent motionEvent) {
        System.exit(1);
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(@NonNull MotionEvent motionEvent) {
        System.exit(1);
        return false;
    }


    private static class TransportBlock {
        private int transpNumb;
        private String transpType;
        private boolean isGoneOnWeekends;
        private String city;
        private final String transportName = transpType + " " + transpNumb;

        public TransportBlock(int transpNumb, String transpType, boolean isGoneOnWeekends, String city) {
            this.transpNumb = transpNumb;
            this.transpType = transpType;
            this.city = city;
            this.isGoneOnWeekends = isGoneOnWeekends;
        }

        private void changeCity(String newCity) {
            this.city = newCity;
        }

        private void changeTransport(String newTransport) {
            this.transpType = newTransport;
        }

        private void changeTransportNumber(int newTransportNumber) {
            this.transpNumb = newTransportNumber;
        }
    }

    public final void goSettings(View view) {
        Intent changePage = new Intent(this, Settings.class);
        startActivity(changePage);
    }

    public final void seeTransport36(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/citybus/36?";
        goWebBrowser();
    }

    public final void seeTransport12(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/citybus/12?";
        goWebBrowser();
    }

    public final void seeTransport27(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/citybus/27?";
        goWebBrowser();
    }

    public final void seeTransport14(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/trolleybus/14?";
        goWebBrowser();
    }

    public final void seeTransport4(View view) {
        WebBrowser.TransportURL = "https://igis-transport.ru/izh/trolleybus/4?";
        goWebBrowser();
    }

    protected final void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "Загрузка сохранения", Toast.LENGTH_SHORT).show();
    }

    private void goWebBrowser() {
        Intent goWebBrowser = new Intent(this, WebBrowser.class);
        startActivity(goWebBrowser);
    }

    public final void onBlockClick(View view) {
        registerForContextMenu(view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Изменение транспорта");
        menu.add(1, v.getId(), 1, "Удалить");
        SubMenu sbm = menu.addSubMenu(2, v.getId(), 2, "Изменить");
        menu.add(3, v.getId(), 3, "Закрыть");

        sbm.setHeaderTitle("Изменения");
        sbm.add(4, v.getId(), 4, "Тип транспорта");
        sbm.add(5, v.getId(), 5, "Номер транспорта");
        sbm.add(6, v.getId(), 6, "Город транспорта");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == 1) item.setVisible(false);
        return super.onContextItemSelected(item);
    }
}