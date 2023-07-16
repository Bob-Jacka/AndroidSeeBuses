package com.example.seebuses;

class TransportBlock {
    private static final String transportURI_IGIS = "https://igis-transport.ru/";
    private static final String transportURI_BUSTI = "https://ru.busti.me/";
    private int transpNumb;
    private String transpType;
    private String city;
    private String fakeCity;
    String textViewText;

    public TransportBlock(int transpNumb, String transpType, String city, String viewText, String fakeCity) {
        this.transpNumb = transpNumb;
        this.transpType = transpType;
        this.city = city;
        this.textViewText = viewText;
        this.fakeCity = fakeCity;
    }

    TransportBlock() {
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

    private String getTransportName() {
        switch (transpType) {
            case "citybus":
                return "Автобус " + transpNumb;
            case "trolleybus":
                return "Троллейбус " + transpNumb;
            case "tram":
                return "Трамвай " + transpNumb;
        }
        return null;
    }

    String getTextViewText() {
        return getTransportName();
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

    void setTextViewText(String textViewText) {
        this.textViewText = textViewText;
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
}