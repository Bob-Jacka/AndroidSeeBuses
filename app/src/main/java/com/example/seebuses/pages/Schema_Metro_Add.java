package com.example.seebuses.pages;

import static com.example.seebuses.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.ControlVars.IS_RUSSIAN;

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

import com.example.seebuses.API;
import com.example.seebuses.BlockElement;
import com.example.seebuses.R;
import com.example.seebuses.tables.MetroCitiesTable;

import java.util.ArrayList;

public class Schema_Metro_Add extends AppCompatActivity {
    private Button goBackBtn, choose, chooseSchema;
    private String schemaCity, schemaFakeCity;
    private BlockElement schema;
    private TextView SchemaTitle, SchemaBlockText;
    private int acceptFlag;
    private final ArrayList<String[]> mc = IS_RUSSIAN ?
            MetroCitiesTable.initTable_ru() : MetroCitiesTable.initTable_en();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schema_metro_add);
        startSetUp();
        setTextSize();
        registerForContextMenu(choose);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        createContextMenu(menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void createContextMenu(ContextMenu menu) {
        menu.setHeaderTitle(R.string.CityWithMetro);
        for (String[] mcin : mc) {
            menu.add(mcin[0]);
        }
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

    @Override
    protected void onStop() {
        mc.clear();
        super.onStop();
    }

    public void onAccept(View view) {
        schema = new BlockElement(schemaCity, "metro", schemaFakeCity, IS_RUSSIAN ? "Метро" : "Metro");
        if (acceptSchema() == 1 && acceptFlag != 0) {
            int viewPointer = MainActivity.transportBlocks.indexOfChild(MainActivity.BlockView_pointer);
            MainActivity.transports[viewPointer] = schema;
            API.saveTransportBlocksData();
            Intent returnMain = new Intent(this, MainActivity.class);
            startActivity(returnMain);
        }
    }

    private int acceptSchema() {
        for (BlockElement sch : MainActivity.transports) {
            if (sch != null) {
                if (sch.getCity().equals(schema.getCity())) {
                    Toast.makeText(this, R.string.SuchSchema, Toast.LENGTH_SHORT).show();
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
        chooseSchema = findViewById(R.id.chooseSchema);
    }

    private void setTextSize() {
        SchemaTitle.setTextSize(CURRENT_TEXT_SIZE + 6);
        SchemaBlockText.setTextSize(CURRENT_TEXT_SIZE);
        choose.setTextSize(CURRENT_TEXT_SIZE);
        chooseSchema.setTextSize(CURRENT_TEXT_SIZE);
        goBackBtn.setTextSize(CURRENT_TEXT_SIZE);
    }
}