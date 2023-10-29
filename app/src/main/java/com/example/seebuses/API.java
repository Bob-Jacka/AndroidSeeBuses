package com.example.seebuses;

import static com.example.seebuses.ControlVars.CURRENT_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.CURRENT_TEXT_SIZE;
import static com.example.seebuses.ControlVars.DEFAULT_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.LAST_BLOCKS_COUNT;
import static com.example.seebuses.ControlVars.MAX_BLOCKS;
import static com.example.seebuses.pages.MainActivity.saveFile;
import static com.example.seebuses.pages.MainActivity.transportBlocks;
import static com.example.seebuses.pages.MainActivity.transports;

import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class API {

    public static void saveTransportBlocksData() {
        try {
            if (saveFile.length() != 0) {
                saveFile.delete();
                saveTransportBlocksData();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            saveControlVars(writer);

            for (BlockElement tb : transports) {
                if (tb == null) {
                    writer.write("0");
                    writer.newLine();
                } else {
                    if (!tb.getType().equals("metro")) {
                        saveTransport(writer, tb);
                    } else {
                        saveMetroSchema(writer, tb);
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveFile, Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveControlVars(BufferedWriter writer) {
        try {
            writer.write(String.valueOf(CURRENT_TEXT_SIZE));
            writer.write(' ');
            writer.write(String.valueOf(CURRENT_BLOCKS_COUNT));
            writer.write(' ');
            writer.write(String.valueOf(LAST_BLOCKS_COUNT));
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveContVars, Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveTransport(BufferedWriter writer, BlockElement tb) {
        try {
            writer.write(String.valueOf(tb.getTranspNumb()));
            writer.write(' ');
            writer.write(tb.getType());
            writer.write(' ');
            writer.write(tb.getCity());
            writer.write(' ');
            writer.write(tb.getFakeCity());
            writer.write(' ');
            writer.write(tb.getViewText());

            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveTB, Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveMetroSchema(BufferedWriter writer, BlockElement tb) {
        try {
            writer.write(tb.getCity());
            writer.write(' ');
            writer.write(tb.getType());
            writer.write(' ');
            writer.write(tb.getFakeCity());
            writer.write(' ');
            writer.write(tb.getViewText());
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrSaveMetroSchema, Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public static void loadTransportArray() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                read_ControlVars(reader);
                transports = new BlockElement[MAX_BLOCKS];
                changeBlocksCount();
                read_SaveFile(reader);
            } else {
                CURRENT_BLOCKS_COUNT = 5;
                LAST_BLOCKS_COUNT = 5;
                transports = new BlockElement[DEFAULT_BLOCKS_COUNT];
            }
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrLoadFromFile, Toast.LENGTH_SHORT).show();
        }
    }

    private static void read_ControlVars(BufferedReader reader) {
        try {
            String[] params = reader.readLine().split(" ");
            CURRENT_TEXT_SIZE = Integer.parseInt(params[0]);
            CURRENT_BLOCKS_COUNT = Integer.parseInt(params[1]);
            LAST_BLOCKS_COUNT = Integer.parseInt(params[2]);
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrReadCntrlVars, Toast.LENGTH_SHORT).show();
        }
    }

    private static void read_SaveFile(BufferedReader reader) {
        int increment = 0;
        String saveLine;
        try {
            do {
                saveLine = reader.readLine();
                if (saveLine != null && !saveLine.equals("0")) {
                    String[] splitted = saveLine.split(" ");
                    if (splitted.length == 5) {
                        transports[increment] = new BlockElement(Integer.parseInt(splitted[0]), splitted[1], splitted[2], splitted[3], splitted[4]);
                    } else
                        transports[increment] = new BlockElement(splitted[0], splitted[1], splitted[2], splitted[3]);
                } else {
                    transports[increment] = null;
                }
                increment++;
            } while (increment < CURRENT_BLOCKS_COUNT);
            reader.close();
        } catch (IOException e) {
            Toast.makeText(transportBlocks.getContext(), R.string.ErrReadTBParams, Toast.LENGTH_SHORT).show();
        }
    }

    private static void changeBlocksCount() {
        if (CURRENT_BLOCKS_COUNT > LAST_BLOCKS_COUNT) {
            addBlocks(Math.abs(DEFAULT_BLOCKS_COUNT - CURRENT_BLOCKS_COUNT));
        } else if (CURRENT_BLOCKS_COUNT < LAST_BLOCKS_COUNT) {
            removeBlocks(Math.abs(LAST_BLOCKS_COUNT - CURRENT_BLOCKS_COUNT));
        }
        saveTransportBlocksData();
    }

    private static void addBlocks(int howManyBlocksToAdd) {
        for (int i = 0; i < howManyBlocksToAdd; i++) {
            transports[CURRENT_BLOCKS_COUNT - howManyBlocksToAdd + i] = null;
            transportBlocks.getChildAt(CURRENT_BLOCKS_COUNT - howManyBlocksToAdd + i).setVisibility(View.VISIBLE);
        }
    }

    private static void removeBlocks(int howManyBlocksToRemove) {
        if (CURRENT_BLOCKS_COUNT < LAST_BLOCKS_COUNT) {
            addBlocks(Math.abs(DEFAULT_BLOCKS_COUNT - CURRENT_BLOCKS_COUNT));
        }
        for (int i = 1; i <= howManyBlocksToRemove; i++) {
            transports[CURRENT_BLOCKS_COUNT + howManyBlocksToRemove - i] = null;
            transportBlocks.getChildAt(CURRENT_BLOCKS_COUNT + howManyBlocksToRemove - i).setVisibility(View.GONE);
        }
    }
}
