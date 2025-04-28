package org.example.domain;

import java.util.Objects;

/**
 * Class representing coordinates domain object
 */
public class Coordinates {
    private double x;
    private Float y;

    public Coordinates(double x, Float y) {
        this.x = x;
        this.y = y;
        validateFields();
    }

    public static Coordinates createDefault() {
        return new Coordinates(0.0, 0.0f);
    }

    public double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(Float y) {
        if (y == null) {
            throw new IllegalArgumentException("Coordinates: y-coordinate can't be null");
        }
        if (y <= -615) {
            throw new IllegalArgumentException("Coordinate: y-coordinate must be gt -615");
        }
        this.y = y;
    }

    public void validateFields() {
        if (y == null) {
            throw new IllegalArgumentException("Coordinates: y-coordinate can't be null");
        }
        if (y <= -615) {
            throw new IllegalArgumentException("Coordinates: y-coordinate must be gt -615");
        }
    }

    public boolean isValid() {
        try {
            validateFields();
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates coordinates)) return false;
        return Double.compare(coordinates.x, x) == 0 && y.equals(coordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}