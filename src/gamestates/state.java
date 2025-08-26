package gamestates;

import main.BatoBatoPikGame;

public class state
{
    protected BatoBatoPikGame game;

    public void State(BatoBatoPikGame game)
    {
        this.game = game;
    }
    public BatoBatoPikGame getGame()
    {
        return game;
    }
}
