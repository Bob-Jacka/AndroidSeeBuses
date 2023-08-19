package com.example.seebuses;

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

import java.util.HashMap;

public class Change_Transport extends AppCompatActivity {
    private TextInputEditText chooseTransportNum;
    private TextInputEditText chooseCity;
    private Button choseBtn;
    private static final TransportBlock transportBlock = new TransportBlock();
    private String transpNumb;
    private String transpCity;
    private String transpType;
    private final HashMap<String, String> ct = CityTable.initTable();
    private TextView insertTranspTypeText;
    private TextView chooseTranspType;
    private TextView insertTranspCity;
    private Button ApplyBtn;
    private Button CancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_transport);
        startSetUp();
        setTextSize();
        registerForContextMenu(choseBtn);
        chooseCity.addTextChangedListener(new TextWatcher() { // блок с заполнением города
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
                transpCity = String.valueOf(editable);
            }
        });
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
        menu.setHeaderTitle("Типы транпорта");
        menu.add(1, v.getId(), 1, "Автобус");
        menu.add(2, v.getId(), 2, "Троллейбус");
        menu.add(3, v.getId(), 3, "Трамвай");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
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
        }
        return super.onContextItemSelected(item);
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    public void onAccept(View view) {
        transportBlock.changeTransportType(transpType);
        transportBlock.changeTransportNumber(Integer.parseInt(transpNumb));
        if (acceptCity() == 1) {
            transportBlock.setTextViewText(transpType + " " + transpNumb);
            if (acceptTransport() == 1) {
                if (acceptAllData() == 1) {
                    Intent returnMain = new Intent(this, MainActivity.class);
                    startActivity(returnMain);
                }
            }
        }
    }

    private int acceptCity() {
        if (ct.containsKey(transpCity)) {
            transportBlock.changeCity(transpCity);
            transportBlock.changeFakeCity(ct.get(transpCity));
            return 1;
        }
        Toast.makeText(this, "Данный город не поддерживается", Toast.LENGTH_SHORT).show();
        return 0;
    }

    private int acceptTransport() {
        for (TransportBlock tb : MainActivity.transports) {
            if (tb != null) {
                if (tb.getTranspNumb() == transportBlock.getTranspNumb()
                        && tb.getTranspType().equals(transportBlock.getTranspType())
                        && tb.getCity().equals(transportBlock.getCity())) {
                    Toast.makeText(this, "Такой транспорт уже есть", Toast.LENGTH_SHORT).show();
                    return 0;
                }
            }
        }
        return 1;
    }

    private int acceptAllData() {
        if (transportBlock.getTranspNumb() != 0 && transportBlock.getTranspType() != null && transportBlock.getCity() != null) {
            int viewPointer = MainActivity.transportBlocks.indexOfChild(MainActivity.transportBlockView); //указатель на выбранный транспорт
            MainActivity.transports[viewPointer] = transportBlock;
            MainActivity.saveTransportData();
            return 1;
        } else {
            Toast.makeText(Change_Transport.this, "Заполните все данные", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private void startSetUp() {
        choseBtn = findViewById(R.id.choseBtn);
        chooseTransportNum = findViewById(R.id.chooseTransportNum);
        chooseCity = findViewById(R.id.chooseCity);
        insertTranspTypeText = findViewById(R.id.insertTranspTypeText);
        chooseTranspType = findViewById(R.id.chooseTranspType);
        insertTranspCity = findViewById(R.id.insertTranspCity);
        ApplyBtn = findViewById(R.id.ApplyBtn);
        CancelBtn = findViewById(R.id.CancelBtn);
    }

    private void setTextSize() {
        choseBtn.setTextSize(CURRENT_TEXT_SIZE);
        chooseTransportNum.setTextSize(CURRENT_TEXT_SIZE);
        chooseCity.setTextSize(CURRENT_TEXT_SIZE);
        insertTranspTypeText.setTextSize(CURRENT_TEXT_SIZE);
        chooseTranspType.setTextSize(CURRENT_TEXT_SIZE);
        insertTranspCity.setTextSize(CURRENT_TEXT_SIZE);
        ApplyBtn.setTextSize(CURRENT_TEXT_SIZE);
        CancelBtn.setTextSize(CURRENT_TEXT_SIZE);
    }
}