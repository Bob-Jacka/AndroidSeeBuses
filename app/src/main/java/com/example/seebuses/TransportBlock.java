package com.example.seebuses;

import android.graphics.drawable.Drawable;

class TransportBlock {
    static final String transportURI = "https://igis-transport.ru/";
    int transpNumb;
    String transpType;
    boolean isGoneOnWeekends;
    String city;
    final String busDrawableName = "bus.png";
    final String tramDrawableName = "tram.png";
    final String trollDrawableName = "troll.png";
    int drawable = R.drawable.empty_block;

    public TransportBlock(int transpNumb, String transpType, boolean isGoneOnWeekends, String city) {
        this.transpNumb = transpNumb;
        this.transpType = transpType;
        this.city = city;
        this.isGoneOnWeekends = isGoneOnWeekends;
    }

    public TransportBlock(int transpNumb, String transpType, String city) {
        this.transpNumb = transpNumb;
        this.transpType = transpType;
        this.city = city;
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

    String getTransportName() {
        return transpType + " " + transpNumb;
    }

    int getDrawable() {
        return drawable;
    }
}