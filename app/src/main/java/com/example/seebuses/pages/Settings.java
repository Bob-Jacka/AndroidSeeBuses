package com.example.seebuses.pages;

import static com.example.seebuses.core.SaveModule.saveTransportBlocksData;
import static com.example.seebuses.core.data.ControlVars.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.core.data.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.core.data.ControlVars.DEFAULT_BLOCKS_COUNT;
import static com.example.seebuses.core.data.ControlVars.IS_RUSSIAN;
import static com.example.seebuses.core.data.ControlVars.LAST_BLOCKS_COUNT;
import static com.example.seebuses.core.data.ControlVars.MAX_BLOCKS;
import static com.example.seebuses.core.data.ControlVars.MAX_FONT_SIZE;
import static com.example.seebuses.core.data.ControlVars.MIN_BLOCKS;
import static com.example.seebuses.core.data.ControlVars.MIN_FONT_SIZE;
import static com.example.seebuses.pages.MainActivity.saveFile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seebuses.R;
import com.example.seebuses.core.data.ControlVars;

public class Settings extends AppCompatActivity {

    private SeekBar howManyBlocks;
    private TextView title, gitHubLink, ChooseTextSizeText, ChangeHowManyBlocks, CurrentBlocksCount;
    private Button deleteBtn, GoMain, AcceptSettings;
    private int fontSize = CURRENT_TEXT_SIZE;
    private String fontText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        startSetUp();
        if (IS_RUSSIAN) {
            fontText = "Размер шрифта: ";
        } else fontText = "Font size: ";
        howManyBlocks.setProgress(CURRENT_BLOCKS_COUNT);
        howManyBlocks.setMax(MAX_BLOCKS);
        CurrentBlocksCount.setText(String.valueOf(CURRENT_BLOCKS_COUNT));
        changeTextSize();
    }

    @Override
    public void onBackPressed() {
        goMain(this.getCurrentFocus());
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    public void deleteSave(View view) {
        Toast.makeText(Settings.this, R.string.FileDelete, Toast.LENGTH_SHORT).show();
        saveFile.delete();
        CURRENT_BLOCKS_COUNT = DEFAULT_BLOCKS_COUNT;
        CurrentBlocksCount.setText(String.valueOf(CURRENT_BLOCKS_COUNT));
        howManyBlocks.setProgress(CURRENT_BLOCKS_COUNT);
    }

    public void applySettings(View view) {
        if (acceptBlocksCount() || acceptFontSize()) {
            saveTransportBlocksData();
            goMain(this.getCurrentFocus());
            Toast.makeText(this, R.string.ChngApplied, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean acceptBlocksCount() {
        final int progressBlocksCount = howManyBlocks.getProgress();
        if (progressBlocksCount != CURRENT_BLOCKS_COUNT) {
            if (progressBlocksCount > MIN_BLOCKS) {
                LAST_BLOCKS_COUNT = CURRENT_BLOCKS_COUNT;
                CURRENT_BLOCKS_COUNT = progressBlocksCount;
                CurrentBlocksCount.setText(String.valueOf(ControlVars.CURRENT_BLOCKS_COUNT));
                return true;
            } else {
                Toast.makeText(this, R.string.BlocksNotSupp, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }

    private boolean acceptFontSize() {
        if (fontSize != CURRENT_TEXT_SIZE) {
            if (fontSize <= MAX_FONT_SIZE && fontSize >= MIN_FONT_SIZE) {
                CURRENT_TEXT_SIZE = fontSize;
                return true;
            } else {
                Toast.makeText(this, R.string.FontNotSupp, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }

    private void startSetUp() {
        howManyBlocks = findViewById(R.id.ChooseHowManyBlocksBar);
        title = findViewById(R.id.SettingsTitle);
        gitHubLink = findViewById(R.id.GitHubLinkText);
        ChooseTextSizeText = findViewById(R.id.ChooseTextSizeText);
        ChangeHowManyBlocks = findViewById(R.id.ChangeHowManyBlocks);
        CurrentBlocksCount = findViewById(R.id.CurrentBlocksCount);

        AcceptSettings = findViewById(R.id.AcceptSettings);
        GoMain = findViewById(R.id.GoMain);
        deleteBtn = findViewById(R.id.deleteBtn);
    }

    private void changeTextSize() {
        title.setTextSize(CURRENT_TEXT_SIZE + 6);
        gitHubLink.setTextSize(CURRENT_TEXT_SIZE);
        ChooseTextSizeText.setTextSize(CURRENT_TEXT_SIZE);
        ChangeHowManyBlocks.setTextSize(CURRENT_TEXT_SIZE);
        CurrentBlocksCount.setTextSize(CURRENT_TEXT_SIZE);

        AcceptSettings.setTextSize(CURRENT_TEXT_SIZE);
        GoMain.setTextSize(CURRENT_TEXT_SIZE);
        deleteBtn.setTextSize(CURRENT_TEXT_SIZE);
    }

    public void increase_font(View view) {
        fontSize += 2;
        Toast.makeText(this, fontText + fontSize, Toast.LENGTH_SHORT).show();
    }

    public void decrease_font(View view) {
        fontSize -= 2;
        Toast.makeText(this, fontText + fontSize, Toast.LENGTH_SHORT).show();
    }
}