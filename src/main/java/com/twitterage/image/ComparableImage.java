package com.twitterage.image;

import java.awt.image.BufferedImage;

public class ComparableImage implements Comparable<ComparableImage> {

    private BufferedImage image;
    private int resolution;

    public ComparableImage() {

    }

    public ComparableImage(BufferedImage image) {
        this.image = image;

        if (image != null)
            this.resolution = image.getHeight() * image.getWidth();
        else
            this.resolution = 0;
    }

    @Override
    public int compareTo(ComparableImage o) {
        return Integer.compare(this.resolution, o.resolution);
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
