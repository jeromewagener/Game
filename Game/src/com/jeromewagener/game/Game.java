package com.jeromewagener.game;

import com.jeromewagener.game.graphics.Screen;
import com.jeromewagener.game.input.Keyboard;
import com.jeromewagener.game.input.Mouse;
import com.jeromewagener.game.levels.Level;
import com.jeromewagener.game.levels.SpawnLevel;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
    private static final double UPDATES_PER_SECOND = 60.0;
    private static final double NANO_SECONDS_IN_SECOND = 1000000000.0;
    private static final int SCALE = 2;
    private static final int WIDTH = 1024 / SCALE;
    private static final int HEIGHT = 640 / SCALE;

    private Thread thread;
    private boolean isRunning = false;

    private Screen screen;

    private Level level;

    public static int gameState = 0; // 0 == ok, 1 == lost, 2 == win

    private JFrame jFrame;
    private BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] gamePixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

    public Game() {
        playMusic("st.wav");

        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);

        screen = new Screen(WIDTH, HEIGHT);
        jFrame = new JFrame();
        Mouse mouse = new Mouse();
        level = new SpawnLevel("/textures/level.png");

        addKeyListener(Keyboard.getInstance());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double nanoSeconds = NANO_SECONDS_IN_SECOND / UPDATES_PER_SECOND;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        requestFocus();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSeconds;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            // Once per second, show frames and updates
            if (System.currentTimeMillis() - timer > 1000) {
                jFrame.setTitle("Updates per second: " + updates + " - Frames per second: " + frames);
                timer += 1000;
                updates = 0;
                frames = 0;

            }
        }
        stop();
    }

    private void update() {
        level.update();
    }

    private void render() {
        int xScrollMap = level.getPlayer().x - (screen.getWidth() >> 1);
        int yScrollMap = level.getPlayer().y - (screen.getHeight() >> 1);

        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy();
        }

        screen.clear();
        level.render(xScrollMap, yScrollMap, screen);

        // Copy gamePixels from screen class to bufferedImage displayed by Game. (Viewport)
        System.arraycopy(screen.getPixels(), 0, gamePixels, 0, gamePixels.length);

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);

        /*try {
            graphics.drawImage(ImageIO.read(Game.class.getResource("/menu/welcome.png")), 0, 0, getWidth(), getHeight(), null);

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        graphics.setFont(new Font("Arial", Font.BOLD, 40));
        if (gameState == 1) {
            graphics.drawString("You lose!", 100, 100);
        } else if (gameState == 2) {
            graphics.drawString("You win!", 100, 100);
        }

        graphics.dispose();
        bufferStrategy.show();
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.jFrame.setResizable(false);
        game.jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        game.jFrame.setUndecorated(false);
        game.jFrame.setTitle("Game");
        game.jFrame.add(game);
        game.jFrame.pack();
        game.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.jFrame.setLocationRelativeTo(null);

        game.jFrame.setVisible(true);


        game.start();
    }

    public static int getWindowWidth() {
        return WIDTH * SCALE;
    }

    public static int getWindowHeight() {
        return HEIGHT * SCALE;
    }

    public synchronized void playMusic(final String track) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(Game.class.getResourceAsStream("/music/" + track));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
