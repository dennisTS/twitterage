package com.twitterage.image;

import org.imgscalr.Scalr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImageIO.class, Scalr.class})
public class ImageProcessorTest {

    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 150;

    private static final int TARGET_DOWNSIZE = 75;
    private static final double HALF_SIZE_PERCENT = 0.5D;
    private static final double SAME_SIZE_PERCENT = 1D;

    private static String INVALID_IMAGE_URL = "definitely/no/image/here.jpg";
    private static String VALID_IMAGE_URL = "http://lolwhut.com/assets/so/valid/image/here.jpg";

    private BufferedImage testImage;

    @Before
    public void setUp() {
        testImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void shouldReturnNullWhenPassedInvalidURLOnDownload() {
        Image image = ImageProcessor.downloadImage(INVALID_IMAGE_URL);

        assertEquals(null, image);
    }

    @Test
    public void shouldReturnValidImageOnDownload() throws IOException {
        PowerMockito.mockStatic(ImageIO.class);
        Image expImage = testImage;
        when(ImageIO.read(new URL(VALID_IMAGE_URL))).thenReturn((BufferedImage) expImage);

        Image actImage = ImageProcessor.downloadImage(VALID_IMAGE_URL);

        assertEquals(expImage, actImage);
    }

    @Test
    public void shouldReturnNullWhenNoImageFoundOnDownload() throws IOException {
        PowerMockito.mockStatic(ImageIO.class);
        when(ImageIO.read(new URL(VALID_IMAGE_URL))).thenThrow(IOException.class);

        Image actImage = ImageProcessor.downloadImage(VALID_IMAGE_URL);

        assertEquals(null, actImage);
    }

    @Test
    public void shouldReturnNullWhenPassedNullOrInvalidImageOnResize() throws IOException {
        BufferedImage inputImage = null;

        Image actImage = ImageProcessor.resizeToPercent(inputImage, 0);

        assertEquals(null, actImage);
    }

    @Test
    public void shouldReturnProperlyResizedImageOnResize() throws IOException {
        PowerMockito.mockStatic(Scalr.class);
        BufferedImage inputImage = testImage;

        ImageProcessor.resizeToPercent(inputImage, HALF_SIZE_PERCENT);

        verifyStatic();
        Scalr.resize(inputImage, TARGET_DOWNSIZE);
    }

    @Test
    public void shouldReturnProperlyResizedImageOnResizeWithHundredPercent() throws IOException {
        PowerMockito.mockStatic(Scalr.class);
        BufferedImage inputImage = testImage;

        ImageProcessor.resizeToPercent(inputImage, SAME_SIZE_PERCENT);

        verifyStatic();
        Scalr.resize(inputImage, IMAGE_WIDTH);
    }
}
