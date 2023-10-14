package com.example.seebuses;

import java.util.Locale;

abstract class ControlVars {

    ////////////////////App
    static int CURRENT_TEXT_SIZE = 16;
    static boolean IS_RUSSIAN = Locale.getDefault().getDisplayLanguage().equals("русский");

    ////////////////////Main_Activity
    static final int DEFAULT_BLOCKS_COUNT = 5;
    static int CURRENT_BLOCKS_COUNT = 5;
    static int LAST_BLOCKS_COUNT = 5;
    static final int ELEMENTS_IN_BLOCK = 2;
    static final int SUPPORTED_TRANSPORT_CITIES = 10;
    static final int SUPPORTED_METRO_CITIES = 10;
    static final String SAVE_FILE_NAME = "/saveBlocks";

    /////////////////////Settings_Activity
    static final int MIN_BLOCKS = 3;
    static final int MAX_BLOCKS = 8;
}
