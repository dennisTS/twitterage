package com.twitterage.image;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class ImageProcessor {

    private static final Logger LOG = Logger.getLogger(ImageProcessor.class);

    private ImageProcessor() {
        throw new AssertionError();
    }

    public static BufferedImage downloadImage(String url) {
        URL imageUrl;
        BufferedImage image = null;

        try {
            imageUrl = new URL(url);
            image = ImageIO.read(imageUrl);

        } catch (MalformedURLException exception) {
            LOG.error("Could not download an image with broken URL.", exception);
            return null;
        } catch (IOException exception) {
            LOG.error("Could not download an image.", exception);
            return null;
        }

        return image;
    }

    public static BufferedImage resizeToPercent(BufferedImage image, double percent) {
        if (image == null)
            return null;

        if (percent <= 0 || percent > 1)
            throw new IllegalArgumentException("Invalid percent value: " + percent);

        int maxDimension = Integer.max(image.getWidth(), image.getHeight());
        int targetSize = (int) (maxDimension * percent);

        return Scalr.resize(image, targetSize);
    }
}
