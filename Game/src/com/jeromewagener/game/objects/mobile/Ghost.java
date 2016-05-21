package com.jeromewagener.game.objects.mobile;

import com.jeromewagener.game.graphics.AnimatedSprite;
import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.SpriteSheet;
import com.jeromewagener.game.levels.TileCoordinate;

public class Ghost extends AbstractMob {
    private long counter = 0;
    private int go = 0;
    private AnimatedSprite animatedSprite;

    public Ghost(int startCol, int startRow, TileCoordinate spawnPoint) {
        this.x = spawnPoint.getX();
        this.y = spawnPoint.getY();

        animatedSprite = new AnimatedSprite(SpriteSheet.tiles, startCol, startRow, 32, 32, 3);
    }

    @Override
    public void update() {
        counter++;
        animationCounter++;

        if (counter > 100000) {
            counter = 0;
        }

        if (counter % 20 == 0 && isPlayerInProximity()) {
            double dx = level.getPlayer().x - x;
            double dy = level.getPlayer().y - y;
            double angle = Math.atan2(dy, dx);

            shoot(x, y, angle, true);
        }

        if (counter % 50 == 0) {
            go = random.nextInt(4);
        }

        isWalking = true;

        int xa = 0, ya = 0;

        if (go == 1) {
            xa++;
        }
        if (go == 3) {
            xa--;
        }
        if (go == 2) {
            ya++;
        }
        if (go == 0) {
            ya--;
        }

        move(xa, ya);
    }

    @Override
    public void render(Screen screen) {
        screen.renderGhost(
                x - TileCoordinate.TILE_SIZE,
                y - TileCoordinate.TILE_SIZE,
                animatedSprite.getSprite(AnimatedSprite.Animation.values()[dir]));
    }

    public boolean isPlayerInProximity() {
        return !level.getPlayer().isDead() && Math.abs(level.getPlayer().x - x) < 80 && Math.abs(level.getPlayer().y - y) < 80;
    }
}
