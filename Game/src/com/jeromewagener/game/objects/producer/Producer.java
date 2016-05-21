package com.jeromewagener.game.objects.producer;

import com.jeromewagener.game.objects.GameObject;
import com.jeromewagener.game.levels.Level;
import com.jeromewagener.game.objects.particle.BloodParticle;
import com.jeromewagener.game.objects.particle.Particle;

public class Producer extends GameObject {
    public static void produceDebrisParticles(Level level, int x, int y, int amount, int life) {
        for (int i=0; i<amount; i++) {
            level.getParticleList().add(new Particle(x, y, life));
        }
    }

    public static void produceBloodParticles(Level level, int x, int y, int amount, int life) {
        for (int i=0; i<amount; i++) {
            level.getParticleList().add(new BloodParticle(x, y, life));
        }
    }
}
