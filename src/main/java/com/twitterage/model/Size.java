package com.twitterage.model;

public class Size {
    private int x;
    private int y;

    public Size(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Size(String size) {
        if (size == null || size.isEmpty()) {
            invalidateSize();

            return;
        }

        parseSize(size);
    }

    private void invalidateSize() {
        this.x = 0;
        this.y = 0;
    }

    private void parseSize(String size) {
        String[] tokens = size.split("xX");

        if (tokens.length != 2) {
            invalidateSize();

            return;
        }

        try {
            setX(Integer.parseInt(tokens[0]));
            setY(Integer.parseInt(tokens[1]));
        } catch (NumberFormatException exception) {
            invalidateSize();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Size size = (Size) o;

        return getX() == size.getX() && getY() == size.getY();

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }
}
