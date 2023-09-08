package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.Consts.CURRENT_LANGUAGE;
import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;
import static com.example.seebuses.Consts.DEFAULT_BLOCKS_COUNT;
import static com.example.seebuses.Consts.ELEMENTS_IN_BLOCK;
import static com.example.seebuses.Consts.LAST_BLOCKS_COUNT;
import static com.example.seebuses.Consts.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static LinearLayout transportBlocks;
    static BlockElement[] transports = new BlockElement[DEFAULT_BLOCKS_COUNT];
    static View BlockView_pointer;
    private Drawable emptBlkImage;
    static File saveFile;
    private ConstraintLayout instructions;
    private TextView instructuonsText;
    private TextView title;

    @SuppressLint("UsableSpace")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        String saveFileName = context.getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;
        saveFile = new File(saveFileName);

        loadTransportArray();
        startInit();
        startSetup();
    }

    public final void seeTransportUltimate(View view) {
        int pointer = transportBlocks.indexOfChild((LinearLayout) (view.getParent()));
        BlockElement tb = transports[pointer];
        if (tb != null) {
            if (!tb.getTranspType().equals("metro")) {
                switch (tb.getCity()) {
                    case "Izhevsk":
                    case "Ижевск":
                        WebBrowser.URL = tb.getTransportURI_IGIS();
                        break;
                    case "Perm":
                    case "Пермь":
                        WebBrowser.URL = tb.getTransportURI_BUSTI();
                        break;
                }
            } else WebBrowser.URL = tb.getSchemaURI_YandexMetro();
            goWebBrowser();
        }
    }

    protected final void onRestoreInstanceState(@NonNull Bundle bundle) {
        loadTransportArray();
        super.onRestoreInstanceState(bundle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        saveTransportBlocksData();
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

    public void goSettings(View view) {
        Intent goSettings = new Intent(this, Settings.class);
        startActivity(goSettings);
    }

    private boolean isInternetAvailable() {
        try {
            return Runtime.getRuntime().exec("ping -c 1 google.com").waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        BlockView_pointer = v;
        if (CURRENT_LANGUAGE.equals("Russian")) {
            addMenu_ruLocale(menu, v);
        } else addMenu_enLocale(menu, v);
    }

    private void addMenu_ruLocale(ContextMenu menu, View v) {
        if ((((TextView) (((LinearLayout) (((LinearLayout) v).getChildAt(1)))).getChildAt(1)).getText()) == "Нет данных") {
            menu.setHeaderTitle("Добавить - ");
            menu.add("Транспорт");
            menu.add("Cхему метро");
            menu.add("Закрыть");

        } else {
            menu.setHeaderTitle("Транспорт - ");
            menu.add("Удалить");
            menu.add("Изменить");
            menu.add("Закрыть");
        }
    }

    private void addMenu_enLocale(ContextMenu menu, View v) {
        if ((((TextView) (((LinearLayout) (((LinearLayout) v).getChildAt(1)))).getChildAt(1)).getText()) == "No data") {
            menu.setHeaderTitle("Add - ");
            menu.add("Transport");
            menu.add("Schema map");
            menu.add("Close");

        } else {
            menu.setHeaderTitle("Transport - ");
            menu.add("Delete");
            menu.add("Change");
            menu.add("Close");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (CURRENT_LANGUAGE.equals("Russian")) {
            chooseItem_ruLocale(item);
        } else {
            chooseItem_enLocale(item);
        }
        return super.onContextItemSelected(item);
    }

    private void chooseItem_ruLocale(MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Удалить":
                deleteTransport();
                break;
            case "Изменить":
                Intent changeTransp = new Intent(new Intent(this, Change_Transport.class));
                startActivity(changeTransp);
                break;
            case "Транспорт":
                Intent addTransport = new Intent(new Intent(this, Change_Transport.class));
                startActivity(addTransport);
                break;
            case "Cхему метро":
                Intent goSchema = new Intent(this, Schema_Metro_Add.class);
                startActivity(goSchema);
                break;
        }
    }

    private void chooseItem_enLocale(MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Delete":
                deleteTransport();
                break;
            case "Change":
                Intent changeTransp = new Intent(new Intent(this, Change_Transport.class));
                startActivity(changeTransp);
                break;
            case "Transport":
                Intent addTransport = new Intent(new Intent(this, Change_Transport.class));
                startActivity(addTransport);
                break;
            case "Schema map":
                Intent goSchema = new Intent(this, Schema_Metro_Add.class);
                startActivity(goSchema);
                break;
        }
    }

    private void deleteTransport() {
        int pointer = transportBlocks.indexOfChild(BlockView_pointer);
        transports[pointer] = null;
        saveTransportBlocksData();
        recreate();
    }

    private void initFilledBlock(BlockElement tb, int increment) {
        LinearLayout inner_block;
        View innerView;
        TextView type;
        TextView city;
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
                    case "metro":
                        ((ImageButton) innerView).setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.metro));
                        break;
                }
            }
            if (innerIncrement == 1) {
                type = ((TextView) ((LinearLayout) innerView).getChildAt(0));
                city = ((TextView) ((LinearLayout) innerView).getChildAt(1));

                type.setText(tb.getTextViewText());
                type.setTextSize(CURRENT_TEXT_SIZE);
                city.setText(tb.getCity());
                city.setTextSize(CURRENT_TEXT_SIZE);
            }
        }
    }

    private void initEmptyBlocks(int increment) {
        LinearLayout innerBlock;
        LinearLayout textblock;
        TextView type;
        TextView city;
        innerBlock = (LinearLayout) transportBlocks.getChildAt(increment);
        for (int innerIncrement = 0; innerIncrement < ELEMENTS_IN_BLOCK; innerIncrement++) {
            if (innerIncrement == 0) {
                ((ImageButton) innerBlock.getChildAt(innerIncrement)).setImageDrawable(emptBlkImage);
            }
            if (innerIncrement == 1) {
                textblock = ((LinearLayout) innerBlock.getChildAt(innerIncrement));
                type = ((TextView) (textblock.getChildAt(0)));
                city = ((TextView) (textblock.getChildAt(1)));
                if (CURRENT_LANGUAGE.equals("Russian")) {
                    type.setText("Нет данных");
                    city.setText("Нет данных");
                } else {
                    type.setText("No data");
                    city.setText("No data");
                }
                type.setTextSize(CURRENT_TEXT_SIZE);
                city.setTextSize(CURRENT_TEXT_SIZE);
            }
        }
    }

    private void initializeAfterLoadBlocks() {
        for (int i = 0; i < CURRENT_BLOCKS_COUNT; i++) {
            BlockElement tb = transports[i];
            if (tb != null) {
                initFilledBlock(tb, i);
            } else {
                initEmptyBlocks(i);
            }
        }
    }

    @Override
    protected void onDestroy() {
        saveTransportBlocksData();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        saveTransportBlocksData();
        super.onStop();
    }

    static void saveTransportBlocksData() {
        try {
            if (saveFile.length() != 0) {
                saveFile.delete();
                saveTransportBlocksData();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(MainActivity.saveFile));
            saveControlVars(writer);

            for (BlockElement tb : transports) {
                if (tb == null) {
                    writer.write("0");
                    writer.newLine();
                } else {
                    if (!tb.getTranspType().equals("metro")) {
                        saveTransport(writer, tb);

                    } else {
                        saveMetroSchema(writer, tb);
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), "Ошибка сохранения в файл", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveControlVars(BufferedWriter writer) {
        try {
            writer.write(String.valueOf(CURRENT_TEXT_SIZE));
            writer.write(' ');
            writer.write(String.valueOf(CURRENT_BLOCKS_COUNT));
            writer.write(' ');
            writer.write(String.valueOf(LAST_BLOCKS_COUNT));
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), "Ошибка сохранения контрольных переменных в файл", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveTransport(BufferedWriter writer, BlockElement tb) {
        try {
            writer.write(String.valueOf(tb.getTranspNumb()));
            writer.write(' ');

            writer.write(tb.getTranspType());
            writer.write(' ');

            writer.write(tb.getCity());
            writer.write(' ');

            writer.write(tb.getFakeCity());
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), "Ошибка сохранения транспортного блока в файл", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveMetroSchema(BufferedWriter writer, BlockElement tb) {
        try {
            writer.write(tb.getCity());
            writer.write(' ');
            writer.write(tb.getTranspType());
            writer.write(' ');
            writer.write(tb.getFakeCity());
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), "Ошибка сохранения схемы метро в файл", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTransportArray() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                read_ControlVars(reader);
                transports = new BlockElement[CURRENT_BLOCKS_COUNT + 1];
                changeBlocksCount();
                read_SaveFile(reader);
            } else {
                transports = new BlockElement[CURRENT_BLOCKS_COUNT];
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Ошибка загрузки из файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void read_ControlVars(BufferedReader reader) {
        try {
            String[] params = reader.readLine().split(" ");
            CURRENT_TEXT_SIZE = Integer.parseInt(params[0]);
            CURRENT_BLOCKS_COUNT = Integer.parseInt(params[1]);
            LAST_BLOCKS_COUNT = Integer.parseInt(params[2]);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Ошибка чтения управляющих переменных из файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void read_SaveFile(BufferedReader reader) {
        int increment = 0;
        String transportBlock;
        try {
            do {
                transportBlock = reader.readLine();
                if (transportBlock != null && !transportBlock.equals("0")) {
                    String[] splitted = transportBlock.split(" ");
                    if (splitted.length == 4) {
                        transports[increment] = new BlockElement(Integer.parseInt(splitted[0]), splitted[1], splitted[2], splitted[3]);
                    } else
                        transports[increment] = new BlockElement(splitted[0], splitted[1], splitted[2]);
                } else {
                    transports[increment] = null;
                }
                increment++;
            } while (increment < CURRENT_BLOCKS_COUNT);
            reader.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Ошибка чтения параметров транспорта из файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeBlocksCount() {
        if (CURRENT_BLOCKS_COUNT > LAST_BLOCKS_COUNT) {
            addBlocks(Math.abs(DEFAULT_BLOCKS_COUNT - CURRENT_BLOCKS_COUNT));
        } else if (CURRENT_BLOCKS_COUNT < LAST_BLOCKS_COUNT) {
            removeBlocks(Math.abs(LAST_BLOCKS_COUNT - CURRENT_BLOCKS_COUNT));
        }
        saveTransportBlocksData();
    }

    private void addBlocks(int howManyBlocksToAdd) {
        for (int i = 0; i < howManyBlocksToAdd; i++) {
            transports[CURRENT_BLOCKS_COUNT - howManyBlocksToAdd + i] = null;
            transportBlocks.getChildAt(CURRENT_BLOCKS_COUNT - howManyBlocksToAdd + i).setVisibility(View.VISIBLE);
        }
    }

    private void removeBlocks(int howManyBlocksToRemove) {
        if (CURRENT_BLOCKS_COUNT < LAST_BLOCKS_COUNT) {
            addBlocks(Math.abs(DEFAULT_BLOCKS_COUNT - CURRENT_BLOCKS_COUNT));
        }
        for (int i = 0; i < howManyBlocksToRemove; i++) {
            transports[CURRENT_BLOCKS_COUNT - i] = null;
            transportBlocks.getChildAt(CURRENT_BLOCKS_COUNT - i).setVisibility(View.GONE);
        }
    }

    private void startSetup() {
        if (CURRENT_BLOCKS_COUNT > DEFAULT_BLOCKS_COUNT) {
            instructions.setVisibility(View.GONE);
        } else if (CURRENT_BLOCKS_COUNT == DEFAULT_BLOCKS_COUNT) {
            instructions.setVisibility(View.VISIBLE);
        } else if (CURRENT_BLOCKS_COUNT < DEFAULT_BLOCKS_COUNT) {
            instructions.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < CURRENT_BLOCKS_COUNT; i++) {
            registerForContextMenu(transportBlocks.getChildAt(i));
        }
        emptBlkImage = AppCompatResources.getDrawable(this, R.drawable.emptblk);
        initializeAfterLoadBlocks();
    }

    private void startInit() {
        transportBlocks = findViewById(R.id.TransportBlocks);
        instructions = findViewById(R.id.Instructions);
        title = findViewById(R.id.Title);
        instructuonsText = findViewById(R.id.InstructionText);

        instructuonsText.setTextSize(CURRENT_TEXT_SIZE + 2);
        title.setTextSize(CURRENT_TEXT_SIZE + 6);
    }
}