package com.jeromewagener.game.levels.tile;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.graphics.Sprite;

class GrassTile extends Tile {

    GrassTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }
}
