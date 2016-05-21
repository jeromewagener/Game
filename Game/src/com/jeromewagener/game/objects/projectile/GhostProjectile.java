package com.jeromewagener.game.objects.projectile;

import com.jeromewagener.game.Game;
import com.jeromewagener.game.objects.GameObject;
import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;
import com.jeromewagener.game.objects.producer.Producer;

public class GhostProjectile extends Projectile {
    public GhostProjectile(GameObject source, int xOrigin, int yOrigin, double angle) {
        super(source, xOrigin, yOrigin, angle);
        speed = 1;
        range = GameObject.random.nextInt(30) + 50;
        damage = 20;
        sprite = Sprite.GHOST_PROJECTILE;

        newX = speed * Math.cos(angle);
        newY = speed * Math.sin(angle);
    }

    @Override
    public void update() {
        // TODO remove only for testing
        double dx = level.getPlayer().x - x;
        double dy = level.getPlayer().y - y;
        double angle = Math.atan2(dy, dx);
        newX = speed * Math.cos(angle);
        newY = speed * Math.sin(angle);

        // check player collisions
        int newX1 = new Double(x + newX).intValue();
        int newY1 = new Double(y + newY).intValue();

        if (level.getPlayer().x - 10 <= newX1
                && newX1 <= level.getPlayer().x + 10
                && level.getPlayer().y - 10 <= newY1
                && newY1 <= level.getPlayer().y + 10) {


            Producer.produceBloodParticles(level, (int) x, (int) y, 10, 10);
            level.getPlayer().life -= 10;

            if (level.getPlayer().isDead()) {
                level.getPlayer().remove();
                Game.gameState = 1;
            }

            remove();
        }

        if (level.xTileCollision(x,y, newX) || level.yTileCollision(x,y, newY)) {
            Producer.produceDebrisParticles(level, (int) x, (int) y, 20, 30);

            remove();
        }

        move();
    }

    @Override
    protected void move() {
        x += newX;
        y += newY;

        if (getDistance() > range) {
            remove();
        }
    }

    protected double getDistance() {
        return Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite((int) x - 8, (int) y - 2, sprite, true);
    }
}
