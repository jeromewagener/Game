package com.jeromewagener.game.objects;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;
import com.jeromewagener.game.levels.Level;

import java.util.Random;

public abstract class GameObject {
    public Sprite sprite;
    public int x, y;
    private boolean removed = false;
    protected Level level;
    protected final static Random random = new Random();

    public void update() {
    }

    public void render(Screen screen) {
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void init(Level level) {
        this.level = level;
    }
}
