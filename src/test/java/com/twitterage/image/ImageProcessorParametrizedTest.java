package com.twitterage.image;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RunWith(JUnitParamsRunner.class)
public class ImageProcessorParametrizedTest {

    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 150;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    @Parameters({"-1", "0", "1.2"})
    public void shouldThrowExceptionWhenPassedInvalidPercent(double percent) throws IOException {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid percent value: " + percent);

        BufferedImage inputImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        Image actImage = ImageProcessor.resizeToPercent(inputImage, percent);
    }
}
