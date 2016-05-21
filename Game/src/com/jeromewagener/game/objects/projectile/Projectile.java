package com.jeromewagener.game.objects.projectile;

import com.jeromewagener.game.objects.GameObject;
import com.jeromewagener.game.graphics.Sprite;

public abstract class Projectile extends GameObject {

    protected final int xOrigin, yOrigin;
    protected final GameObject source;
    protected double angle;
    protected Sprite sprite;
    protected double x, y;
    protected double newX, newY;
    protected int distance;
    protected double speed, range, damage;

    public Projectile(GameObject source, int xOrigin, int yOrigin, double angle) {
        this.source = source;
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.x = xOrigin;
        this.y = yOrigin;
        this.angle = angle;
    }

    protected void move() {

    }

    public double getTravelledDistance() {
        return Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
    }
}
