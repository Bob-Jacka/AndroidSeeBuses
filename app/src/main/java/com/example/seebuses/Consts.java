package com.example.seebuses;

import java.util.Locale;

abstract class Consts {

    ////////////////////App
    static String CURRENT_LANGUAGE = Locale.getDefault().getDisplayLanguage();
    static int CURRENT_TEXT_SIZE;

    ////////////////////Main_Activity
    static final int DEFAULT_BLOCKS_COUNT = 5;
    static int CURRENT_BLOCKS_COUNT;
    static int LAST_BLOCKS_COUNT;
    static final int ELEMENTS_IN_BLOCK = 2;
    static final int SUPPORTED_CITIES = 2;
    static final int SUPPORTED_METRO_CITIES = 10;
    static final String SAVE_FILE_NAME = "/saveBlocks";

    /////////////////////Settings_Activity
    static final int MIN_BLOCKS = 1;
    static final int MAX_BLOCKS = 8;
}
