package com.example.seebuses;

import static com.example.seebuses.Consts.SUPPORTED_CITIES;

import java.util.HashMap;

class CityTable {

    private static final HashMap<String, String> cityTable = new HashMap<>(SUPPORTED_CITIES);

    static HashMap<String, String> initTable() {
        cityTable.put("Ижевск", "izh");
        cityTable.put("Пермь", "perm");
        return cityTable;
    }
}
