package com.twitterage.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComparableImage implements Comparable<ComparableImage> {

    private Image image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}