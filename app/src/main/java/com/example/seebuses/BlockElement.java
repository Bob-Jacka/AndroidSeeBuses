package com.example.seebuses;

import static com.example.seebuses.ControlVars.IS_RUSSIAN;

class BlockElement {
    private static final String transportURI_IGIS = "https://igis-transport.ru/";
    private static final String transportURI_BUSTI = "https://ru.busti.me/";
    private static final String transportURI_YandexMetro = "https://yandex.ru/metro/";
    private int transpNumb;
    private String transpType;
    private String city;
    private String fakeCity;

    //transport
    BlockElement(int transpNumb, String transpType, String city, String fakeCity) {
        this.transpNumb = transpNumb;
        this.transpType = transpType;
        this.city = city;
        this.fakeCity = fakeCity;
    }

    //Metro
    BlockElement(String city, String transpType, String schemaFakeCity) {
        this.city = city;
        this.transpType = transpType;
        this.fakeCity = schemaFakeCity;
    }

    BlockElement() {
    }

    void changeCity(String newCity) {
        this.city = newCity;
    }

    void changeTransportType(String newType) {
        this.transpType = newType;
    }

    void changeTransportNumber(int newNumber) {
        this.transpNumb = newNumber;
    }

    String getTextViewText() {
        if (IS_RUSSIAN) {
            return getTextVievText_ru();
        } else {
            return getTextVievText_en();
        }
    }

    private String getTextVievText_ru() {
        switch (transpType) {
            case "citybus":
                return "Автобус " + transpNumb;
            case "trolleybus":
                return "Троллейбус " + transpNumb;
            case "tram":
                return "Трамвай " + transpNumb;
            case "metro":
                return "Метро";
        }
        return null;
    }

    private String getTextVievText_en() {
        switch (transpType) {
            case "citybus":
                return "Bus " + transpNumb;
            case "trolleybus":
                return "Trolleybus " + transpNumb;
            case "tram":
                return "Tram " + transpNumb;
            case "metro":
                return "Metro";
        }
        return null;
    }

    int getTranspNumb() {
        return transpNumb;
    }

    String getTranspType() {
        return transpType;
    }

    String getCity() {
        return city;
    }

    String getTransportURI_IGIS() {
        return transportURI_IGIS + fakeCity + "/" + transpType + "/" + transpNumb + "?";
    }

    String getTransportURI_BUSTI() {
        return transportURI_BUSTI + fakeCity + "/#" + (transpType.equals("citybus") ? "bus" : transpType.equals("trolleybus") ? "trolleybus" : transpType.equals("tram") ? "tramway" : null) + "-" + transpNumb;
    }

    String getSchemaURI_YandexMetro() {
        return transportURI_YandexMetro + fakeCity + "/";
    }

    String getFakeCity() {
        return fakeCity;
    }

    void changeFakeCity(String fakeCity) {
        this.fakeCity = fakeCity;
    }
}