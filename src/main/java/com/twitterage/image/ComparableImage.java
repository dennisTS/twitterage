package com.twitterage.image;

import java.awt.image.BufferedImage;

public class ComparableImage implements Comparable<ComparableImage> {

    private BufferedImage image;
    private int value;

    public ComparableImage() {

    }

    public ComparableImage(BufferedImage image, int value) {
        this.image = image;
        this.value = value;
    }

    @Override
    public int compareTo(ComparableImage o) {
        return Integer.compare(this.value, o.value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
