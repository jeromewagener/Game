package com.jeromewagener.game.graphics;

import com.jeromewagener.game.levels.TileCoordinate;
import com.jeromewagener.game.levels.tile.Tile;
import com.jeromewagener.game.objects.GameObject;

public class Screen {
    private int width;
    private int height;
    private int[] pixels;

    public int xOffset, yOffset;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public int[] getPixels() {
        return pixels;
    }

    public void renderTile(int xPixel, int yPixel, Tile tile) {
        renderSprite(xPixel, yPixel, tile.sprite, true);
    }

    public void renderSprite(int xPixel, int yPixel, Sprite sprite, boolean fixed) {
        if (fixed) {
            xPixel -= xOffset;
            yPixel -= yOffset;
        }

        for (int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yPixel;

            for (int x = 0; x < sprite.getWidth(); x++) {
                int xa = x + xPixel;

                if (xa < -sprite.getWidth() || xa >= getWidth() || ya < 0 || ya >= getHeight()) {
                    break;
                }

                if (xa < 0) {
                    xa = 0;
                }

                int color = sprite.pixels[x + y * sprite.getWidth()];
                if (color != 0xFFFF00FF) {
                    pixels[xa + ya * getWidth()] = color;
                }
            }
        }
    }

    public void renderStationary(GameObject gameObject) {
        int xPixel = gameObject.x * TileCoordinate.TILE_SIZE;
        int yPixel = gameObject.y * TileCoordinate.TILE_SIZE;

        xPixel -= xOffset;
        yPixel -= yOffset;

        for (int y = 0; y < gameObject.sprite.getHeight(); y++) {
            int ya = y + yPixel;

            for (int x = 0; x < gameObject.sprite.getWidth(); x++) {
                int xa = x + xPixel;

                if (xa < -gameObject.sprite.getWidth() || xa >= getWidth() || ya < 0 || ya >= getHeight()) {
                    break;
                }

                if (xa < 0) {
                    xa = 0;
                }

                int color = gameObject.sprite.pixels[x + y * gameObject.sprite.getWidth()];
                if (color != 0xFFFF00FF) {
                    pixels[xa + ya * getWidth()] = color;
                }
            }
        }
    }

    public void renderPlayer(int xPixel, int yPixel, Sprite sprite) {
        // 32 = Player size

        xPixel -= xOffset;
        yPixel -= yOffset;

        for (int y=0; y< 32; y++) {
            int ya = y + yPixel;

            for (int x=0; x< 32; x++) {
                int xa = x + xPixel;

                if (xa < -32 || xa >= getWidth() || ya < 0 || ya >= getHeight()) {
                    break;
                }

                if (xa < 0) {
                    xa = 0;
                }

                int color = sprite.pixels[x + y * 32];
                if (color != 0xFFFF00FF) {
                    pixels[xa + ya * getWidth()] = color;
                }
            }
        }
    }

    public void renderGhost(int xPixel, int yPixel, Sprite sprite) {
        // 32 = Ghost size

        xPixel -= xOffset;
        yPixel -= yOffset;

        for (int y=0; y< 32; y++) {
            int ya = y + yPixel;

            for (int x=0; x< 32; x++) {
                int xa = x + xPixel;

                if (xa < -32 || xa >= getWidth() || ya < 0 || ya >= getHeight()) {
                    break;
                }

                if (xa < 0) {
                    xa = 0;
                }

                int color = sprite.pixels[x + y * 32];
                if (color != 0xFFFF00FF) {
                    pixels[xa + ya * getWidth()] = color;
                }
            }
        }
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
