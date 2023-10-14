package com.example.seebuses;

import static com.example.seebuses.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.ControlVars.IS_RUSSIAN;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Change_Transport extends AppCompatActivity {
    private TextInputEditText chooseTransportNum;
    private BlockElement transportBlock = null;
    private String transpNumb;
    private String transpCity;
    private String typeForSearch;
    private String fakeCity;
    private String viewText;
    private final ArrayList<String[]> ct = IS_RUSSIAN ? CityTable.initTable_ru() : CityTable.initTable_en();
    private TextView insertTranspTypeText;
    private TextView chooseTranspType;
    private TextView chooseTranspCity;
    private Button choseBtn;
    private Button ApplyBtn;
    private Button CancelBtn;
    private Button choseCityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_transport);
        startSetUp();
        setTextSize();
        registerForContextMenu(choseBtn);
        registerForContextMenu(choseCityBtn);
        chooseTransportNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //при введении символа
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //во время введения символа
            }

            @Override
            public void afterTextChanged(Editable editable) {
                transpNumb = String.valueOf(editable);
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        addMenu(menu, v);
    }

    private void addMenu(@NonNull ContextMenu menu, @NonNull View v) {
        if (v.getId() != R.id.choseCityBtn) {
            menu.setHeaderTitle(R.string.TransportTypes);
            menu.add(R.string.Bus);
            menu.add(R.string.Trolleybus);
            menu.add(R.string.Tram);

        } else {
            menu.setHeaderTitle(R.string.Cities);
            for (String[] block : ct) {
                menu.add(block[0]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (IS_RUSSIAN) {
            itemSelected_ruLocale(item);
        } else itemSelected_enLocale(item);
        return super.onContextItemSelected(item);
    }

    private void itemSelected_ruLocale(MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Автобус":
                typeForSearch = "citybus";
                viewText = "Автобус";
                choseBtn.setText(R.string.Bus);
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Троллейбус":
                typeForSearch = "trolleybus";
                viewText = "Троллейбус";
                choseBtn.setText(R.string.Trolleybus);
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Трамвай":
                typeForSearch = "tram";
                viewText = "Трамвай";
                choseBtn.setText(R.string.Tram);
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            default:
                transpCity = (String) item.getTitle();
                choseCityBtn.setText(transpCity);
                choseCityBtn.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    private void itemSelected_enLocale(MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Bus":
                typeForSearch = "citybus";
                viewText = "Bus";
                choseBtn.setText(R.string.Bus);
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Trolleybus":
                typeForSearch = "trolleybus";
                viewText = "Trolleybus";
                choseBtn.setText(R.string.Trolleybus);
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Tram":
                typeForSearch = "tram";
                viewText = "Tram";
                choseBtn.setText(R.string.Tram);
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            default:
                transpCity = (String) item.getTitle();
                choseCityBtn.setText(transpCity);
                choseCityBtn.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        goMain(this.getCurrentFocus());
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    @Override
    protected void onStop() {
        ct.clear();
        super.onStop();
    }

    public void onAccept(View view) {
        checkCity();
        if (acceptNonNull() == 1) {
            if (acceptTransport() == 1) {
                int viewPointer = MainActivity.transportBlocks.indexOfChild(MainActivity.BlockView_pointer);
                MainActivity.transports[viewPointer] = transportBlock;
                MainActivity.saveTransportBlocksData();
                Intent returnMain = new Intent(this, MainActivity.class);
                startActivity(returnMain);
            }
        }
    }

    private int acceptNonNull() {
        if (transpNumb != null && typeForSearch != null && transpCity != null) {
            final int transpNumber = Integer.parseInt(transpNumb);
            transportBlock = new BlockElement(transpNumber, typeForSearch, transpCity, fakeCity, viewText + "_" + transpNumber);
            return 1;
        } else {
            Toast.makeText(Change_Transport.this, R.string.FillAllData, Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private int acceptTransport() {
        for (BlockElement tb : MainActivity.transports) {
            if (tb != null) {
                if ((tb.getTranspNumb() == Integer.parseInt(transpNumb) && tb.getTypeForSearch().equals(typeForSearch) && tb.getCity().equals(transpCity))) {
                    Toast.makeText(this, R.string.SuchTransp, Toast.LENGTH_SHORT).show();
                    return 0;
                }
            }
        }
        return 1;
    }

    private void checkCity() {
        for (int i = 0; i < ct.size(); i++) {
            String[] scanString = ct.get(i);
            if (scanString[0].equals(transpCity)) {
                fakeCity = scanString[1];
            }
        }
    }

    private void startSetUp() {
        chooseTransportNum = findViewById(R.id.chooseTransportNum);
        insertTranspTypeText = findViewById(R.id.insertTranspTypeText);
        chooseTranspType = findViewById(R.id.chooseTranspType);
        chooseTranspCity = findViewById(R.id.insertTranspCity);

        choseBtn = findViewById(R.id.choseBtn);
        ApplyBtn = findViewById(R.id.ApplyBtn);
        CancelBtn = findViewById(R.id.CancelBtn);
        choseCityBtn = findViewById(R.id.choseCityBtn);
    }

    private void setTextSize() {
        chooseTransportNum.setTextSize(CURRENT_TEXT_SIZE);
        insertTranspTypeText.setTextSize(CURRENT_TEXT_SIZE);
        chooseTranspType.setTextSize(CURRENT_TEXT_SIZE);
        chooseTranspCity.setTextSize(CURRENT_TEXT_SIZE);

        choseBtn.setTextSize(CURRENT_TEXT_SIZE);
        choseCityBtn.setTextSize(CURRENT_TEXT_SIZE);
        ApplyBtn.setTextSize(CURRENT_TEXT_SIZE);
        CancelBtn.setTextSize(CURRENT_TEXT_SIZE);
    }
}