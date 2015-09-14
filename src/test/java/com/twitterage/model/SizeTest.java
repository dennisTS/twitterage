package com.twitterage.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class SizeTest {

    private Size size;

    @Before
    public void setUp() {
        size = new Size(234, 111);
    }

    @Test
    public void shouldSetDimensionsZeroWhenPassedNullString() {
        size = new Size(null);

        assertEquals(0, size.getX());
        assertEquals(0, size.getY());
    }

    @Test
    public void shouldSetDimensionsZeroWhenPassedEmptyString() {
        size = new Size("");

        assertEquals(0, size.getX());
        assertEquals(0, size.getY());
    }

    @Test
    @Parameters({"-1x78", "x", "xx", "xxx", "A7x87", "342x3B4", "0x0", "34x-87", "23xx23", "412xx", "300", "23x53x523", "2.1x4", "34x78.2"})
    public void shouldSetDimensionsZeroWhenPassedInvalidString(String sizeString) {
        size = new Size(sizeString);

        assertEquals(0, size.getX());
        assertEquals(0, size.getY());
    }

    @Test
    @Parameters({"23x78", "1x1", "789X987"})
    public void shouldSetDimensionsProperlyWhenPassedValidString(String sizeString) {
        size = new Size(sizeString);

        assertEquals(0, size.getX());
        assertEquals(0, size.getY());
    }
}
