package com.jeromewagener.game.levels;

import com.jeromewagener.game.input.Keyboard;
import com.jeromewagener.game.objects.mobile.Ghost;
import com.jeromewagener.game.objects.mobile.Player;
import com.jeromewagener.game.objects.stationary.Tree;

public class SpawnLevel extends Level {
    public SpawnLevel(String path) {
        super(path);

        TileCoordinate spawnPoint = new TileCoordinate(128, 128);
        player = new Player(Keyboard.getInstance(), spawnPoint);
        player.init(this);

        Ghost ghost = new Ghost(8, 4, spawnPoint);
        ghost.init(this);
        enemies.add(ghost);

        ghost = new Ghost(16, 4, spawnPoint);
        ghost.init(this);
        enemies.add(ghost);

        ghost = new Ghost(0, 10, spawnPoint);
        ghost.init(this);
        enemies.add(ghost);

        ghost = new Ghost(8, 10, spawnPoint);
        ghost.init(this);
        enemies.add(ghost);

        ghost = new Ghost(16, 10, spawnPoint);
        ghost.init(this);
        enemies.add(ghost);

        for (int i=0; i<5; i++) {
            Tree tree = new Tree();
            tree.x = 110 + (i*10);
            tree.y = 130 + (i*6);
            stationaryObjects.add(tree);
        }
    }
}
