package com.jeromewagener.game.levels.tile;

import com.jeromewagener.game.graphics.Sprite;

public class TileConstants {
    public static final Tile GRASS = new GrassTile(Sprite.GRASS);
    public static final Tile GRASS_2 = new GrassTile(Sprite.GRASS_2);
    public static final Tile GRASS_3 = new GrassTile(Sprite.GRASS_3);
    public static final Tile FLOWER = new GrassTile(Sprite.FLOWER);
    public static final Tile STONE = new StoneTile(Sprite.STONE);
    public static final Tile BRICK_STONES = new StoneTile(Sprite.BRICK_STONES);
    public static final Tile WOOD_FLOOR = new FloorTile(Sprite.WOOD_FLOOR);
    public static final Tile SAIL = new SailTile(Sprite.SAIL);
    public static final Tile WATER = new WaterTile(Sprite.WATER);
    public static final Tile VOID_TILE = new VoidTile(Sprite.VOID_SPRITE);
}
