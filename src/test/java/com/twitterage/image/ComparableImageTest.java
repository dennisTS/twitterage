package com.twitterage.image;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertTrue;

public class ComparableImageTest {

    private BufferedImage bigImage, smallImage;
    private ComparableImage bigComparableImage, smallComparableImage;

    @Before
    public void setUp() {
        bigImage = new BufferedImage(300, 400, BufferedImage.TYPE_INT_RGB);
        smallImage = new BufferedImage(30, 40, BufferedImage.TYPE_INT_RGB);

        bigComparableImage = new ComparableImage(bigImage);
        smallComparableImage = new ComparableImage(smallImage);
    }

    @Test
    public void shouldReturnPositiveWhenComparingBigToSmall() {
        int actResult = bigComparableImage.compareTo(smallComparableImage);

        assertTrue(actResult > 0);
    }

    @Test
    public void shouldReturnNegativeWhenComparingSmallToBig() {
        int actResult = smallComparableImage.compareTo(bigComparableImage);

        assertTrue(actResult < 0);
    }

    @Test
    public void shouldReturnZeroWhenComparingToSelf() {
        int actResult = bigComparableImage.compareTo(bigComparableImage);

        assertTrue(actResult == 0);
    }
}
