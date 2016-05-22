package com.jeromewagener.game.objects.particle;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;
import com.jeromewagener.game.objects.GameObject;

public class Particle extends GameObject {
    private Sprite sprite;
    private int life;
    private int time = 0;

    double xx, yy, zz;
    double xa, ya, za;

    public Particle(int x, int y, int life) {
        this.x = x;
        this.xx = x;
        this.y = y;
        this.yy = y;
        this.life = life + random.nextInt(25) - 10;
        sprite = Sprite.PARTICLE;

        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();

        this.zz = 20.0;
    }

    @Override
    public void update() {
        time++;
        if (time >= 250) {
            // Safe guard
            time = 0;
        }

        if (time > life) {
            remove();
        }

        if (zz < 0) {
            zz = 0;
            za *= -0.8;
            xa *= 0.4;
            ya *= 0.4;
        }

        za -= 0.1;

        this.xx += xa;
        this.yy += ya;
        this.zz += za;
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
    }
}
