package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_LANGUAGE;
import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Schema_Metro_Add extends AppCompatActivity {
    private Button goBackBtn;
    private Button choose;
    private String schemaCity;
    private String schemaFakeCity;
    private BlockElement schema;
    private TextView SchemaTitle;
    private TextView SchemaBlockText;
    private final String schemaType = "metro";
    private int acceptFlag;
    private final ArrayList<String[]> mc = CURRENT_LANGUAGE.equals("Russian") ?
            MetroCitiesTable.initTable_ru() : MetroCitiesTable.initTable_en();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schema_metro_add);
        startSetUp();
        setTextSize();
        registerForContextMenu(choose);
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (CURRENT_LANGUAGE.equals("Russian")) {
            createContextMenu_ruLocale(menu);
        } else createContextMenu_enLocale(menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void createContextMenu_ruLocale(ContextMenu menu) {
        menu.setHeaderTitle("Город с метро - ");
        menu.add("Москва");
        menu.add("Санкт-Петербурга");
        menu.add("Самара");
        menu.add("Екатеринбург");
        menu.add("Новосибирск");
        menu.add("Нижний-Новгород");
        menu.add("Казань");
        menu.add("Измир");
        menu.add("Стамбул");
        menu.add("Тбилиси");
    }

    private void createContextMenu_enLocale(ContextMenu menu) {
        menu.setHeaderTitle("City with metro - ");
        menu.add("Moscow");
        menu.add("Saint-petersburg");
        menu.add("Samara");
        menu.add("Ekaterinburg");
        menu.add("Novosibirsk");
        menu.add("Nizhniy-novgorod");
        menu.add("Kazan");
        menu.add("Izmir");
        menu.add("Istanbul");
        menu.add("Tbilisi");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String cityName = (String) item.getTitle();
        for (String[] strArr : mc) {
            if (strArr[0].equals(cityName)) {
                schemaFakeCity = strArr[1];
                acceptFlag++;
            }
        }
        enterData(cityName);
        return super.onContextItemSelected(item);
    }


    public void onAccept(View view) {
        schema = new BlockElement(schemaCity, schemaType, schemaFakeCity);
        if (acceptSchema() == 1 && acceptFlag != 0) {
            int viewPointer = MainActivity.transportBlocks.indexOfChild(MainActivity.BlockView_pointer);
            MainActivity.transports[viewPointer] = schema;
            MainActivity.saveTransportBlocksData();
            Intent returnMain = new Intent(this, MainActivity.class);
            startActivity(returnMain);
        }
    }

    private int acceptSchema() {
        for (BlockElement sch : MainActivity.transports) {
            if (sch != null) {
                if (sch.getCity().equals(schema.getCity())) {
                    Toast.makeText(this, "Такая схема уже есть", Toast.LENGTH_SHORT).show();
                    return 0;
                }
            }
        }
        return 1;
    }

    private void enterData(String cityToAccept) {
        schemaCity = cityToAccept;
        choose.setText(cityToAccept);
        choose.setBackgroundColor(Color.GREEN);
    }

    private void startSetUp() {
        choose = findViewById(R.id.SchemaBlockChoose);
        goBackBtn = findViewById(R.id.goBackBtn);
        SchemaTitle = findViewById(R.id.SchemaTitle);
        SchemaBlockText = findViewById(R.id.SchemaBlockText);
    }

    private void setTextSize() {
        SchemaTitle.setTextSize(CURRENT_TEXT_SIZE + 6);
        SchemaBlockText.setTextSize(CURRENT_TEXT_SIZE);
        choose.setTextSize(CURRENT_TEXT_SIZE);
        goBackBtn.setTextSize(CURRENT_TEXT_SIZE);
    }
}