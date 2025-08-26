package main;

import gamestates.GameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Panel class that handles rendering and input management
 * Acts as the main display surface for the game
 */
public class GamePanel extends JPanel {
    private int panelWidth = 0, panelHeight = 0;
    // Current active input listeners
    private MouseListener currentMouseListener;
    private MouseMotionListener currentMotionListener;
    private KeyListener currentKeyListener;

    /**
     * Constructor sets up the panel and initializes input handling
     */
    public GamePanel() {
        setFocusable(true);
        requestFocusInWindow();

        // Set up state change listener to handle input updates
        GameState.setStateChangeListener(this::updateListeners);

        // Initialize listeners based on starting state
        updateListeners(GameState.getCurrentState());
    }

    /**
     * Updates input listeners when game state changes
     * Removes old listeners and adds new ones based on current game state
     * @param newState The new game state to switch to
     */
    private void updateListeners(int newState)
    {
        // Remove any existing listeners first
        if (currentMouseListener != null) {
            removeMouseListener(currentMouseListener);
        }
        if (currentMotionListener != null) {
            removeMouseMotionListener(currentMotionListener);
        }
        if (currentKeyListener != null) {
            removeKeyListener(currentKeyListener);
        }

        // Set appropriate listeners based on game state
        switch (newState) {
            case GameState.MENU:
                currentMouseListener = GameState.getGameMenu();
                currentMotionListener = GameState.getGameMenu();
                break;
            case GameState.PLAYING:
                currentMouseListener = GameState.getPlaying();
                currentMotionListener = GameState.getPlaying();
                currentKeyListener = GameState.getPlaying();
                break;
            case GameState.SETTINGS:
                currentMouseListener = GameState.getSettings();
                currentMotionListener = GameState.getSettings();
                currentKeyListener = GameState.getSettings();
                break;
        }

        // Add the new listeners to the panel
        addMouseListener(currentMouseListener);
        addMouseMotionListener(currentMotionListener);

        // Only add key listener if it's not null
        if (currentKeyListener != null) {
            addKeyListener(currentKeyListener);
        }
    }

    /**
     * Overridden paintComponent method handles rendering
     * Updates panel dimensions and delegates rendering to current game state
     * @param g Graphics context for rendering
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelWidth = getWidth();
        panelHeight = getHeight();
        GameState.render(g, panelWidth, panelHeight);
    }
}