package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;
import static com.example.seebuses.MainActivity.saveFile;
import static com.example.seebuses.MainActivity.saveTransportBlocksData;
import static com.example.seebuses.MainActivity.transports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private SeekBar howManyBlocks;
    private TextView title;
    private TextView gitHubLink;
    private TextView ChooseTextSizeText;
    private TextView ChangeHowManyBlocks;
    private TextView CurrentBlocksCount;
    private int fontSize = CURRENT_TEXT_SIZE;
    private int onAccept = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        startSetUp();

        howManyBlocks.setProgress(Consts.CURRENT_BLOCKS_COUNT);
        CurrentBlocksCount.setText(String.valueOf(Consts.CURRENT_BLOCKS_COUNT));

        changeTextSize();
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    public void deleteSave(View view) {
        Toast.makeText(Settings.this, "Файл удалён", Toast.LENGTH_SHORT).show();
        saveFile.delete();
        transports = new BlockElement[CURRENT_BLOCKS_COUNT];
    }

    public void applySettings(View view) {
        acceptBlocksCount();
        if (fontSize > 10 && fontSize < 40) {
            CURRENT_TEXT_SIZE = fontSize;
            onAccept++;
        }
        if (onAccept != 0) {
            recreate();
            Toast.makeText(this, "Изменения применены", Toast.LENGTH_SHORT).show();
            saveTransportBlocksData();
        }
    }

    private void acceptBlocksCount() {
        int progressBlocksCount = howManyBlocks.getProgress();
        if (progressBlocksCount != Consts.CURRENT_BLOCKS_COUNT) {
            if (progressBlocksCount > 3) {
                Consts.LAST_BLOCKS_COUNT = CURRENT_BLOCKS_COUNT;
                CURRENT_BLOCKS_COUNT = progressBlocksCount;
                CurrentBlocksCount.setText(String.valueOf(Consts.CURRENT_BLOCKS_COUNT));
                onAccept++;
            } else {
                Toast.makeText(this, "Количество блоков равное 3 или ниже не поддерживается", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startSetUp() {
        howManyBlocks = findViewById(R.id.ChooseHowManyBlocksBar);
        title = findViewById(R.id.SettingsTitle);
        gitHubLink = findViewById(R.id.GitHubLinkText);
        ChooseTextSizeText = findViewById(R.id.ChooseTextSizeText);
        ChangeHowManyBlocks = findViewById(R.id.ChangeHowManyBlocks);
        CurrentBlocksCount = findViewById(R.id.CurrentBlocksCount);
    }

    private void changeTextSize() {
        title.setTextSize(CURRENT_TEXT_SIZE + 4);
        gitHubLink.setTextSize(CURRENT_TEXT_SIZE);
        ChooseTextSizeText.setTextSize(CURRENT_TEXT_SIZE);
        ChangeHowManyBlocks.setTextSize(CURRENT_TEXT_SIZE);
        CurrentBlocksCount.setTextSize(CURRENT_TEXT_SIZE);
    }

    public void increase_font(View view) {
        fontSize += 2;
        Toast.makeText(this, "Размер шрифта: " + fontSize, Toast.LENGTH_SHORT).show();
    }

    public void decrease_font(View view) {
        fontSize -= 2;
        Toast.makeText(this, "Размер шрифта: " + fontSize, Toast.LENGTH_SHORT).show();
    }
}