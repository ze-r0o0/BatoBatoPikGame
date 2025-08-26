package main;

import gamestates.GameState;

/**
 * Main entry point for the Bato Bato Pik game
 */
public class Main
{
    /**
     * Main method initializes the game
     * Sets initial game state to MENU and creates game instance
     */

    public static void main(String[] args)
    {
        GameState.setState(GameState.MENU);  // Set initial game state
        new BatoBatoPikGame();  // Create and start game
    }
}
