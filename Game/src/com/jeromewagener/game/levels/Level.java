package com.jeromewagener.game.levels;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.input.Keyboard;
import com.jeromewagener.game.levels.tile.Tile;
import com.jeromewagener.game.levels.tile.TileColorCodes;
import com.jeromewagener.game.levels.tile.TileConstants;
import com.jeromewagener.game.objects.GameObject;
import com.jeromewagener.game.objects.mobile.AbstractMob;
import com.jeromewagener.game.objects.mobile.Player;
import com.jeromewagener.game.objects.particle.Particle;
import com.jeromewagener.game.objects.projectile.Projectile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    private int width, height;
    private int[] tiles;

    private Player player;
    private List<AbstractMob> enemies = new ArrayList<>();
    private List<GameObject> stationaryObjects = new ArrayList<>();
    private List<Projectile> projectileList = new ArrayList<>();
    private List<Particle> particleList = new ArrayList<>();

    Level(String path) {
        loadLevel(path);
    }

    private void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void update() {
        Keyboard.getInstance().update();

        projectileList.forEach(Projectile::update);
        particleList.forEach(Particle::update);

        if (player.isRemoved()) {
            player.gameOverUpdate();
        } else {
            player.update();
        }

        enemies.stream().filter(enemy -> !enemy.isRemoved()).forEach(com.jeromewagener.game.objects.mobile.AbstractMob::update);
    }

    public void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffset(xScroll, yScroll);

        int x0 = xScroll >> 4; // same as div 16, which is the size of the tiles
        int x1 = (xScroll + screen.getWidth() + TileCoordinate.TILE_SIZE) >> 4; // + 16 to hide tile popping in/out

        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.getHeight() + TileCoordinate.TILE_SIZE) >> 4;

        for (int y=y0; y<y1; y++) {
            for (int x=x0; x<x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }

        for (Projectile projectile : projectileList) {
            projectile.render(screen);
        }

        for (Particle particle : particleList) {
            particle.render(screen);
        }

        if (!player.isRemoved()) {
            player.render(screen);
        }

        enemies.stream().filter(enemy -> !enemy.isRemoved()).forEach(enemy -> enemy.render(screen));

        stationaryObjects.forEach(screen::renderStationary);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return TileConstants.VOID_TILE;
        }

        int tileIndex = x + y * width;

        if (tiles[tileIndex] == TileColorCodes.GRASS) {
            return TileConstants.GRASS;
        } else if (tiles[tileIndex] == TileColorCodes.GRASS2) {
            return TileConstants.GRASS_2;
        } else if (tiles[tileIndex] == TileColorCodes.GRASS3) {
            return TileConstants.GRASS_3;
        } else if (tiles[tileIndex] == TileColorCodes.FLOWER) {
            return TileConstants.FLOWER;
        } else if (tiles[tileIndex] == TileColorCodes.STONE) {
            return TileConstants.STONE;
        } else if (tiles[tileIndex] == TileColorCodes.BRICK_STONE) {
            return TileConstants.BRICK_STONES;
        } else if (tiles[tileIndex] == TileColorCodes.WATER) {
            return TileConstants.WATER;
        } else if (tiles[tileIndex] == TileColorCodes.WOOD_FLOOR) {
            return TileConstants.WOOD_FLOOR;
        } else if (tiles[tileIndex] == TileColorCodes.SAIL) {
            return TileConstants.SAIL;
        }

        return TileConstants.VOID_TILE;
    }

    public boolean xTileCollision(double x, double y, double xa) {
        boolean collision = getTile((int) ((x + xa) / TileCoordinate.TILE_SIZE), (int) (y / TileCoordinate.TILE_SIZE)).solid();
        collision = collision || getTile((int) ((x + xa) / TileCoordinate.TILE_SIZE), (int) (y / TileCoordinate.TILE_SIZE)).solid();

        return collision;
    }

    public boolean yTileCollision(double x, double y, double ya) {
        boolean collision = getTile((int) (x / TileCoordinate.TILE_SIZE), (int) ((y + ya) / TileCoordinate.TILE_SIZE)).solid();
        collision = collision || getTile((int) (x / TileCoordinate.TILE_SIZE), (int) ((y + ya) / TileCoordinate.TILE_SIZE)).solid();

        return collision;
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public List<AbstractMob> getEnemies() {
        return enemies;
    }

    List<GameObject> getStationaryObjects() {
        return stationaryObjects;
    }

    public List<Projectile> getProjectileList() {
        return projectileList;
    }

    public List<Particle> getParticleList() {
        return particleList;
    }
}
