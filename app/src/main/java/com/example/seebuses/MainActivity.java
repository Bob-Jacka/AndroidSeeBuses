package com.example.seebuses;

import static com.example.seebuses.ControlVars.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.ControlVars.DEFAULT_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.ELEMENTS_IN_BLOCK;
import static com.example.seebuses.ControlVars.LAST_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.MAX_BLOCKS;
import static com.example.seebuses.ControlVars.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private TextView instructionsText;
    private TextView title;

    @SuppressLint("UsableSpace")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String saveFileName = getApplicationContext().getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;
        saveFile = new File(saveFileName);
        transportBlocks = findViewById(R.id.TransportBlocks);
        loadTransportArray();
        startInit();
        startSetup();
    }

    @Override
    public void onBackPressed() {

    }

    public final void seeTransportUltimate(View view) {
        int pointer = transportBlocks.indexOfChild((LinearLayout) (view.getParent()));
        BlockElement tb = transports[pointer];
        if (tb != null) {
            if (!tb.getTypeForSearch().equals("metro")) {
                if (!tb.getCity().equals("Ижевск") && !tb.getCity().equals("Izhevsk")) {
                    WebBrowser.URL = tb.getTransportURI_BUSTI();
                } else WebBrowser.URL = tb.getTransportURI_IGIS();
            } else WebBrowser.URL = tb.getSchemaURI_YandexMetro();
            goWebBrowser();
        }
    }

    private void goWebBrowser() {
        if (isInternetAvailable()) {
            Toast.makeText(this, R.string.Wait, Toast.LENGTH_LONG).show();
            Intent goWebBrowser = new Intent(this, WebBrowser.class);
            startActivity(goWebBrowser);
        } else {
            Toast.makeText(this, R.string.CheckInternet, Toast.LENGTH_LONG).show();
        }
    }

    public void goSettings(View view) {
        Intent goSettings = new Intent(this, Settings.class);
        startActivity(goSettings);
    }

    private boolean isInternetAvailable() {
        try {
            return Runtime.getRuntime().exec("ping -c 1 yandex.ru").waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        BlockView_pointer = v;
        addMenu(menu, v);
    }

    private void addMenu(ContextMenu menu, View v) {
        if (transports[transportBlocks.indexOfChild(v)] == null) {
            menu.setHeaderTitle(R.string.Add);
            menu.add(R.string.Transport);
            menu.add(R.string.MetroMap);
            menu.add(R.string.Close);

        } else {
            menu.setHeaderTitle(R.string.Transport2);
            menu.add(R.string.Delete);
            menu.add(R.string.Change);
            menu.add(R.string.Close);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        chooseItem(item);
        return super.onContextItemSelected(item);
    }

    private void chooseItem(MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Delete":
            case "Удалить":
                deleteTransport();
                break;
            case "Change":
            case "Изменить":
                changeTransport();
                break;
            case "Transport":
            case "Транспорт":
                Intent addTransport = new Intent(new Intent(this, Change_Transport.class));
                startActivity(addTransport);
                break;
            case "Metro map":
            case "Cхему метро":
                Intent goSchema = new Intent(this, Schema_Metro_Add.class);
                startActivity(goSchema);
                break;
        }
    }

    private void changeTransport() {
        if (transports[transportBlocks.indexOfChild(BlockView_pointer)].getTypeForSearch().equals("metro")) {
            Intent changeTransp = new Intent(new Intent(this, Schema_Metro_Add.class));
            startActivity(changeTransp);
        } else {
            Intent changeTransp = new Intent(new Intent(this, Change_Transport.class));
            startActivity(changeTransp);
        }
    }

    private void deleteTransport() {
        transports[transportBlocks.indexOfChild(BlockView_pointer)] = null;
        eraseDeletedTB();
        saveTransportBlocksData();
    }

    private void eraseDeletedTB() {
        LinearLayout ll = (LinearLayout) transportBlocks.getChildAt(transportBlocks.indexOfChild(BlockView_pointer));
        View innerView;
        TextView type;
        TextView city;
        for (int i = 0; i < ELEMENTS_IN_BLOCK; i++) {
            innerView = ll.getChildAt(i);
            if (i == 0) {
                Animation anim_evince;
                ImageButton view = findViewById(innerView.getId());
                anim_evince = AnimationUtils.loadAnimation(this, R.anim.alpha_evince);
                ((ImageButton) innerView).setImageDrawable(emptBlkImage);
                view.startAnimation(anim_evince);
            }
            if (i == 1) {
                type = ((TextView) ((LinearLayout) innerView).getChildAt(0));
                city = ((TextView) ((LinearLayout) innerView).getChildAt(1));
                MediaPlayer.create(this, R.raw.deleting_sound).start();
                evinceTV(type);
                evinceTV(city);
            }
        }
    }

    private void evinceTV(TextView tv) {
        TextView view = findViewById(tv.getId());
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.alpha_evince);
        tv.setText(R.string.NoData);
        tv.setTextSize(CURRENT_TEXT_SIZE);
        view.startAnimation(anim);
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
                switch (tb.getTypeForSearch()) {
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

                type.setText(tb.getViewText());
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
                type.setText(R.string.NoData);
                city.setText(R.string.NoData);
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
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        saveTransportBlocksData();
        super.onPause();
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
                    if (!tb.getTypeForSearch().equals("metro")) {
                        saveTransport(writer, tb);
                    } else {
                        saveMetroSchema(writer, tb);
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveFile, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveContVars, Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveTransport(BufferedWriter writer, BlockElement tb) {
        try {
            writer.write(String.valueOf(tb.getTranspNumb()));
            writer.write(' ');
            writer.write(tb.getTypeForSearch());
            writer.write(' ');
            writer.write(tb.getCity());
            writer.write(' ');
            writer.write(tb.getFakeCity());
            writer.write(' ');
            writer.write(tb.getViewText());

            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveTB, Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveMetroSchema(BufferedWriter writer, BlockElement tb) {
        try {
            writer.write(tb.getCity());
            writer.write(' ');
            writer.write(tb.getTypeForSearch());
            writer.write(' ');
            writer.write(tb.getFakeCity());
            writer.write(' ');
            writer.write(tb.getViewText());
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveMetroSchema, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTransportArray() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                read_ControlVars(reader);
                transports = new BlockElement[MAX_BLOCKS];
                changeBlocksCount();
                read_SaveFile(reader);
            } else {
                transports = new BlockElement[DEFAULT_BLOCKS_COUNT];
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, R.string.ErrLoadFromFile, Toast.LENGTH_SHORT).show();
        }
    }

    private void read_ControlVars(BufferedReader reader) {
        try {
            String[] params = reader.readLine().split(" ");
            CURRENT_TEXT_SIZE = Integer.parseInt(params[0]);
            CURRENT_BLOCKS_COUNT = Integer.parseInt(params[1]);
            LAST_BLOCKS_COUNT = Integer.parseInt(params[2]);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, R.string.ErrReadCntrlVars, Toast.LENGTH_SHORT).show();
        }
    }

    private void read_SaveFile(BufferedReader reader) {
        int increment = 0;
        String saveLine;
        try {
            do {
                saveLine = reader.readLine();
                if (saveLine != null && !saveLine.equals("0")) {
                    String[] splitted = saveLine.split(" ");
                    if (splitted.length == 5) {
                        transports[increment] = new BlockElement(Integer.parseInt(splitted[0]), splitted[1], splitted[2], splitted[3], splitted[4]);
                    } else
                        transports[increment] = new BlockElement(splitted[0], splitted[1], splitted[2], splitted[3]);
                } else {
                    transports[increment] = null;
                }
                increment++;
            } while (increment < CURRENT_BLOCKS_COUNT);
            reader.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, R.string.ErrReadTBParams, Toast.LENGTH_SHORT).show();
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
        for (int i = 1; i <= howManyBlocksToRemove; i++) {
            transports[CURRENT_BLOCKS_COUNT + howManyBlocksToRemove - i] = null;
            transportBlocks.getChildAt(CURRENT_BLOCKS_COUNT + howManyBlocksToRemove - i).setVisibility(View.GONE);
        }
    }

    private void startSetup() {
        if (CURRENT_BLOCKS_COUNT > DEFAULT_BLOCKS_COUNT) {
            instructions.setVisibility(View.GONE);
        } else if (CURRENT_BLOCKS_COUNT <= DEFAULT_BLOCKS_COUNT) {
            instructions.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < CURRENT_BLOCKS_COUNT; i++) {
            registerForContextMenu(transportBlocks.getChildAt(i));
        }
        emptBlkImage = AppCompatResources.getDrawable(this, R.drawable.emptblk);
        initializeAfterLoadBlocks();
    }

    private void startInit() {
        instructions = findViewById(R.id.Instructions);
        title = findViewById(R.id.Title);
        instructionsText = findViewById(R.id.InstructionText);

        instructionsText.setTextSize(CURRENT_TEXT_SIZE + 2);
        title.setTextSize(CURRENT_TEXT_SIZE + 6);
    }
}