package com.example.seebuses;

class BlockElement {
    private static final String transportURI_IGIS = "https://igis-transport.ru/";
    private static final String transportURI_BUSTI = "https://ru.busti.me/";
    private static final String transportURI_MetroWay = "https://metro-way.ru/";
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

    void changeTransportType(String newTransport) {
        this.transpType = newTransport;
    }

    void changeTransportNumber(int newTransportNumber) {
        this.transpNumb = newTransportNumber;
    }

    String getTextViewText() {
        if (MainActivity.language.equals("Russian")) {
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
        return transportURI_IGIS + fakeCity + "/" + getTranspType() + "/" + getTranspNumb() + "?";
    }

    String getTransportURI_BUSTI() {
        return transportURI_BUSTI + fakeCity + "/#" + getTranspType().substring(0, 3) + "-" + getTranspNumb();
    }

    String getFakeCity() {
        return fakeCity;
    }

    void changeFakeCity(String fakeCity) {
        this.fakeCity = fakeCity;
    }

    String getSchemaURI() {
        return transportURI_MetroWay + fakeCity + "/";
    }
}