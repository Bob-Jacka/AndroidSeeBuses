package com.example.seebuses.pages;

import static com.example.seebuses.API.loadTransportArray;
import static com.example.seebuses.API.saveTransportBlocksData;
import static com.example.seebuses.ControlVars.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.ControlVars.DEFAULT_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.ELEMENTS_IN_BLOCK;
import static com.example.seebuses.ControlVars.SAVE_FILE_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
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

import com.example.seebuses.BlockElement;
import com.example.seebuses.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static LinearLayout transportBlocks;
    public static BlockElement[] transports = new BlockElement[DEFAULT_BLOCKS_COUNT];
    static View BlockView_pointer;
    private Drawable emptBlkImage;
    public static File saveFile;
    private ConstraintLayout instructions;
    private TextView instructionsText, title;
    private int exit = 0;

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
        exit++;
        switch (exit) {
            case 1:
                Toast.makeText(this, R.string.toExit, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                finishAffinity();
                break;
        }
    }

    public final void seeTransportUltimate(View view) {
        int pointer = transportBlocks.indexOfChild((LinearLayout) (view.getParent()));
        BlockElement tb = transports[pointer];
        if (tb != null) {
            WebBrowser.getURL(tb);
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
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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
                Intent addTransport = new Intent(new Intent(this, Transport_Action.class));
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
        if (transports[transportBlocks.indexOfChild(BlockView_pointer)].getType().equals("metro")) {
            Intent changeTransp = new Intent(new Intent(this, Schema_Metro_Add.class));
            startActivity(changeTransp);
        } else {
            Intent changeTransp = new Intent(new Intent(this, Transport_Action.class));
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
                switch (tb.getType()) {
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
                city.setText(tb.getCity());
                type.setTextSize(CURRENT_TEXT_SIZE - 1);
                city.setTextSize(CURRENT_TEXT_SIZE - 1);
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
    protected void onPause() {
        saveTransportBlocksData();
        super.onPause();
    }

    private void startSetup() {
        if (CURRENT_BLOCKS_COUNT > DEFAULT_BLOCKS_COUNT) {
            instructions.setVisibility(View.GONE);
        } else {
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