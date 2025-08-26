package gamestates;

import java.awt.Graphics;
import java.util.function.Consumer;

/**
 * Manages the current state of the game and transitions between states.
 * Provides methods to set, retrieve, and render the active game state.
 */
public class GameState
{

    // Constants representing different game states
    public static final int MENU = 0;
    public static final int PLAYING = 1;
    public static final int SETTINGS = 2;

    // Current active state
    private static int state = 0;

    // Instances of different game state objects
    private static GameMenu gameMenu;
    private static Playing playing;
    private static Settings settings;

    // Listener for state change events
    private static Consumer<Integer> stateChangeListener;

    // Static block to initialize state objects
    static
    {
        gameMenu = new GameMenu();
        playing = new Playing();
        settings = new Settings();
    }

    /**
     * Sets the active game state and notifies the state change listener if available.
     *
     * @param newState The new state to switch to
     */
    public static void setState(int newState)
    {
        state = newState;
        if (stateChangeListener != null) {
            stateChangeListener.accept(newState);
        }
    }

    /**
     * Sets a listener to handle state change events.
     *
     * @param listener A Consumer that accepts the new state as an argument
     */
    public static void setStateChangeListener(Consumer<Integer> listener)
    {
        stateChangeListener = listener;
    }

    /**
     * Retrieves the currently active game state.
     *
     * @return The current state as an integer constant
     */
    public static int getCurrentState()
    {
        return state;
    }

    /**
     * Renders the active state by delegating the draw call to the appropriate state object.
     *
     * @param g Graphics object for rendering
     * @param width Width of the rendering area
     * @param height Height of the rendering area
     */
    public static void render(Graphics g, int width, int height) {
        switch (state) {
            case MENU:
                gameMenu.draw(g, width, height);
                break;
            case PLAYING:
                playing.draw(g, width, height);
                break;
            case SETTINGS:
                settings.draw(g, width, height);
                break;
        }
    }

    /**
     * Retrieves the instance of the GameMenu state.
     *
     * @return The GameMenu instance
     */
    public static GameMenu getGameMenu() {
        return gameMenu;
    }

    /**
     * Retrieves the instance of the Playing state.
     *
     * @return The Playing instance
     */
    public static Playing getPlaying() {
        return playing;
    }

    /**
     * Retrieves the instance of the Settings state.
     *
     * @return The Settings instance
     */
    public static Settings getSettings() {
        return settings;
    }
}