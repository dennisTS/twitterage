package com.twitterage.collage;

import com.twitterage.image.ComparableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Collage {
    int counter = 0;
    private List<ComparableImage> images;
    private Queue<CollageCrossing> crossings;

    private BufferedImage canvas;

    public Collage(List<ComparableImage> images) {
        this.images = images;

        createCollage();
    }

    private void createCollage() {
        if (images == null || images.isEmpty())
            return;

        canvas = images.get(0).getImage();
        initCrossingsForImage(canvas, new Point(0, 0));

        for (int i = 1; i < images.size(); i++) {
            renderImage(images.get(i));
        }
    }

    private void renderImage(ComparableImage image) {
        BufferedImage buffImage = image.getImage();

        if (crossings.peek() == null)
            return;

        CollageCrossing crossing = crossings.poll();

        placeImageAtCrossing(buffImage, crossing);
    }

    private void placeImageAtCrossing(BufferedImage buffImage, CollageCrossing crossing) {
        drawAtCanvas(buffImage, crossing);
    }

    private void drawAtCanvas(BufferedImage buffImage, CollageCrossing crossing) {
        int topOffset = 0;
        int bottomOffset = 0;
        int leftOffset = 0;
        int rightOffset = 0;

        int xCoord;
        int yCoord;

        if (crossing.isTop()) {
            topOffset = calculateBeginningCanvasOffset(crossing.getLocation().y, buffImage.getHeight());

            yCoord = calculateBeginningImageCoordinate(crossing.getLocation().y, buffImage.getHeight());
        } else {
            bottomOffset = calculateEndingCanvasOffset(crossing.getLocation().y, buffImage.getHeight(), canvas.getHeight());

            yCoord = crossing.getLocation().y;
        }

        if (crossing.isLeft()) {
            leftOffset = calculateBeginningCanvasOffset(crossing.getLocation().x, buffImage.getWidth());

            xCoord = calculateBeginningImageCoordinate(crossing.getLocation().x, buffImage.getWidth());
        } else {
            rightOffset = calculateEndingCanvasOffset(crossing.getLocation().x, buffImage.getWidth(), canvas.getWidth());

            xCoord = crossing.getLocation().x;
        }

        Graphics graphics = extendCanvas(topOffset, bottomOffset, leftOffset, rightOffset);
        graphics.drawImage(buffImage, xCoord, yCoord, null);

//        try {
//            ImageIO.write(canvas, "PNG", new File("collage" + counter++ + ".png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        resetCrossings(leftOffset, topOffset);

        initCrossings(buffImage, crossing);
    }

    private void initCrossings(BufferedImage buffImage, CollageCrossing crossing) {
        Point oldLocation = crossing.getLocation();

        if (crossing.getType().equals(CollageCrossing.CrossingType.TOP_RIGHT)) {
            if (buffImage.getWidth() > crossing.getRightBranch()) {
                Point newLocation = new Point(oldLocation.x + buffImage.getWidth() - crossing.getRightBranch(), oldLocation.y);

                crossings.offer(new CollageCrossing.Builder(newLocation)
                                .rightBranch(buffImage.getWidth() - crossing.getRightBranch())
                                .bottomBranch(1)
                                .build());
            } else {
                Point newLocation = new Point(oldLocation.x + buffImage.getWidth(), oldLocation.y);

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .rightBranch(crossing.getRightBranch() - buffImage.getWidth())
                        .topBranch(buffImage.getHeight())
                        .build());
            }

            if (buffImage.getHeight() > crossing.getTopBranch()) {
                Point newLocation = new Point(oldLocation.x, oldLocation.y + buffImage.getHeight() - crossing.getTopBranch());

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .topBranch(buffImage.getHeight() - crossing.getTopBranch())
                        .leftBranch(1)
                        .build());
            } else {
                Point newLocation = new Point(oldLocation.x, oldLocation.y - buffImage.getHeight());

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .rightBranch(buffImage.getWidth())
                        .topBranch(crossing.getTopBranch() - buffImage.getHeight())
                        .build());
            }
        }

        if (crossing.getType().equals(CollageCrossing.CrossingType.BOTTOM_RIGHT)) {
            if (buffImage.getWidth() > crossing.getRightBranch()) {
                Point newLocation = new Point(oldLocation.x + buffImage.getWidth() - crossing.getRightBranch(), oldLocation.y);

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .rightBranch(buffImage.getWidth() - crossing.getRightBranch())
                        .topBranch(1)
                        .build());
            } else {
                Point newLocation = new Point(oldLocation.x + buffImage.getWidth(), oldLocation.y);

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .rightBranch(crossing.getRightBranch() - buffImage.getWidth())
                        .topBranch(buffImage.getHeight())
                        .build());
            }

            if (buffImage.getHeight() > crossing.getBottomBranch()) {
                Point newLocation = new Point(oldLocation.x, oldLocation.y + crossing.getTopBranch());

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .bottomBranch(buffImage.getHeight() - crossing.getBottomBranch())
                        .leftBranch(1)
                        .build());
            } else {
                Point newLocation = new Point(oldLocation.x, oldLocation.y + crossing.getTopBranch() - buffImage.getHeight());

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .rightBranch(buffImage.getWidth())
                        .topBranch(crossing.getBottomBranch() - buffImage.getHeight())
                        .build());
            }
        }

        if (crossing.getType().equals(CollageCrossing.CrossingType.TOP_LEFT)) {
            if (buffImage.getWidth() > crossing.getLeftBranch()) {
                Point newLocation = new Point(oldLocation.x - crossing.getLeftBranch(), oldLocation.y);

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .leftBranch(buffImage.getWidth() - crossing.getLeftBranch())
                        .bottomBranch(1)
                        .build());
            } else {
                Point newLocation = new Point(oldLocation.x - buffImage.getWidth(), oldLocation.y);

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .leftBranch(crossing.getLeftBranch() - buffImage.getWidth())
                        .topBranch(buffImage.getHeight())
                        .build());
            }

            if (buffImage.getHeight() > crossing.getTopBranch()) {
                Point newLocation = new Point(oldLocation.x, oldLocation.y - crossing.getTopBranch());

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .topBranch(buffImage.getHeight() - crossing.getTopBranch())
                        .rightBranch(1)
                        .build());
            } else {
                Point newLocation = new Point(oldLocation.x, oldLocation.y - buffImage.getHeight());

                crossings.offer(new CollageCrossing.Builder(newLocation)
                        .leftBranch(buffImage.getWidth())
                        .topBranch(crossing.getTopBranch() - buffImage.getHeight())
                        .build());
            }
        }
    }

    private Graphics extendCanvas(int topOffset, int bottomOffset, int leftOffset, int rightOffset) {
        int width = leftOffset + rightOffset + canvas.getWidth();
        int height = topOffset + bottomOffset + canvas.getHeight();
        BufferedImage newCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = newCanvas.getGraphics();
        graphics.drawImage(canvas, leftOffset, topOffset, canvas.getWidth(), canvas.getHeight(), null);

        canvas = newCanvas;

        return graphics;
    }


    private int calculateBeginningCanvasOffset(int crossingCoordinate, int dimension) {
        return Math.max(0, dimension - crossingCoordinate);
    }

    private int calculateEndingCanvasOffset(int crossingCoordinate, int dimension, int canvasDimension) {
        int distanceBetweenCanvasBorderAndCrossing = canvasDimension - crossingCoordinate;

        return Math.max(0, dimension - distanceBetweenCanvasBorderAndCrossing);
    }

    private int calculateBeginningImageCoordinate(int crossingCoordinate, int dimension) {
        return Math.max(0, crossingCoordinate - dimension);
    }

    private void resetCrossings(int leftOffset, int topOffset) {
        for (CollageCrossing crossing : crossings) {
            Point oldLocation = crossing.getLocation();

            crossing.setLocation(new Point(oldLocation.x + leftOffset, oldLocation.y + topOffset));
        }
    }

    private void initCrossingsForImage(BufferedImage image, Point location) {
        int x = location.x;
        int y = location.y;

        crossings = new LinkedList<>();

        crossings.offer(new CollageCrossing.Builder(location)
                .topBranch(1)
                .rightBranch(image.getWidth())
                .build());

        crossings.offer(new CollageCrossing.Builder(new Point(x + image.getWidth(), y))
                .rightBranch(1)
                .bottomBranch(image.getHeight())
                .build());

        crossings.offer(new CollageCrossing.Builder(new Point(x + image.getWidth(), y + image.getHeight()))
                .bottomBranch(1)
                .leftBranch(image.getWidth())
                .build());

        crossings.offer(new CollageCrossing.Builder(new Point(x, y + image.getHeight()))
                .leftBranch(1)
                .topBranch(image.getHeight())
                .build());
    }

    public BufferedImage getCollageAsImage() {
        return canvas;
    }
}
