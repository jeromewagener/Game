package com.jeromewagener.game.levels;

public class TileCoordinate {
    public static final int TILE_SIZE = 16;
    private int x, y;

    public TileCoordinate(int x, int y) {
        this.x = x * TILE_SIZE;
        this.y = y * TILE_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
