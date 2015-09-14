package com.twitterage.image;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
        } catch (IOException exception) {
            LOG.error("Could not download an image.", exception);
        }

        return image;
    }

    public static BufferedImage resizeToPercent(BufferedImage image, double percent) {
        if (image == null)
            return null;

        if (percent <= 0 || percent > 1)
            throw new IllegalArgumentException("Invalid percent value: " + percent);

        int maxDimension = Math.max(image.getWidth(), image.getHeight());
        int targetSize = (int) (maxDimension * percent);

        return Scalr.resize(image, targetSize);
    }

    public static byte[] imageToByteArray(BufferedImage image) {
        if (image == null)
            return null;

        byte[] result = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(image, "jpg", baos);
            baos.flush();
            result = baos.toByteArray();

            baos.close();
        } catch (IOException exception) {
            LOG.error("Unknown IO exception while converting image.", exception);
        }

        return result;
    }

    public static String imageToBase64String(BufferedImage image) {
        Base64 base64 = new Base64();

        return base64.encodeAsString(imageToByteArray(image));
    }
}
