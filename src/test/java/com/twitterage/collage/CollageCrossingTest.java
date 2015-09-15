package com.twitterage.collage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class CollageCrossingTest {

    private static final String ERROR_MESSAGE = "Collage crossing must have exactly two branches.";

    private static final Point POINT = new Point(3, 3);
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int BOTTOM = 4;


    @Rule
    public ExpectedException exception = ExpectedException.none();

    private CollageCrossing underTest;

    @Test
    public void shouldThrowExceptionWhenSettingMoreThanTwoBranches() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage(ERROR_MESSAGE);

        underTest = new CollageCrossing.Builder(POINT).bottomBranch(1).leftBranch(2).rightBranch(3).build();
    }

    @Test
    public void shouldThrowExceptionWhenSettingLessThanTwoBranches() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage(ERROR_MESSAGE);

        underTest = new CollageCrossing.Builder(POINT).bottomBranch(BOTTOM).build();
    }

    @Test
    public void shouldThrowExceptionWhenSettingNoBranches() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage(ERROR_MESSAGE);

        underTest = new CollageCrossing.Builder(POINT).build();
    }

    @Test
    public void shouldProperlySetPoint() {
        underTest = new CollageCrossing.Builder(POINT).topBranch(TOP).bottomBranch(BOTTOM).build();

        assertEquals(POINT, underTest.getLocation());
    }

    @Test
    public void shouldProperlySetTopAndRightBranches() {
        underTest = new CollageCrossing.Builder(POINT).topBranch(TOP).rightBranch(RIGHT).build();

        assertEquals(TOP, underTest.getTopBranch());
        assertEquals(RIGHT, underTest.getRightBranch());
    }

    @Test
    public void shouldProperlySetLeftAndBottomBranches() {
        underTest = new CollageCrossing.Builder(POINT).leftBranch(LEFT).bottomBranch(BOTTOM).build();

        assertEquals(LEFT, underTest.getLeftBranch());
        assertEquals(BOTTOM, underTest.getBottomBranch());
    }
}
