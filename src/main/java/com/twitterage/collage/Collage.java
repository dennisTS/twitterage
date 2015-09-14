package com.twitterage.collage;

import com.twitterage.image.ComparableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Collage {

    private List<ComparableImage> images;
    private Queue<CollageCrossing> crossings;

    BufferedImage canvas;

    public Collage(List<ComparableImage> images) {
        this.images = images;

        createCollage();
    }

    private void createCollage() {
        if (images == null || images.isEmpty())
            return;

        canvas = images.get(0).getImage();
        initCrossings();

        for (ComparableImage image : images) {
            renderImage(image);
        }
    }

    private void renderImage(ComparableImage image) {
        BufferedImage buffImage = image.getImage();
        CollageCrossing crossing = crossings.poll();

        placeImageAtCrossing(buffImage, crossing);
    }

    private void placeImageAtCrossing(BufferedImage buffImage, CollageCrossing crossing) {
        expandCanvas(buffImage, crossing);

        Graphics graphics = canvas.getGraphics();
       // graphics.
    }

    private void expandCanvas(BufferedImage buffImage, CollageCrossing crossing) {
        //THIScanvas = new BufferedImage()
    }

    private void initCrossings() {
        crossings = new LinkedList<>();

        crossings.offer(new CollageCrossing.Builder(new Point(0, 0))
                .topBranch(1)
                .rightBranch(canvas.getWidth())
                .build());

        crossings.offer(new CollageCrossing.Builder(new Point(canvas.getWidth(), 0))
                .rightBranch(1)
                .bottomBranch(canvas.getHeight())
                .build());

        crossings.offer(new CollageCrossing.Builder(new Point(canvas.getWidth(), canvas.getHeight()))
                .bottomBranch(1)
                .leftBranch(canvas.getWidth())
                .build());

        crossings.offer(new CollageCrossing.Builder(new Point(0, canvas.getHeight()))
                .leftBranch(1)
                .topBranch(canvas.getHeight())
                .build());
    }


}
