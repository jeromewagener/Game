package com.jeromewagener.game.objects.particle;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;

public class BloodParticle extends Particle {
    private Sprite sprite;
    private int life;
    private int time = 0;

    public BloodParticle(int x, int y, int life) {
        super(x,y,life);
        this.x = x;
        this.xx = x;
        this.y = y;
        this.yy = y;
        this.life = life + random.nextInt(15) - 10;
        sprite = Sprite.BLOOD_PARTICLE;

        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();

        this.zz = 10.0;
    }

    @Override
    public void update() {
        time++;
        if (time == Integer.MAX_VALUE) {
            // Safe guard
            time = 0;
        }

        if (time > life) {
            remove();
        }

        if (zz < 0) {
            zz = 0;
            za *= -0.90;
            xa *= 0.50;
            ya *= 0.50;
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
