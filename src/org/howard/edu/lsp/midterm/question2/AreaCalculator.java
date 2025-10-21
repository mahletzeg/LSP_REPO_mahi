package org.howard.edu.lsp.midterm.question2;

public class AreaCalculator {
    public static double area(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero.");
        }
        return Math.PI * radius * radius;
    }

    public static double area(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than zero.");
        }
        return width * height;
    }

    public static double area(int base, int height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base and height must be greater than zero.");
        }
        return 0.5 * base * height;
    }

    public static double area(int side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be greater than zero.");
        }
        return side * side;
    }
}
