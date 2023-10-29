package com.example.seebuses.tables;

import static com.example.seebuses.ControlVars.SUPPORTED_TRANSPORT_CITIES;

import java.util.ArrayList;

public abstract class CityTable {

    private static final ArrayList<String[]> cityTable = new ArrayList<>(SUPPORTED_TRANSPORT_CITIES);

    public static ArrayList<String[]> initTable_ru() {
        cityTable.add(new String[]{"Ижевск", "izh"});
        cityTable.add(new String[]{"Пермь", "perm"});
        cityTable.add(new String[]{"Ростов", "rostov"});
        cityTable.add(new String[]{"Воронеж", "voronezh"});
        cityTable.add(new String[]{"Санкт-петербург", "spb"});
        cityTable.add(new String[]{"Екатеринбург", "ekaterinburg"});
        cityTable.add(new String[]{"Ярославль", "yaroslavl"});
        cityTable.add(new String[]{"Уфа", "ufa"});
        cityTable.add(new String[]{"Калининград", "kaliningrad"});
        cityTable.add(new String[]{"Казань", "kazan"});
        return cityTable;
    }

    public static ArrayList<String[]> initTable_en() {
        cityTable.add(new String[]{"Izhevsk", "izh"});
        cityTable.add(new String[]{"Perm", "perm"});
        cityTable.add(new String[]{"Rostov", "rostov"});
        cityTable.add(new String[]{"Voronezh", "voronezh"});
        cityTable.add(new String[]{"Saint-petersburg", "spb"});
        cityTable.add(new String[]{"Ekaterinburg", "ekaterinburg"});
        cityTable.add(new String[]{"Yaroslavl", "yaroslavl"});
        cityTable.add(new String[]{"Ufa", "ufa"});
        cityTable.add(new String[]{"Kaliningrad", "kaliningrad"});
        cityTable.add(new String[]{"Kazan", "kazan"});
        return cityTable;
    }
}