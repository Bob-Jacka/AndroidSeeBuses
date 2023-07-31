package com.example.seebuses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GestureDetectorCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final int BLOCKS_COUNT = 5;
    private static final int ELEMENTS_IN_BLOCK = 2;
    static final int SUPPORTED_CITIES = 2;
    private GestureDetectorCompat gd;
    static LinearLayout transportBlocks;
    static TransportBlock[] transports = new TransportBlock[BLOCKS_COUNT];
    static View transportBlockView;
    private Drawable emptBlkImage;
    private static File saveFile;
    private final HashMap<String, String> ct = CityTable.initTable();

    @SuppressLint("UsableSpace")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        transportBlocks = findViewById(R.id.TransportBlocks);

        String saveFileName = context.getFilesDir().getAbsolutePath() + "/saveBlocks";
        saveFile = new File(saveFileName);

        gd = new GestureDetectorCompat(this, this);
        gd.setIsLongpressEnabled(true);
        gd.setOnDoubleTapListener(new GestureDetector.SimpleOnGestureListener());
        gd.setOnDoubleTapListener(this);

        for (int i = 0; i < BLOCKS_COUNT; i++) {
            registerForContextMenu(transportBlocks.getChildAt(i));
        }
        emptBlkImage = AppCompatResources.getDrawable(this, R.drawable.emptblk);

        loadTransportArray();
    }

    @Override
    protected void onStart() {
        loadTransportArray();
        super.onStart();
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
    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent motionEvent) {
        System.exit(1);
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(@NonNull MotionEvent motionEvent) {
        return false;
    }

    public final void seeTransportUltimate(View view) {
        int pointer = transportBlocks.indexOfChild((LinearLayout) (view.getParent())); //указатель на выбранный транспорт
        TransportBlock tb = transports[pointer];
        if (tb != null && ct.containsKey(tb.getCity())) {
            WebBrowser.TransportURL = tb.getTransportURI_IGIS();
            goWebBrowser();
        }
    }

    protected final void onRestoreInstanceState(Bundle bundle) {
        loadTransportArray();
        super.onRestoreInstanceState(bundle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        saveTransportData();
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void goWebBrowser() {
        if (isInternetAvailable()) {
            Intent goWebBrowser = new Intent(this, WebBrowser.class);
            startActivity(goWebBrowser);
        } else {
            Toast.makeText(this, "Проверьте ваше соединение с интернетом", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInternetAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        transportBlockView = v;
        if ((((TextView) (((LinearLayout) (((LinearLayout) v).getChildAt(1)))).getChildAt(1)).getText()) == "Нет данных") {
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
                deleteTransport();
                break;
            case "Изменить транспорт":
                Intent changeTransp = new Intent(new Intent(this, change_Transport.class));
                startActivity(changeTransp);
                break;
            case "Закрыть":
                break;
            case "Добавить":
                Intent addTransport = new Intent(new Intent(this, change_Transport.class));
                startActivity(addTransport);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteTransport() {
        int pointer = transportBlocks.indexOfChild(transportBlockView);
        transports[pointer] = null;
        saveTransportData();
        loadTransportArray();
    }

    private void initFilledBlock(TransportBlock tb, int increment) {
        LinearLayout inner_block;
        View innerView;
        inner_block = (LinearLayout) transportBlocks.getChildAt(increment);
        for (int innerIncrement = 0; innerIncrement < ELEMENTS_IN_BLOCK; innerIncrement++) {
            innerView = inner_block.getChildAt(innerIncrement);
            if (innerIncrement == 0) {
                switch (tb.getTranspType()) {
                    case "citybus":
                        ((ImageButton) innerView).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.bus));
                        break;
                    case "trolleybus":
                        ((ImageButton) innerView).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.troll));
                        break;
                    case "tram":
                        ((ImageButton) innerView).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.tram));
                        break;
                }
            }
            if (innerIncrement == 1) {
                ((TextView) ((LinearLayout) innerView).getChildAt(0)).setText(tb.getTextViewText());
                ((TextView) ((LinearLayout) innerView).getChildAt(1)).setText(tb.getCity());
            }
        }
    }

    private void initEmptyBlocks(int increment) {
        LinearLayout innerBlock;

        for (int innerIncrement = 0; innerIncrement < ELEMENTS_IN_BLOCK; innerIncrement++) {
            innerBlock = (LinearLayout) transportBlocks.getChildAt(increment);
            if (innerIncrement == 0) {
                ((ImageButton) innerBlock.getChildAt(innerIncrement)).setImageDrawable(emptBlkImage);
            }
            if (innerIncrement == 1) {
                LinearLayout textblock = ((LinearLayout) innerBlock.getChildAt(innerIncrement));
                ((TextView) (textblock.getChildAt(0))).setText("Нет данных");
                ((TextView) (textblock.getChildAt(1))).setText("Нет данных");
            }
        }
    }

    private void initializeAfterLoadBlocks() {
        int increment = 0;
        for (TransportBlock tb : transports) {
            if (tb != null) {
                initFilledBlock(tb, increment);
            } else {
                initEmptyBlocks(increment);
            }
            increment++;
        }
    }

    private void initializeEmptyBlocks() {
        int increment = 0;
        while (increment < BLOCKS_COUNT) {
            transports[increment] = null;
            increment++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    static void saveTransportData() {
        try {
            if (saveFile.length() != 0) {
                saveFile.delete();
                saveTransportData();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(MainActivity.saveFile));
            for (TransportBlock tb : transports) {
                if (tb == null) {
                    writer.write(String.valueOf(0));
                    writer.newLine();

                } else {
                    writer.write(String.valueOf(tb.getTranspNumb()));
                    writer.write(' ');

                    writer.write(tb.getTranspType());
                    writer.write(' ');

                    writer.write(tb.getCity());
                    writer.write(' ');

                    writer.write(tb.getTextViewText());
                    writer.write(' ');

                    writer.write(tb.getFakeCity());
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //
        }
    }

    private void loadTransportArray() {
        initializeEmptyBlocks();
        try {
            if (saveFile.length() != 0L) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                int increment = 0;
                String transportBlock;

                do {
                    transportBlock = reader.readLine();
                    if (!Objects.equals(transportBlock, "0")) {
                        String[] splitted = transportBlock.split(" ");
                        transports[increment] = new TransportBlock(Integer.parseInt(splitted[0]), splitted[1],
                                splitted[2], splitted[3] + " " + splitted[4], splitted[5]);
                    }
                    increment++;
                }
                while (increment < BLOCKS_COUNT);
                reader.close();
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Ошибка загрузки из файла", Toast.LENGTH_SHORT).show();
        }
        initializeAfterLoadBlocks();
    }
}