package main;

import javax.swing.*;

/**
 * Creates and manages the main game window
 * Handles window-specific settings and initialization
 */
public class GameWindow {
    private JFrame jframe;

    /**
     * Constructor creates and configures the game window
     * @param gamePanel The main game panel to add to the window
     */
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.setTitle("Bato Bato Pik");  // Set window title
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the game panel to the window
        jframe.add(gamePanel);

        // Configure window properties
        jframe.pack();
        jframe.setSize(1530, 890);  // Set initial window size
        jframe.setLocationRelativeTo(null);  // Center window on screen
        jframe.setResizable(true);  // Allow window resizing
        jframe.setVisible(true);  // Make window visible
    }
}