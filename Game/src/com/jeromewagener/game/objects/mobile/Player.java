package com.jeromewagener.game.objects.mobile;

import com.jeromewagener.game.Game;
import com.jeromewagener.game.graphics.AnimatedSprite;
import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.SpriteSheet;
import com.jeromewagener.game.input.Keyboard;
import com.jeromewagener.game.input.Mouse;
import com.jeromewagener.game.levels.TileCoordinate;
import com.jeromewagener.game.objects.particle.Particle;
import com.jeromewagener.game.objects.projectile.FireProjectile;
import com.jeromewagener.game.objects.projectile.Projectile;

import java.util.Iterator;

public class Player extends AbstractMob {
    private int fireRate = 0;
    private AnimatedSprite animatedSprite;

    public Player(Keyboard keyboard, TileCoordinate spawnPoint) {
        this.keyboard = keyboard;
        this.x = spawnPoint.getX();
        this.y = spawnPoint.getY();

        animatedSprite = new AnimatedSprite(SpriteSheet.tiles, 0, 4, 32, 32, 3);
        animatedSprite.setKeyboard(keyboard);
    }

    public void update() {
        if (fireRate>0) {
            fireRate--;
        }

        int xa = 0, ya = 0;

        if (animationCounter < 0) {
            animationCounter = 0;
        } else {
            animationCounter++;
        }

        if (keyboard.isUpPressed()) {
            ya--;
            if (keyboard.isShiftPressed()) {
                ya--;
            }
        }
        if (keyboard.isDownPressed()) {
            ya++;
            if (keyboard.isShiftPressed()) {
                ya++;
            }
        }
        if (keyboard.isLeftPressed()) {
            xa--;
            if (keyboard.isShiftPressed()) {
                xa--;
            }
        }
        if (keyboard.isRightPressed()) {
            xa++;
            if (keyboard.isShiftPressed()) {
                xa++;
            }
        }

        clear();
        updateShooting();

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            isWalking = true;
        } else {
            isWalking = false;
        }
    }

    public void gameOverUpdate() {
        clear();
        updateShooting();
    }

    private void clear() {
        for (Iterator<Projectile> projectileIterator = level.getProjectileList().iterator(); projectileIterator.hasNext();) {
            if (projectileIterator.next().isRemoved()) {
                projectileIterator.remove();
            }
        }

        for (Iterator<Particle> particleIterator = level.getParticleList().iterator(); particleIterator.hasNext();) {
            if (particleIterator.next().isRemoved()) {
                particleIterator.remove();
            }
        }
    }

    private void updateShooting() {
        if (Mouse.getButton() == 1 && fireRate <= 0) {
            double dx = Mouse.getX() - Game.getWindowWidth() / 2;
            double dy = Mouse.getY() - Game.getWindowHeight() / 2;
            double angle = Math.atan2(dy, dx);

            shoot(x, y, angle, false);

            fireRate = FireProjectile.FIRE_RATE;
        }
    }

    public void render(Screen screen) {
        if (isWalking) {
            screen.renderPlayer(
                    x - TileCoordinate.TILE_SIZE,
                    y - TileCoordinate.TILE_SIZE,
                    animatedSprite.getSprite(AnimatedSprite.Animation.values()[dir]));
        } else {
            screen.renderPlayer(
                    x - TileCoordinate.TILE_SIZE,
                    y - TileCoordinate.TILE_SIZE,
                    animatedSprite.getDefaultSprite(AnimatedSprite.Animation.values()[dir]));
        }
    }
}
