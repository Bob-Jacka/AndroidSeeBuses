package com.example.seebuses.core.entities;

import com.example.seebuses.core.data.TransportType;

public class Metro extends BlockElement {

    public Metro() {

    }

    public Metro(String city, String schemaFakeCity, String viewText) {
        this.city = city;
        this.type = TransportType.valueOf("metro");
        this.fakeCity = schemaFakeCity;
        this.viewText = viewText;
    }
}
