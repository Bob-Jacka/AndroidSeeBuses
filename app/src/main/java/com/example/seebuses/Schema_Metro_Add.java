package com.example.seebuses;

import static com.example.seebuses.Consts.CURRENT_TEXT_SIZE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Schema_Metro_Add extends AppCompatActivity {
    private Button goBackBtn;
    private Button choose;
    private String schemaCity;
    private String schemaFakeCity;
    private BlockElement schema;
    private final String schemaType = "metro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schema_metro_add);
        choose = findViewById(R.id.SchemaBlockChoose);
        goBackBtn = findViewById(R.id.goBackBtn);

        choose.setTextSize(CURRENT_TEXT_SIZE);
        goBackBtn.setTextSize(CURRENT_TEXT_SIZE);
        registerForContextMenu(choose);
    }

    public void goMain(View view) {
        Intent goMain = new Intent(this, MainActivity.class);
        startActivity(goMain);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Город с метро - ");
        menu.add(1, v.getId(), 1, "Москва");
        menu.add(2, v.getId(), 2, "Санкт-Петербурга");
        menu.add(3, v.getId(), 3, "Самара");
        menu.add(4, v.getId(), 4, "Екатеринбург");
        menu.add(5, v.getId(), 5, "Новосибирск");
        menu.add(6, v.getId(), 6, "Нижний-Новгород");
        menu.add(7, v.getId(), 7, "Казань");
        menu.add(8, v.getId(), 8, "Закрыть");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (String.valueOf(item.getTitle())) {
            case "Москва":
                acceptData("Москва");
                schemaFakeCity = "moscow";
                break;
            case "Санкт-Петербурга":
                acceptData("Санкт-Петербурга");
                schemaFakeCity = "spb";
                break;
            case "Самара":
                acceptData("Самара");
                schemaFakeCity = "samara";
                break;
            case "Екатеринбург":
                acceptData("Екатеринбург");
                schemaFakeCity = "ekaterinburg";
                break;
            case "Новосибирск":
                acceptData("Новосибирск");
                schemaFakeCity = "novosibirsk";
                break;
            case "Нижний-Новгород":
                acceptData("Нижний-Новгород");
                schemaFakeCity = "nizhniy-novgorod";
                break;
            case "Казань":
                acceptData("Казань");
                schemaFakeCity = "kazan";
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void onAccept(View view) {
        schema = new BlockElement(schemaCity, schemaType, schemaFakeCity);
        if (acceptSchema() == 1) {
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

    private void acceptData(String cityToAccept) {
        schemaCity = cityToAccept;
        choose.setText(cityToAccept);
        choose.setBackgroundColor(Color.GREEN);
    }
}