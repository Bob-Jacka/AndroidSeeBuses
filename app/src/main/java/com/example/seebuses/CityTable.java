package com.example.seebuses;

import static com.example.seebuses.Consts.SUPPORTED_CITIES;

import java.util.ArrayList;

abstract class CityTable {

    private static final ArrayList<String[]> cityTable = new ArrayList<>(SUPPORTED_CITIES);

    static ArrayList<String[]> initTable_ru() {
        cityTable.add(new String[]{"Ижевск", "izh"});
        cityTable.add(new String[]{"Пермь", "perm"});
        return cityTable;
    }

    static ArrayList<String[]> initTable_en() {
        cityTable.add(new String[]{"Izhevsk", "izh"});
        cityTable.add(new String[]{"Perm", "perm"});
        return cityTable;
    }
}