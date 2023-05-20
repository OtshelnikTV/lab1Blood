package com.example.lab4.Baza;

import java.util.List;
import java.util.Random;

public enum Direction {
    Right(1, 0), Left(-1, 0), Up(0, -1), Down(0, 1);
    private final int xBias;
    private final int yBias;

    Direction(int xBias, int yBias) {
        this.xBias = xBias;
        this.yBias = yBias;
    }

    public int getXBias() {
        return xBias;
    }

    public int getYBias() {
        return yBias;
    }

    public static Direction getRandomDirection() {
        return List.of(values()).get(new Random().nextInt(values().length));
    }
}
