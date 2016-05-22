package com.jeromewagener.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private boolean[] keys = new boolean[120];
    private boolean up, down, left, right, space, shift;
    private static Keyboard INSTANCE;

    private Keyboard() {

    }

    public static Keyboard getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Keyboard();
        }

        return INSTANCE;
    }

    public void update() {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        space = keys[KeyEvent.VK_SPACE];
        shift = keys[KeyEvent.VK_SHIFT];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public boolean isUpPressed() {
        return up;
    }

    public boolean isDownPressed() {
        return down;
    }

    public boolean isLeftPressed() {
        return left;
    }

    public boolean isRightPressed() {
        return right;
    }

    public boolean isSpacePressed() {
        return space;
    }

    public boolean isShiftPressed() {
        return shift;
    }
}
