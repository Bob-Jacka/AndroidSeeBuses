package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.Consts.CURRENT_LANGUAGE;
import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;
import static com.example.seebuses.MainActivity.saveFile;
import static com.example.seebuses.MainActivity.saveTransportData;
import static com.example.seebuses.MainActivity.transports;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Button chooseLangBtn;
    private SeekBar howManyBlocks;
    private String selectedLanguage;
    private TextView title;
    private TextView gitHubLink;
    private TextView ChangeLangText;
    private TextView ChooseTextSizeText;
    private TextView ChangeHowManyBlocks;
    private TextView CurrentBlocksCount;
    private int fontSize = CURRENT_TEXT_SIZE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        startSetUp();

        chooseLangBtn.setText(Consts.CURRENT_LANGUAGE);

        howManyBlocks.setProgress(Consts.CURRENT_BLOCKS_COUNT);
        CurrentBlocksCount.setText(String.valueOf(Consts.CURRENT_BLOCKS_COUNT));

        changeTextSize();

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
                selectedLanguage = "Ru";
                chooseLangBtn.setText("Русский");
                chooseLangBtn.setBackgroundColor(Color.GREEN);
                break;
            case "Engish":
                selectedLanguage = "Eng";
                chooseLangBtn.setText("English");
                chooseLangBtn.setBackgroundColor(Color.GREEN);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void deleteSave(View view) {
        Toast.makeText(Settings.this, "Файл удалён", Toast.LENGTH_SHORT).show();
        saveFile.delete();
        transports = new TransportBlock[CURRENT_BLOCKS_COUNT];
    }

    public void applySettings(View view) {
        int progressBlocksCount = howManyBlocks.getProgress();
        if (progressBlocksCount != Consts.CURRENT_BLOCKS_COUNT) {
            if (progressBlocksCount > 3) {
                Consts.LAST_BLOCKS_COUNT = CURRENT_BLOCKS_COUNT;
                CURRENT_BLOCKS_COUNT = progressBlocksCount;
                CurrentBlocksCount.setText(String.valueOf(Consts.CURRENT_BLOCKS_COUNT));
            } else {
                Toast.makeText(this, "Количество блоков равное 3 или ниже не поддерживается", Toast.LENGTH_SHORT).show();
            }
        }
        if (selectedLanguage != null) {
            Consts.CURRENT_LANGUAGE = selectedLanguage;
        } else CURRENT_LANGUAGE = CURRENT_LANGUAGE;
        if (fontSize > 10 && fontSize < 40) {
            CURRENT_TEXT_SIZE = fontSize;
        }
        recreate();
        Toast.makeText(this, "Изменения применены", Toast.LENGTH_SHORT).show();
        saveTransportData();
    }

    private void startSetUp() {
        chooseLangBtn = findViewById(R.id.ChooseLanguageBtn);
        howManyBlocks = findViewById(R.id.ChooseHowManyBlocksBar);
        title = findViewById(R.id.SettingsTitle);
        gitHubLink = findViewById(R.id.GitHubLinkText);
        ChangeLangText = findViewById(R.id.ChangeLanguageText);
        ChooseTextSizeText = findViewById(R.id.ChooseTextSizeText);
        ChangeHowManyBlocks = findViewById(R.id.ChangeHowManyBlocks);
        CurrentBlocksCount = findViewById(R.id.CurrentBlocksCount);
    }

    private void changeTextSize() {
        title.setTextSize(CURRENT_TEXT_SIZE + 4);
        gitHubLink.setTextSize(CURRENT_TEXT_SIZE);
        ChangeLangText.setTextSize(CURRENT_TEXT_SIZE);
        ChooseTextSizeText.setTextSize(CURRENT_TEXT_SIZE);
        ChangeHowManyBlocks.setTextSize(CURRENT_TEXT_SIZE);
        CurrentBlocksCount.setTextSize(CURRENT_TEXT_SIZE);
    }

    public void fontUp(View view) {
        fontSize += 2;
        Toast.makeText(this, "Размер шрифта: " + fontSize, Toast.LENGTH_SHORT).show();
    }

    public void fontDown(View view) {
        fontSize -= 2;
        Toast.makeText(this, "Размер шрифта: " + fontSize, Toast.LENGTH_SHORT).show();
    }
}