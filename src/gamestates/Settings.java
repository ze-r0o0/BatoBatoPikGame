package gamestates;

import Sound.SoundManager;
import UI.SettingsUI;
import utils.Constants.GameStates;

import java.awt.*;
import java.awt.event.*;

/**
 * Main game state class that handles the settings state of the Rock Paper Scissors game.
 * Implements event listeners for mouse and key input.
 */
public class Settings implements Statemethods, MouseListener, MouseMotionListener, KeyListener
{
    private SettingsUI settingsUI;
    private GameState gameState;

    /**
     * Constructor for Settings
     * Initializes the settings UI and game state
     */
    public Settings()
    {
        this.gameState = gameState;
        settingsUI = new SettingsUI();
    }

    /**
     * Updates the settings state (logic can be added if needed)
     */
    @Override
    public void update()
    {
        // Update logic if needed
    }

    /**
     * Draws the settings UI on the screen
     * @param g Graphics object for rendering
     * @param width Width of the component
     * @param height Height of the component
     */
    @Override
    public void draw(Graphics g, int width, int height)
    {
        settingsUI.draw(g, width, height);
    }

    /**
     * Handles mouse click events within the settings UI
     * @param e MouseEvent containing information about the click
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        settingsUI.handleClick(e.getX(), e.getY(), e.getComponent().getWidth());
    }

    /**
     * Updates hover state of settings UI based on mouse movement
     * @param e MouseEvent containing the current mouse position
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        settingsUI.updateHoverState(e.getX(), e.getY(), e.getComponent().getWidth());
        e.getComponent().repaint();
    }

    /**
     * Handles mouse press events within the settings UI
     * @param e MouseEvent containing information about the press
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        settingsUI.handlePress(e.getX(), e.getY(), e.getComponent().getWidth());
        e.getComponent().repaint();
    }

    /**
     * Handles mouse release events within the settings UI
     * @param e MouseEvent containing information about the release
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        settingsUI.handleRelease(e.getX(), e.getY(), e.getComponent().getWidth());
        e.getComponent().repaint();
    }

    /**
     * Handles mouse exit events from the settings UI
     * Resets hover states of all UI elements
     * @param e MouseEvent when the mouse exits the component
     */
    @Override
    public void mouseExited(MouseEvent e)
    {
        settingsUI.clearHoverStates();
        e.getComponent().repaint();
    }

    /**
     * Handles key press events in the settings UI
     * Allows escape key to return to the main menu
     * @param e KeyEvent containing information about the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Handle escape key to return to menu
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameState.setState(GameStates.MENU);
            SoundManager.getClickSound().play();
        }
    }

    // Unused but required interface methods
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

}