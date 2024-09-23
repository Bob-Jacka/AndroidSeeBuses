package com.example.seebuses.core.entities;

import com.example.seebuses.core.data.TransportType;

public class WheelTransport extends BlockElement {

    private int transpNumb;

    public WheelTransport() {
    }

    public WheelTransport(String city, String fakeCity, String transpType, String viewText, int transpNumb) {
        this.transpNumb = transpNumb;
        this.type = TransportType.valueOf(transpType);
        this.city = city;
        this.fakeCity = fakeCity;
        this.viewText = viewText;
    }

    public int getTranspNumb() {
        return transpNumb;
    }
}
