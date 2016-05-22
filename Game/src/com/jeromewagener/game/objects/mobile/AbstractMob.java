package com.jeromewagener.game.objects.mobile;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.input.Keyboard;
import com.jeromewagener.game.objects.GameObject;
import com.jeromewagener.game.objects.projectile.FireProjectile;
import com.jeromewagener.game.objects.projectile.GhostProjectile;
import com.jeromewagener.game.objects.projectile.Projectile;

public abstract class AbstractMob extends GameObject {
    Keyboard keyboard;
    int dir = 2;
    int animationCounter = 0;
    boolean isWalking;
    public int life = 100;

    void move(int xa, int ya) {
        if (xa > 0) { dir = 1; }
        if (xa < 0) { dir = 3; }
        if (ya > 0) { dir = 2; }
        if (ya < 0) { dir = 0; }

        if (!xCollision(xa)) {
            x += xa;
        }

        if (!yCollision(ya)) {
            y += ya;
        }
    }

    public void update() {
    }

    private boolean xCollision(int xa) {
        boolean collision = level.getTile(((x + xa + 5)>>4), y>>4).solid();
        collision = collision || level.getTile(((x + xa - 5)>>4), y>>4).solid();
        return collision;
    }

    private boolean yCollision(int ya) {
        boolean collision = level.getTile(x>>4, ((y + ya + 10)>>4)).solid();
        collision = collision || level.getTile(x>>4, ((y + ya - 5)>>4)).solid();
        return collision;
    }

    public void render(Screen screen) {

    }

    void shoot(int x, int y, double angle, boolean isGhost) {
        Projectile p;
        if (isGhost) {
            p = new GhostProjectile(this, x, y, angle);
        } else {
            p = new FireProjectile(this, x, y, angle);
        }

        p.init(level);
        level.getProjectileList().add(p);
    }

    public boolean isDead() {
        return life < 0;
    }
}
