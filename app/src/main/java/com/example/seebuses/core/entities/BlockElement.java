package com.example.seebuses.core.entities;

import com.example.seebuses.core.data.TransportType;

public abstract class BlockElement {

    protected String city, fakeCity, viewText;
    protected TransportType type;

    public String getViewText() {
        return viewText;
    }

    public TransportType getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getFakeCity() {
        return fakeCity;
    }
}