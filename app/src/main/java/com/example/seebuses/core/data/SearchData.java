package com.example.seebuses.core.data;

import com.example.seebuses.core.entities.BlockElement;
import com.example.seebuses.core.entities.WheelTransport;

public abstract class SearchData {

    private static final String transportURI_IGIS = "https://igis-transport.ru/";
    private static final String transportURI_BUSTI = "https://ru.busti.me/";
    private static final String transportURI_YandexMetro = "https://yandex.ru/metro/";
    private static final String transportURI_KUDIKINO = "https://kudikina.ru/izh/";

    public static String getTransportURI_IGIS(WheelTransport elem) {
        return transportURI_IGIS + elem.getFakeCity() + "/" + elem.getType() + "/" + elem.getTranspNumb() + "?";
    }

    public static String getTransportURI_BUSTI(WheelTransport elem) {
        final TransportType type = elem.getType();
        final String typeText = switch (type) {
            case citybus:
                yield "bus";
            case trolleybus:
                yield "trolleybus";
            case tram:
                yield "tramway";
            case metro:
                throw new RuntimeException();
        };
        return transportURI_BUSTI + elem.getFakeCity() + "/#" + typeText + "-" + elem.getTranspNumb();
    }

    public static String getSchemaURI_YandexMetro(BlockElement elem) {
        return transportURI_YandexMetro + elem.getFakeCity() + "/";
    }

    public static String getTransportURI_KUDIKINO(WheelTransport elem) {
        final String type = String.valueOf(elem.getType()).substring(4);
        return transportURI_KUDIKINO + type + "/" + elem.getTranspNumb() + "/online";
    }
}
