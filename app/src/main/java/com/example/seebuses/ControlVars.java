package com.example.seebuses;

import android.content.res.Resources;

import java.util.Locale;

public abstract class ControlVars {

    ////////////////////App
    public static int CURRENT_TEXT_SIZE = 16;
    public static boolean IS_RUSSIAN = Locale.getDefault().getDisplayLanguage().equals("русский");
    public static final int DEFAULT_BLOCKS_COUNT = 5;
    public static final int SUPPORTED_TRANSPORT_CITIES = 10;
    public static final int SUPPORTED_METRO_CITIES = 10;

    ////////////////////Main_Activity
    public static int CURRENT_BLOCKS_COUNT;
    public static int LAST_BLOCKS_COUNT;
    public static final int ELEMENTS_IN_BLOCK = 2;
    public static final String SAVE_FILE_NAME = "/saveBlocks";

    /////////////////////Settings_Activity
    public static final int MIN_BLOCKS = 3;
    private static final int extraBlocks = Math.round(((Resources.getSystem().getConfiguration().screenHeightDp) - 60 - (DEFAULT_BLOCKS_COUNT * 82) - (DEFAULT_BLOCKS_COUNT * 10)) / 82);
    // 60 - высота титульного блока, 82 - высота одного блока
    public static final int MAX_BLOCKS = DEFAULT_BLOCKS_COUNT + (extraBlocks <= 5 ? extraBlocks : 5);
    public static final int MAX_FONT_SIZE = 20;
    public static final int MIN_FONT_SIZE = 12;
}
