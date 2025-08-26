package gamestates;

import Sound.SoundManager;
import UI.PlayingUI;
import utils.Constants;
import utils.Constants.playingUICons;
import java.awt.*;
import java.awt.event.*;

/**
 * Main game state class that handles the playing state of the Rock Paper Scissors game.
 * Implements event listeners for mouse and keyboard input.
 */
public class Playing implements Statemethods, MouseListener, MouseMotionListener, KeyListener {
    private PlayingUI playingUI;

    /**
     * Constructor - initializes the playing state UI
     */
    public Playing() {
        playingUI = new PlayingUI();
    }

    /**
     * Update method for game logic
     * Currently empty as game updates are event-driven
     */
    @Override
    public void update() {
    }

    /**
     * Draws the game state using the PlayingUI
     * @param g Graphics context
     * @param panelWidth Width of the game panel
     * @param panelHeight Height of the game panel
     */
    @Override
    public void draw(Graphics g, int panelWidth, int panelHeight) {
        playingUI.draw(g, panelWidth, panelHeight);
    }

    /**
     * Handles mouse movement events for button hover effects
     * @param e MouseEvent containing cursor position
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // Check pause button hover state
        playingUI.checkPauseButtonHover(e.getX(), e.getY());

        // Check pause menu button hovers if game is paused
        playingUI.checkPauseMenuHover(e.getX(), e.getY(),
                e.getComponent().getWidth(),
                e.getComponent().getHeight());

        // Only check game buttons if not paused
        if (!playingUI.isPaused()) {
            int buttonHovered = playingUI.getButtonClicked(
                    e.getX(), e.getY(),
                    e.getComponent().getWidth(),
                    e.getComponent().getHeight()
            );

            // Reset all buttons and set the hovered one
            for (int i = 0; i < playingUICons.TOTAL_RPS_BUTTONS; i++) {
                playingUI.setButtonHovered(i, i == buttonHovered);
            }
        }

        e.getComponent().repaint();
    }

    /**
     * Handles mouse click events for buttons and game interactions
     * @param e MouseEvent containing click position
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle pause button clicks
        if (playingUI.isPauseButtonClicked(e.getX(), e.getY())) {
            SoundManager.getClickSound().play();
            playingUI.togglePause();
            e.getComponent().repaint();
            return;
        }

        // Handle pause menu interactions when game is paused
        if (playingUI.isPaused()) {
            handlePauseMenuClick(e);
            return;
        }

        // Handle gameplay button clicks when not paused
        handleGameplayClick(e);
    }

    /**
     * Handles clicks in the pause menu
     * @param e MouseEvent containing click position
     */
    private void handlePauseMenuClick(MouseEvent e) {
        int pauseMenuButton = playingUI.getPauseMenuButtonClicked(
                e.getX(), e.getY(),
                e.getComponent().getWidth(),
                e.getComponent().getHeight()
        );

        switch (pauseMenuButton) {
            case playingUICons.RESUME_BUTTON:
                SoundManager.getClickSound().play();
                playingUI.togglePause();
                break;
            case playingUICons.RETRY_BUTTON:
                SoundManager.getClickSound().play();
                playingUI.playAgain();
                break;
            case playingUICons.MENU_BUTTON:
                SoundManager.getClickSound().play();
                SoundManager.switchToMenuMusic();
                playingUI.cleanupForMenu();
                GameState.setState(Constants.GameStates.MENU);
                break;
        }
        e.getComponent().repaint();
    }

    /**
     * Handles clicks during active gameplay
     * @param e MouseEvent containing click position
     */
    private void handleGameplayClick(MouseEvent e) {
        int buttonClicked = playingUI.getButtonClicked(
                e.getX(), e.getY(),
                e.getComponent().getWidth(),
                e.getComponent().getHeight()
        );

        if (buttonClicked != -1) {
            SoundManager.getClickSound().play();
            playingUI.setCurrentHand(buttonClicked);
            e.getComponent().repaint();  // Immediate visual feedback
        } else if (playingUI.canResetGame()) {
            playingUI.resetGame();
            e.getComponent().repaint();
        }
    }

    /**
     * Resets hover states when mouse exits the window
     * @param e MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < playingUICons.TOTAL_RPS_BUTTONS; i++) {
            playingUI.setButtonHovered(i, false);
        }
        e.getComponent().repaint();
    }

    /**
     * Handles keyboard input for game controls
     * @param e KeyEvent containing the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                handleEscapeKey(e);
                break;
            case KeyEvent.VK_Q:
                handleGameplayKey(e, playingUICons.ROCK);
                break;
            case KeyEvent.VK_W:
                handleGameplayKey(e, playingUICons.PAPER);
                break;
            case KeyEvent.VK_E:
                handleGameplayKey(e, playingUICons.SCISSORS);
                break;
            case KeyEvent.VK_ENTER:
                if (playingUI.canResetGame()) {
                    SoundManager.getClickSound().play();
                    playingUI.resetGame();
                    e.getComponent().repaint();
                }
                break;
        }
    }

    /**
     * Handles the escape key functionality
     * @param e KeyEvent
     */
    private void handleEscapeKey(KeyEvent e)
    {
        // Play click sound
        SoundManager.getClickSound().play();

        // If game is already paused, return to menu
        if (playingUI.isPaused())
        {
            playingUI.cleanupForMenu();
            SoundManager.switchToMenuMusic();
            GameState.setState(Constants.GameStates.MENU);
        }
        // If game is not paused, pause the game
        else {
            playingUI.togglePause();
        }
        e.getComponent().repaint();
    }

    /**
     * Handles gameplay keys (Q, W, E)
     * @param e KeyEvent
     * @param hand The hand choice corresponding to the key
     */
    private void handleGameplayKey(KeyEvent e, int hand) {
        SoundManager.getClickSound().play();
        playingUI.setCurrentHand(hand);
        e.getComponent().repaint();
    }

    // Unused but required interface methods
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}