package com.example.seebuses;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

class TransportBlock implements Serializable {
    static final String transportURI = "https://igis-transport.ru/";
    int id;
    private int transpNumb;
    private String transpType;
    boolean isGoneOnWeekends;
    private String city;
    String textViewText;
    Drawable drawable;

    public TransportBlock(int transpNumb, String transpType, String city, String viewText) {
        this.transpNumb = transpNumb;
        this.transpType = transpType;
        this.city = city;
        this.textViewText = viewText;
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

    Drawable getDrawable() {
        return drawable;
    }

    void setDrawable(Drawable drawable) {
        this.drawable = drawable;
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
}