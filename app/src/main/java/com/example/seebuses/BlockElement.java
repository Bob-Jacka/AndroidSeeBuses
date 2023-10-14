package com.example.seebuses;

class BlockElement {
    private static final String transportURI_IGIS = "https://igis-transport.ru/";
    private static final String transportURI_BUSTI = "https://ru.busti.me/";
    private static final String transportURI_YandexMetro = "https://yandex.ru/metro/";
    private int transpNumb;
    private String typeForSearch;
    private String city;
    private String fakeCity;
    private String viewText;

    //Transport
    BlockElement(int transpNumb, String transpType, String city, String fakeCity, String viewText) {
        this.transpNumb = transpNumb;
        this.typeForSearch = transpType;
        this.city = city;
        this.fakeCity = fakeCity;
        this.viewText = viewText;
    }

    //Metro
    BlockElement(String city, String transpType, String schemaFakeCity, String viewText) {
        this.city = city;
        this.typeForSearch = transpType;
        this.fakeCity = schemaFakeCity;
        this.viewText = viewText;
    }

    BlockElement() {
    }

    String getViewText() {
        return viewText;
    }

    int getTranspNumb() {
        return transpNumb;
    }

    String getTypeForSearch() {
        return typeForSearch;
    }

    String getCity() {
        return city;
    }

    String getTransportURI_IGIS() {
        return transportURI_IGIS + fakeCity + "/" + typeForSearch + "/" + transpNumb + "?";
    }

    String getTransportURI_BUSTI() {
        return transportURI_BUSTI + fakeCity + "/#" + (typeForSearch.equals("citybus") ? "bus" : typeForSearch.equals("trolleybus") ? "trolleybus" : typeForSearch.equals("tram") ? "tramway" : null) + "-" + transpNumb;
    }

    String getSchemaURI_YandexMetro() {
        return transportURI_YandexMetro + fakeCity + "/";
    }

    String getFakeCity() {
        return fakeCity;
    }
}