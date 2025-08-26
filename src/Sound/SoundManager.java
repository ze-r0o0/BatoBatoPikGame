package Sound;
import utils.Constants;

/**
 * Manages sound effects and music for the application.
 * Provides centralized control for loading, playing, and manipulating audio.
 */
public class SoundManager
{

    // Default volume levels for music and sound effects
    public static final float DEFAULT_MUSIC_VOLUME = 0.3f;
    public static final float DEFAULT_SFX_VOLUME = 0.7f;

    // Static references to sound objects
    private static SFX clickSound;
    private static SFX winSound;
    private static SFX loseSound;
    private static MusicPlayer menuMusic;
    private static MusicPlayer playingMusic;

    // Tracks whether sounds have been initialized
    private static boolean isInitialized = false;

    // Initialize sounds when class is first loaded
    static {
        initializeSounds();
    }

    /**
     * Initializes all sound resources.
     * Creates sound effects and music players for different game states.
     */
    public static void initializeSounds()
    {
        if (!isInitialized)
        {
            // Create sound effects
            clickSound = new SFX(Constants.SoundPaths.CLICK_SOUND);
            winSound = new SFX(Constants.SoundPaths.WIN_SOUND);
            loseSound = new SFX(Constants.SoundPaths.LOSE_SOUND);

            // Create music players with looping enabled
            menuMusic = new MusicPlayer(Constants.SoundPaths.MENU_MUSIC, true);
            playingMusic = new MusicPlayer(Constants.SoundPaths.PLAYING_MUSIC, true);

            // Ensure no music is playing during initialization
            if (playingMusic != null) playingMusic.stop();
            if (menuMusic != null) menuMusic.stop();

            isInitialized = true;
        }
    }

    /**
     * Sets volume for all music tracks.
     * @param volume Desired volume level (0.0 to 1.0)
     */
    public static void setMusicVolume(float volume)
    {
        if (menuMusic != null) menuMusic.setVolume(volume);
        if (playingMusic != null) playingMusic.setVolume(volume);
    }

    /**
     * Sets volume for all sound effects.
     * @param volume Desired volume level (0.0 to 1.0)
     */
    public static void setSFXVolume(float volume)
    {
        if (clickSound != null) clickSound.setVolume(volume);
        if (winSound != null) winSound.setVolume(volume);
        if (loseSound != null) loseSound.setVolume(volume);
    }

    // Getter methods for accessing sound resources
    public static SFX getClickSound() { return clickSound; }
    public static SFX getWinSound() { return winSound; }
    public static SFX getLoseSound() { return loseSound; }
    public static MusicPlayer getMenuMusic() { return menuMusic; }
    public static MusicPlayer getPlayingMusic() { return playingMusic; }

    /**
     * Switches from menu music to playing music.
     * Stops menu music and starts playing music.
     */
    public static void switchToPlayingMusic()
    {
        if (menuMusic != null) {
            menuMusic.stop();
        }
        if (playingMusic != null) {
            playingMusic.resetToBeginning();
            playingMusic.play();
        }
    }

    /**
     * Switches from playing music to menu music.
     * Stops playing music and starts menu music.
     */
    public static void switchToMenuMusic()
    {
        if (playingMusic != null)
        {
            playingMusic.stop();
        }
        if (menuMusic != null)
        {
            menuMusic.resetToBeginning();
            menuMusic.play();
        }
    }

    /**
     * Stops and releases all sound resources.
     * Should be called when application is closing.
     */
    public static void cleanup()
    {
        if (clickSound != null) clickSound.stop();
        if (winSound != null) winSound.stop();
        if (loseSound != null) loseSound.stop();
        if (menuMusic != null) menuMusic.stop();
        if (playingMusic != null) playingMusic.stop();
    }
}