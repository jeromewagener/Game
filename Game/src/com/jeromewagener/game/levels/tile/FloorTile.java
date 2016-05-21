package com.jeromewagener.game.levels.tile;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;

class FloorTile extends Tile {

    FloorTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

    @Override
    public boolean solid() {
        return false;
    }
}
