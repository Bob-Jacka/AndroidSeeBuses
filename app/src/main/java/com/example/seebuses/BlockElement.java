package com.example.seebuses;

public class BlockElement {
    private static final String transportURI_IGIS = "https://igis-transport.ru/";
    private static final String transportURI_BUSTI = "https://ru.busti.me/";
    private static final String transportURI_YandexMetro = "https://yandex.ru/metro/";
    private int transpNumb;
    private String type, city, fakeCity, viewText;

    //Transport
    public BlockElement(int transpNumb, String transpType, String city, String fakeCity, String viewText) {
        this.transpNumb = transpNumb;
        this.type = transpType;
        this.city = city;
        this.fakeCity = fakeCity;
        this.viewText = viewText;
    }

    //Metro
    public BlockElement(String city, String transpType, String schemaFakeCity, String viewText) {
        this.city = city;
        this.type = transpType;
        this.fakeCity = schemaFakeCity;
        this.viewText = viewText;
    }

    BlockElement() {
    }

    public String getViewText() {
        return viewText;
    }

    public int getTranspNumb() {
        return transpNumb;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getTransportURI_IGIS() {
        return transportURI_IGIS + fakeCity + "/" + type + "/" + transpNumb + "?";
    }

    public String getTransportURI_BUSTI() {
        return transportURI_BUSTI + fakeCity + "/#" + (type.equals("citybus") ? "bus" : type.equals("trolleybus") ? "trolleybus" : type.equals("tram") ? "tramway" : null) + "-" + transpNumb;
    }

    public String getSchemaURI_YandexMetro() {
        return transportURI_YandexMetro + fakeCity + "/";
    }

    public String getFakeCity() {
        return fakeCity;
    }
}