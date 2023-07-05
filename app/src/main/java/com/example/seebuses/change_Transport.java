package com.example.seebuses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class change_Transport extends AppCompatActivity {
    private TextInputEditText chooseTransportNum;
    private TextInputEditText chooseCity;
    private Button choseBtn;
    private View chosenTransportBlock;
    private static final TransportBlock transportBlock = new TransportBlock();
    private int transpNumb;
    private String transpCity;
    private String transpType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_transport);

        choseBtn = findViewById(R.id.choseBtn);
        chooseTransportNum = findViewById(R.id.chooseTransportNum);
        chooseCity = findViewById(R.id.chooseCity);

        registerForContextMenu(choseBtn);  //for context menu

        choseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(change_Transport.this, "Загрузка изменений", Toast.LENGTH_SHORT).show();
            }
        });
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
                transpNumb = Integer.parseInt(editable.toString());
            }
        });
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        chosenTransportBlock = v;
        menu.setHeaderTitle("Типы транпорта");
        menu.add(1, v.getId(), 1, "Автобус");
        menu.add(2, v.getId(), 2, "Троллейбус");
        menu.add(3, v.getId(), 3, "Трамвай");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch ((String) item.getTitle()) {
            case "Автобус":
                transpType = "Автобус";
                choseBtn.setText(transpType);
                choseBtn.setBackgroundColor(Color.GREEN);
                Toast.makeText(change_Transport.this, "Тип транспорта изменён", Toast.LENGTH_SHORT).show();
                break;
            case "Троллейбус":
                transpType = "Троллейбус";
                choseBtn.setText(transpType);
                choseBtn.setBackgroundColor(Color.GREEN);
                Toast.makeText(change_Transport.this, "Тип транспорта изменён", Toast.LENGTH_SHORT).show();
                break;
            case "Трамвай":
                transpType = "Трамвай";
                choseBtn.setText(transpType);
                choseBtn.setBackgroundColor(Color.GREEN);
                Toast.makeText(change_Transport.this, "Тип транспорта изменён", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void goMain(View view) {
        Intent goWebBrowser = new Intent(this, MainActivity.class);
        startActivity(goWebBrowser);
    }

    public void onAccept(View view) {
        transportBlock.changeTransportType(transpType);
        transportBlock.changeTransportNumber(transpNumb);
        transportBlock.changeCity(transpCity);

        if(transportBlock.transpNumb != 0 && transportBlock.transpType != null && transportBlock.city != null) {
            Toast.makeText(change_Transport.this, "Все данные заполнены", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(change_Transport.this, "Заполните все данные", Toast.LENGTH_SHORT).show();
    }
}