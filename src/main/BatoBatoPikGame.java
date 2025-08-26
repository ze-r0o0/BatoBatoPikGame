package main;

/**
 * Main game class that handles the game loop and core initialization
 * Implementation of the game "Bato Bato Pik" (Rock Paper Scissors)
 */
public class BatoBatoPikGame implements Runnable
{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    // Constants for frame rate and update rate
    private final int FPS_SET = 60;  // Frames per second for rendering
    private final int UPS_SET = 120; // Updates per second for game logic

    /**
     * Constructor initializes the game components and starts the game loop
     */
    public BatoBatoPikGame()
    {

        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    /**
     * Creates and starts the game thread
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Update method for game logic
     */
    public void update() {
        // Update logic here
    }

    /**
     * Main game loop implementation
     * Handles timing for updates and frame rendering
     * Maintains consistent game speed and frame rate
     */
    @Override
    public void run() {
        // Calculate time per frame and update in nanoseconds
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        // FPS and UPS counting variables
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        // Delta accumulators for updates and frames
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            // Calculate time passed and accumulate in deltas
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            // Update game logic if enough time has passed
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // Render frame if enough time has passed
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Print FPS and UPS every second
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " UPS:" + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}