package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_LANGUAGE;
import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;

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
    private Button choseBtn;
    private final BlockElement transportBlock = new BlockElement();
    private String transpNumb;
    private String transpCity;
    private String transpType;
    private String fakeCity;
    private final ArrayList<String[]> ct = CURRENT_LANGUAGE.equals("Russian") ?
            CityTable.initTable_ru() : CityTable.initTable_en();
    private TextView insertTranspTypeText;
    private TextView chooseTranspType;
    private TextView chooseTranspCity;
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
        if (MainActivity.language.equals("Russian")) {
            addMenu_ruLocale(menu, v);
        } else {
            addMenu_enLocale(menu, v);
        }
    }

    private void addMenu_ruLocale(@NonNull ContextMenu menu, @NonNull View v) {
        if (v.getId() != R.id.choseCityBtn) {
            menu.setHeaderTitle("Типы транпорта");
            menu.add("Автобус");
            menu.add("Троллейбус");
            menu.add("Трамвай");

        } else if (v.getId() == R.id.choseCityBtn) {
            menu.setHeaderTitle("Города");
            menu.add("Ижевск");
            menu.add("Пермь");
        }
    }

    private void addMenu_enLocale(@NonNull ContextMenu menu, @NonNull View v) {
        if (v.getId() != R.id.choseCityBtn) {
            menu.setHeaderTitle("Transport types");
            menu.add("Bus");
            menu.add("Trolleybus");
            menu.add("Tram");

        } else if (v.getId() == R.id.choseCityBtn) {
            menu.setHeaderTitle("Cities");
            menu.add("Izhevsk");
            menu.add("Perm");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (MainActivity.language.equals("Russian")) {
            itemSelected_ruLocale(item);
        } else itemSelected_enLocale(item);
        return super.onContextItemSelected(item);
    }

    private void itemSelected_ruLocale(MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Автобус":
                transpType = "citybus";
                choseBtn.setText("Автобус");
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Троллейбус":
                transpType = "trolleybus";
                choseBtn.setText("Троллейбус");
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Трамвай":
                transpType = "tram";
                choseBtn.setText("Трамвай");
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
                transpType = "citybus";
                choseBtn.setText("Bus");
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Trolleybus":
                transpType = "trolleybus";
                choseBtn.setText("Trolleybus");
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            case "Tram":
                transpType = "tram";
                choseBtn.setText("Tram");
                choseBtn.setBackgroundColor(Color.GREEN);
                break;

            default:
                transpCity = (String) item.getTitle();
                choseCityBtn.setText(transpCity);
                choseCityBtn.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    public void onAccept(View view) {
        transportBlock.changeTransportType(transpType);
        transportBlock.changeTransportNumber(Integer.parseInt(transpNumb));
        transportBlock.changeCity(transpCity);
        getCity();
        transportBlock.changeFakeCity(fakeCity);
        if (acceptTransport() == 1) {
            if (acceptAllData() == 1) {
                Intent returnMain = new Intent(this, MainActivity.class);
                startActivity(returnMain);
            }
        }
    }

    private int acceptTransport() {
        for (BlockElement tb : MainActivity.transports) {
            if (tb != null) {
                if (tb.getTranspNumb() == transportBlock.getTranspNumb()
                        && tb.getTranspType().equals(transportBlock.getTranspType())
                        && tb.getCity().equals(transportBlock.getCity())) {
                    if (MainActivity.language.equals("Russian")) {
                        Toast.makeText(this, "Такой транспорт уже есть", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Such transport already exists", Toast.LENGTH_SHORT).show();
                    }
                    return 0;
                }
            }
        }
        return 1;
    }

    private void getCity() {
        for (int i = 0; i < ct.size(); i++) {
            String[] scanString = ct.get(i);
            if (scanString[0].equals(transpCity)) {
                fakeCity = scanString[1];
            }
        }
    }

    private int acceptAllData() {
        if (transportBlock.getTranspNumb() != 0 && transportBlock.getTranspType() != null && transportBlock.getCity() != null) {
            int viewPointer = MainActivity.transportBlocks.indexOfChild(MainActivity.BlockView_pointer);
            MainActivity.transports[viewPointer] = transportBlock;
            MainActivity.saveTransportBlocksData();
            return 1;
        } else {
            if (MainActivity.language.equals("Russian")) {
                Toast.makeText(Change_Transport.this, "Заполните все данные", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Change_Transport.this, "Fill in all the data", Toast.LENGTH_SHORT).show();
            }
            return 0;
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
        ApplyBtn.setTextSize(CURRENT_TEXT_SIZE);
        CancelBtn.setTextSize(CURRENT_TEXT_SIZE);
    }
}