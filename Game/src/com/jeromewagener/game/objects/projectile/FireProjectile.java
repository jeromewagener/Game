package com.jeromewagener.game.objects.projectile;

import com.jeromewagener.game.Game;
import com.jeromewagener.game.objects.GameObject;
import com.jeromewagener.game.objects.mobile.AbstractMob;
import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;
import com.jeromewagener.game.objects.producer.Producer;

import java.util.Iterator;

public class FireProjectile extends Projectile {
    public static final int FIRE_RATE = 6;

    public FireProjectile(GameObject source, int xOrigin, int yOrigin, double angle) {
        super(source, xOrigin, yOrigin, angle);
        speed = 1;
        range = GameObject.random.nextInt(30) + 50;
        damage = 20;
        sprite = Sprite.PROJECTILE;

        newX = speed * Math.cos(angle);
        newY = speed * Math.sin(angle);
    }

    @Override
    public void update() {
        // check mob collisions
        int newX1 = new Double(x + newX).intValue();
        int newY1 = new Double(y + newY).intValue();

        Iterator<AbstractMob> enemyIterator = level.getEnemies().iterator();
        while (enemyIterator.hasNext()) {
            AbstractMob enemy = enemyIterator.next();
            if (enemy.x - 10 <= newX1
                    && newX1 <= enemy.x + 10
                    && enemy.y - 10 <= newY1
                    && newY1 <= enemy.y + 10) {

                if (source == enemy) {
                    move();
                    return;
                }

                Producer.produceBloodParticles(level, (int) x, (int) y, 10, 10);
                enemy.life -= 10;

                if (enemy.isDead()) {
                    enemyIterator.remove();

                    if (level.getEnemies().isEmpty()) {
                        Game.gameState = 2;
                    }
                }

                remove();
            }

            if (level.xTileCollision(x, y, newX) || level.yTileCollision(x, y, newY)) {
                Producer.produceDebrisParticles(level, (int) x, (int) y, 20, 30);

                remove();
            }
        }

        move();
    }

    @Override
    protected void move() {
        x += newX;
        y += newY;

        if (getTravelledDistance() > range) {
            remove();
        }
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite((int) x - 8, (int) y - 2, sprite, true);
    }
}
