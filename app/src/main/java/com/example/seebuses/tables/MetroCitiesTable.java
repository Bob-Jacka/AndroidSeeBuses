package com.example.seebuses.tables;

import static com.example.seebuses.ControlVars.SUPPORTED_METRO_CITIES;

import java.util.ArrayList;

public abstract class MetroCitiesTable {
    private static final ArrayList<String[]> cityTable = new ArrayList<>(SUPPORTED_METRO_CITIES);

    public static ArrayList<String[]> initTable_ru() {
        cityTable.add(new String[]{"Москва", "moscow"});
        cityTable.add(new String[]{"Санкт-Петербург", "spb"});
        cityTable.add(new String[]{"Самара", "samara"});
        cityTable.add(new String[]{"Екатеринбург", "ekaterinburg"});
        cityTable.add(new String[]{"Новосибирск", "novosibirsk"});
        cityTable.add(new String[]{"Нижний-Новгород", "nizhniy-novgorod"});
        cityTable.add(new String[]{"Казань", "kazan"});
        cityTable.add(new String[]{"Измир", "izmir"});
        cityTable.add(new String[]{"Стамбул", "istanbul"});
        cityTable.add(new String[]{"Тбилиси", "tbilisi"});
        return cityTable;
    }

    public static ArrayList<String[]> initTable_en() {
        cityTable.add(new String[]{"Moscow", "moscow"});
        cityTable.add(new String[]{"Saint-petersburg", "spb"});
        cityTable.add(new String[]{"Samara", "samara"});
        cityTable.add(new String[]{"Ekaterinburg", "ekaterinburg"});
        cityTable.add(new String[]{"Novosibirsk", "novosibirsk"});
        cityTable.add(new String[]{"Nizhniy-novgorod", "nizhniy-novgorod"});
        cityTable.add(new String[]{"Kazan", "kazan"});
        cityTable.add(new String[]{"Izmir", "izmir"});
        cityTable.add(new String[]{"Istanbul", "istanbul"});
        cityTable.add(new String[]{"Tbilisi", "tbilisi"});
        return cityTable;
    }
}