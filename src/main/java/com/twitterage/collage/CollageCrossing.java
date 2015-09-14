package com.twitterage.collage;

import java.awt.*;
import java.util.EnumSet;
import static com.twitterage.collage.CollageCrossing.CrossingType;

public final class CollageCrossing {

    private Point location;

    private final int topBranch;
    private final int rightBranch;
    private final int bottomBranch;
    private final int leftBranch;

    private CrossingType type;

    private CollageCrossing (Builder builder, EnumSet<Branch> flags) {
        this.location = builder.location;

        this.topBranch = builder.topBranch;
        this.rightBranch = builder.rightBranch;
        this.bottomBranch = builder.bottomBranch;
        this.leftBranch = builder.leftBranch;

        setType(flags);
    }

    private void setType(EnumSet<Branch> flags) {
        if (flags.contains(Branch.TOP) && flags.contains(Branch.RIGHT))
            this.type = CrossingType.TOP_RIGHT;
        else if (flags.contains(Branch.BOTTOM) && flags.contains(Branch.RIGHT))
            this.type = CrossingType.BOTTOM_RIGHT;
        else if (flags.contains(Branch.BOTTOM) && flags.contains(Branch.LEFT))
            this.type = CrossingType.BOTTOM_LEFT;
        else if (flags.contains(Branch.TOP) && flags.contains(Branch.LEFT))
            this.type = CrossingType.TOP_LEFT;
    }

    public static class Builder {
        private static final String ERROR_MESSAGE = "Collage crossing must have exactly two branches.";
        private final Point location;

        private int topBranch = 0;
        private int rightBranch = 0;
        private int bottomBranch = 0;
        private int leftBranch = 0;

        private EnumSet<Branch> flags = EnumSet.noneOf(Branch.class);

        public Builder(Point location) {
            this.location = location;
        }

        public Builder topBranch(int topBranch) {
            this.topBranch = topBranch;

            flags.add(Branch.TOP);

            return this;
        }

        public Builder rightBranch(int rightBranch) {
            this.rightBranch = rightBranch;

            flags.add(Branch.RIGHT);

            return this;
        }

        public Builder bottomBranch(int bottomBranch) {
            this.bottomBranch = bottomBranch;

            flags.add(Branch.BOTTOM);

            return this;
        }

        public Builder leftBranch(int leftBranch) {
            this.leftBranch = leftBranch;

            flags.add(Branch.LEFT);

            return this;
        }

        public CollageCrossing build() {
            if (flags.size() != 2)
                throw new UnsupportedOperationException(ERROR_MESSAGE);
            return new CollageCrossing(this, flags);
        }
    }

    public static enum CrossingType {
        TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, TOP_LEFT;
    }

    private static enum Branch {
        TOP, RIGHT, BOTTOM, LEFT;
    }

    public Point getLocation() {
        return location;
    }

    public int getTopBranch() {
        return topBranch;
    }

    public int getRightBranch() {
        return rightBranch;
    }

    public int getBottomBranch() {
        return bottomBranch;
    }

    public int getLeftBranch() {
        return leftBranch;
    }
}
