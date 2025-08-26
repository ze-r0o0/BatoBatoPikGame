package gamestates;

import Sound.SoundManager;
import UI.MenuUI;
import utils.Constants.GameStates;
import utils.Constants.menuButtons;

import java.awt.*;
import java.awt.event.*;

/**
 * Main game state class that handles the menu state of the Rock Paper Scissors game.
 * Implements event listeners for mouse input.
 */
public class GameMenu implements Statemethods, MouseListener, MouseMotionListener {
    private MenuUI menuUI;
    private GameState gameState;

    /**
     * Constructor for GameMenu
     * Initializes the menu UI and game state
     */
    public GameMenu()
    {
        this.gameState = gameState;
        menuUI = new MenuUI();
    }

    /**
     * Updates the game menu state (logic can be added if needed)
     */
    @Override
    public void update() {
        // Update logic if needed
    }

    /**
     * Draws the game menu UI on the screen
     * @param g Graphics object for rendering
     * @param width Width of the component
     * @param height Height of the component
     */
    @Override
    public void draw(Graphics g, int width, int height) {
        menuUI.draw(g, width, height);
    }

    /**
     * Handles mouse click events on menu buttons
     * @param e MouseEvent containing information about the click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int buttonClicked = menuUI.getButtonClicked(e.getX(), e.getY(), e.getComponent().getWidth());
        if (buttonClicked != -1) {
            switch (buttonClicked) {
                case menuButtons.PLAY:
                    System.out.println("Play button clicked"); //Debug
                    SoundManager.switchToPlayingMusic();
                    GameState.setState(GameStates.PLAYING);
                    SoundManager.getClickSound().play();
                    break;
                case menuButtons.SETTINGS:
                    System.out.println("Settings button clicked"); // Debug
                    GameState.setState(GameStates.SETTINGS);
                    SoundManager.getClickSound().play();
                    break;
                case menuButtons.QUIT:
                    System.out.println("Quit button clicked"); // Debug
                    SoundManager.getClickSound().play();
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Handles mouse movement over the menu buttons
     * @param e MouseEvent containing the current mouse position
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int buttonHovered = menuUI.getButtonClicked(e.getX(), e.getY(), e.getComponent().getWidth());

        // Reset all buttons
        for (int i = 0; i < menuButtons.TOTAL_MENU_BUTTONS; i++) {
            menuUI.setButtonHovered(i, i == buttonHovered);
        }

        if (buttonHovered != -1) {
            e.getComponent().repaint();
        }
    }

    /**
     * Handles mouse press events on menu buttons
     * @param e MouseEvent containing information about the press
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int buttonPressed = menuUI.getButtonClicked(e.getX(), e.getY(), e.getComponent().getWidth());
        if (buttonPressed != -1) {
            menuUI.setButtonPressed(buttonPressed, true);
            e.getComponent().repaint();
        }
    }

    /**
     * Handles mouse release events on menu buttons
     * Resets all button press states
     * @param e MouseEvent containing information about the release
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Reset all buttons
        for (int i = 0; i < menuButtons.TOTAL_MENU_BUTTONS; i++) {
            menuUI.setButtonPressed(i, false);
        }
        e.getComponent().repaint();
    }

    /**
     * Handles mouse exit events from the menu
     * Resets all hover states of buttons
     * @param e MouseEvent when the mouse exits the component
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Reset hover states
        for (int i = 0; i < menuButtons.TOTAL_MENU_BUTTONS; i++) {
            menuUI.setButtonHovered(i, false);
        }
        e.getComponent().repaint();
    }

    // Unused but required interface methods
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}


}