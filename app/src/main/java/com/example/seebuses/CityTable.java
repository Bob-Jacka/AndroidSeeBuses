package com.example.seebuses;

import java.util.HashMap;

class CityTable {

    private static HashMap<String, String> cityTable = new HashMap<>(10);

    HashMap<String, String> initTable() {
        cityTable.put("Ижевск", "izh");
        cityTable.put("Перьм", "izh");
        return cityTable;
    }
}
