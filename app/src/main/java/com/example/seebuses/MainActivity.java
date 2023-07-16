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
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final int BLOCKS_COUNT = 5;
    private GestureDetectorCompat gd;
    static LinearLayout transportBlocks;
    static TransportBlock[] transports = new TransportBlock[BLOCKS_COUNT];
    static View transportBlockView;
    private Drawable emptBlkImage;
    static File saveFile;
    private String saveFileName;
    private Context context;

    @SuppressLint("UsableSpace")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        transportBlocks = findViewById(R.id.TransportBlocks);

        saveFileName = context.getFilesDir().getAbsolutePath() + "/saveBlocks";
        saveFile = new File(saveFileName);

        gd = new GestureDetectorCompat(this, this);
        gd.setIsLongpressEnabled(true);
        gd.setOnDoubleTapListener(new GestureDetector.SimpleOnGestureListener());
        gd.setOnDoubleTapListener(this);

        for (int i = 0; i < BLOCKS_COUNT; i++) {
            registerForContextMenu(transportBlocks.getChildAt(i));
        }
        emptBlkImage = AppCompatResources.getDrawable(this, R.drawable.empty_block);

        loadTransportArray();
        initializeAfterLoadBlocks();

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

    public final void seeTransportUltimate(View view) {
        int pointer = transportBlocks.indexOfChild((LinearLayout) (view.getParent())); //указатель на выбранный транспорт
        TransportBlock tb = transports[pointer];
        if (tb.getCity().equals("Ижевск")) {
            WebBrowser.TransportURL = tb.getTransportURI_IGIS();
            goWebBrowser();
        } else if (tb.getCity().equals("Пермь")) {
            WebBrowser.TransportURL = tb.getTransportURI_BUSTI();
            goWebBrowser();
        }
    }

    protected final void onRestoreInstanceState(Bundle bundle) {
        loadTransportArray();
        super.onRestoreInstanceState(bundle);
        Toast.makeText(this, "Загрузка сохранения", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        saveTransportData();
        super.onSaveInstanceState(outState, outPersistentState);
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
                deleteTranspData();
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

    public void deleteTranspData() {
        int pointer = transportBlocks.indexOfChild(transportBlockView); //указатель на выбранный транспорт
        transports[pointer] = new TransportBlock();
        saveTransportData();
        loadTransportArray();
    }

    private void initFilledBlock(TransportBlock tb, int increment) {
        LinearLayout inner_block;
        View innerView;
        inner_block = (LinearLayout) transportBlocks.getChildAt(increment);

        for (int innerIncrement = 0; innerIncrement < 2; innerIncrement++) {
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

        for (int innerIncrement = 0; innerIncrement < 2; innerIncrement++) {
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

            if (tb.getTranspType() != null) {
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
            transports[increment] = new TransportBlock();
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
                if (tb.getTranspNumb() == 0) {
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

    public void loadTransportArray() {
        initializeEmptyBlocks();
        try {
            if (saveFile.length() != 0L) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                int excriment = 0;
                String transportBlock;

                do {
                    transportBlock = reader.readLine();
                    if (!Objects.equals(transportBlock, "0")) {
                        String[] splitted = transportBlock.split(" ");
                        transports[excriment] = new TransportBlock(Integer.parseInt(splitted[0]), splitted[1],
                                splitted[2], splitted[3] + " " + splitted[4], splitted[5]);

                    }
                    excriment++;
                }
                while (excriment < BLOCKS_COUNT);
                reader.close();
            } else
                Toast.makeText(MainActivity.this, "Файл сохранения пустой", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Ошибка загрузки из файла", Toast.LENGTH_SHORT).show();
        }
    }
}
