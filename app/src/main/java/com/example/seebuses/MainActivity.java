package com.example.seebuses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private final int BLOCKS_COUNT = 5;
    static boolean globalVisibleState = false;
    private ImageButton busIB36;
    private ImageButton trollIB14;
    private TextView bus36Text;
    private TextView troll14Text;
    private GestureDetectorCompat gd;
    private LinearLayout transportBlocks;
//    private static TransportBlock[] transports;
    static View transportBlockView;
    private Drawable dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busIB36 = findViewById(R.id.busIB36);
        trollIB14 = findViewById(R.id.trollIB14);
        bus36Text = findViewById(R.id.bus36name);
        troll14Text = findViewById(R.id.troll14name);

        transportBlocks = findViewById(R.id.TransportBlocks);

        gd = new GestureDetectorCompat(this, this);
        gd.setIsLongpressEnabled(true);
        gd.setOnDoubleTapListener(new GestureDetector.SimpleOnGestureListener());
        gd.setOnDoubleTapListener(this);

        dr = AppCompatResources.getDrawable(this, R.drawable.empty_block);
        initBlocks();

        for(int i = 0; i < BLOCKS_COUNT; i++) {
            registerForContextMenu(transportBlocks.getChildAt(i));
        }

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

    public final void seeTransport36(View view) {
        WebBrowser.TransportURL = TransportBlock.transportURI + "izh/citybus/36?";
        goWebBrowser();
    }

    public final void seeTransport12(View view) {
        WebBrowser.TransportURL = TransportBlock.transportURI + "izh/citybus/12?";
        goWebBrowser();
    }

    public final void seeTransport27(View view) {
        WebBrowser.TransportURL = TransportBlock.transportURI + "izh/citybus/27?";
        goWebBrowser();
    }

    public final void seeTransport14(View view) {
        WebBrowser.TransportURL = TransportBlock.transportURI + "izh/trolleybus/14?";
        goWebBrowser();
    }

    public final void seeTransport4(View view) {
        WebBrowser.TransportURL = TransportBlock.transportURI + "izh/trolleybus/4?";
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
        Toast.makeText(this, "клик на блок", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        transportBlockView = v;
        //TODO сделать проверку на то, пустой блок изначально или нет
        if ((TextView)(((LinearLayout) v).getChildAt(1)).equals("Нет данных")) {
            menu.setHeaderTitle("Добавление транспорта");
            menu.add(1, v.getId(), 1, "Добавить");
            menu.add(2, v.getId(), 2, "Закрыть");

        } else {
            menu.setHeaderTitle("Изменение транспорта");
            menu.add(1, v.getId(), 1, "Удалить");
            menu.add(2, v.getId(), 3, "Изменить транспорт");
            menu.add(3, v.getId(), 3, "Закрыть");
        }


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Удалить":
                transportBlockView.setVisibility(View.GONE);
                break;
            case "Изменить транспорт":
                Intent changeTransp = new Intent(new Intent(this, change_Transport.class));
                startActivity(changeTransp);
                break;
            case "Закрыть":
                break;
//            case "Добавить":
//                break;
        }
        return super.onContextItemSelected(item);
    }
    private void initBlocks() {
        LinearLayout outer_block;
        View innerBlock;
        for(int increment = 0; increment < BLOCKS_COUNT; increment++) {
            outer_block = (LinearLayout) transportBlocks.getChildAt(increment);
            for (int innerIncrement = 0; innerIncrement < 2; innerIncrement++) {
                innerBlock = outer_block.getChildAt(innerIncrement);
                if(innerIncrement == 0 ) {
                    ((ImageButton) innerBlock).setImageDrawable(dr);
                } if(innerIncrement == 1) {
                    ((TextView) innerBlock).setText("Нет данных");
                }
            }
        }
    }
}