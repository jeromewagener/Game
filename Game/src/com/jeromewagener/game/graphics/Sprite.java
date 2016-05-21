package com.jeromewagener.game.graphics;

public class Sprite {
    private int width;
    private int height;
    private int x, y;
    public int[] pixels;
    private SpriteSheet spriteSheet;

    public static final Sprite GRASS = new Sprite(16, 16, 0, 0, SpriteSheet.tiles);
    public static final Sprite GRASS_2 = new Sprite(16, 16, 1, 0, SpriteSheet.tiles);
    public static final Sprite GRASS_3 = new Sprite(16, 16, 2, 0, SpriteSheet.tiles);
    public static final Sprite FLOWER = new Sprite(16, 16, 0, 1, SpriteSheet.tiles);
    public static final Sprite STONE = new Sprite(16, 16, 1, 1, SpriteSheet.tiles);
    public static final Sprite WOOD_FLOOR = new Sprite(16, 16, 1, 3, SpriteSheet.tiles);
    public static final Sprite WATER = new Sprite(16, 16, 3, 0, SpriteSheet.tiles);
    public static final Sprite BRICK_STONES = new Sprite(16, 16, 0, 3, SpriteSheet.tiles);
    public static final Sprite SAIL = new Sprite(16, 16, 2, 3, SpriteSheet.tiles);
    public static final Sprite PROJECTILE = new Sprite(16, 16, 0, 2, SpriteSheet.tiles);
    public static final Sprite GHOST_PROJECTILE = new Sprite(16, 16, 1, 2, SpriteSheet.tiles);
    public static final Sprite PARTICLE = new Sprite(2, 2, 0xe78302);
    public static final Sprite BLOOD_PARTICLE = new Sprite(3, 3, 0xff0000);
    public static final Sprite TREE = new Sprite(32, 48, 0, 17, SpriteSheet.tiles);
    public static final Sprite VOID_SPRIE = new Sprite(16, 16, 0x000000);

    public Sprite(int width, int height, int x, int y, SpriteSheet spriteSheet) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.x = x * 16;
        this.y = y * 16;
        this.spriteSheet = spriteSheet;

        load();
    }

    public Sprite(int width, int height, int color) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColor(color);
    }

    private void setColor(int color) {
        for (int i=0; i<width*height; i++) {
            pixels[i] = color;
        }
    }

    private void load() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                pixels[x+y*width] = spriteSheet.pixels[(this.x + x) + (this.y + y) * spriteSheet.SIZE];
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
