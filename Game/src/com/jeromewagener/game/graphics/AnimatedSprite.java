package com.jeromewagener.game.graphics;

import com.jeromewagener.game.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimatedSprite {
    private HashMap<Animation, ArrayList<Sprite>> animationsToSprites = new HashMap<>();
    private int animationCounter = 0;
    private int numberOfAnimationSteps;
    private long counter = 0;
    private Keyboard keyboard;

    public AnimatedSprite(SpriteSheet spriteSheet, int column, int row, int singleAnimationWidth,
                          int singleAnimationHeight, int numberOfAnimationSteps) {

        this.numberOfAnimationSteps = numberOfAnimationSteps;

        for (Animation animation : Animation.values()) {
            ArrayList<Sprite> sprites = new ArrayList<>();
            for (int i = 0; i < numberOfAnimationSteps; i++) {
                sprites.add(new Sprite(singleAnimationWidth, singleAnimationHeight, column + (2 * animation.ordinal()), row + (2 * i), spriteSheet));
            }

            animationsToSprites.put(animation, sprites);
        }
    }

    public Sprite getSprite(Animation animation) {
        int speedFactor;
        if (keyboard != null && keyboard.shift) {
            speedFactor = 20;
        } else {
            speedFactor = 100;
        }

        if (counter++ % speedFactor == 0) {
            animationCounter++;
        }

        if (animationCounter > (numberOfAnimationSteps - 1)) {
            animationCounter = 0;
        }

        return animationsToSprites.get(animation).get(animationCounter);
    }

    public Sprite getDefaultSprite(Animation animation) {
        return animationsToSprites.get(animation).get(1);
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public enum Animation {
        MOVE_UP,
        MOVE_RIGHT,
        MOVE_DOWN,
        MOVE_LEFT
    }
}
