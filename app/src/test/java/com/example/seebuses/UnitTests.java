package com.example.seebuses;

import static com.example.seebuses.ControlVars.DEFAULT_BLOCKS_COUNT;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {

    private BlockElement[] transports = new BlockElement[DEFAULT_BLOCKS_COUNT];

    @Before
    public void before() {
        transports = null;
    }

    @Test
    public void addTransportToArr() {
        for (int i = 0; i < DEFAULT_BLOCKS_COUNT; i++) {
            transports[i] = new BlockElement();
        }
    }

    @Test
    public void addDiffTransportToArr() {
        for (int i = 0; i < DEFAULT_BLOCKS_COUNT; i++) {
            if (i % 2 == 0) {
                transports[i] = new BlockElement(12, "bus", "izhevsk", "izh", "Автобус 12");
            } else {
                transports[i] = new BlockElement("Moscow", "metro", "moscow", "Moscow 12");
            }
        }
        Assert.assertEquals(transports.length, DEFAULT_BLOCKS_COUNT);
    }
}