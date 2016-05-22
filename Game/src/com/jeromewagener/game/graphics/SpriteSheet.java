package com.jeromewagener.game.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    final int SIZE;
    private String path;
    private int[] pixels;

    public static SpriteSheet tiles = new SpriteSheet("/textures/sprite-sheet.png", 512);

    SpriteSheet(String path, int size) {
        this.path = path;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));

            int width = image.getWidth();
            int height = image.getHeight();
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getPixels() {
        return pixels;
    }
}
