package com.example.seebuses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Button chooseLangBtn;
    private SeekBar howManyBlocks;
    private TextView currentBlocksCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        chooseLangBtn = findViewById(R.id.ChooseLanguageBtn);
        howManyBlocks = findViewById(R.id.ChooseHowManyBlocksBar);
        currentBlocksCount = findViewById(R.id.CurrentBlocksCount);

        howManyBlocks.setMax(Consts.MAX_BLOCKS);
        howManyBlocks.setProgress(Consts.CURRENT_BLOCKS_COUNT);
        currentBlocksCount.setText(String.valueOf(Consts.CURRENT_BLOCKS_COUNT));

        registerForContextMenu(chooseLangBtn);
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Выбор языка");
        menu.add(1, v.getId(), 1, "Русский");
        menu.add(2, v.getId(), 3, "Engish");
        menu.add(3, v.getId(), 3, "Закрыть");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Русский":
                Consts.CURRENT_LANGUAGE = "Ru";
                chooseLangBtn.setText("Русский");
                chooseLangBtn.setBackgroundColor(Color.GREEN);
                break;
            case "Engish":
                Consts.CURRENT_LANGUAGE = "Eng";
                chooseLangBtn.setText("English");
                chooseLangBtn.setBackgroundColor(Color.GREEN);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void setBlocksCount() {
        int progressBlocksCount = howManyBlocks.getProgress();
        if (progressBlocksCount != Consts.CURRENT_BLOCKS_COUNT) {
            Consts.CURRENT_BLOCKS_COUNT = progressBlocksCount;
        }
    }

}