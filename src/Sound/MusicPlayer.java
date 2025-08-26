package Sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Manages music playback with advanced control features.
 * Supports looping, volume control, and track switching.
 */
public class MusicPlayer
{
    // Audio clip for music playback
    private Clip clip;

    // Currently loaded music track path
    private String currentTrack;

    // Flag to determine if music should loop
    private boolean isLooping;

    // Current volume of the music
    private float volume = SoundManager.DEFAULT_MUSIC_VOLUME;

    /**
     * Constructs a music player for a specific track.
     * @param musicFilePath Resource path to the music file
     * @param loop Whether the music should loop continuously
     */
    public MusicPlayer(String musicFilePath, boolean loop)
    {
        currentTrack = musicFilePath;
        isLooping = loop;
        loadMusic(musicFilePath);

        // Start playing if looping is enabled
        if (isLooping)
        {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            play();
        }
    }

    /**
     * Loads a music file into an audio clip.
     * @param musicFilePath Resource path to the music file
     */
    private void loadMusic(String musicFilePath)
    {
        try
        {
            // Load audio resource
            InputStream audioSrc = MusicPlayer.class.getResourceAsStream(musicFilePath);
            if (audioSrc == null)
            {
                System.err.println("Music file not found: " + musicFilePath);
                return;
            }

            // Buffer the input stream
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            // Create audio clip
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Set initial volume
            setVolume(volume);
        } catch (Exception e) {
            System.err.println("Error loading music file: " + musicFilePath);
            e.printStackTrace();
        }
    }

    /**
     * Sets the volume of the music.
     * @param newVolume Volume level between 0.0 and 1.0
     */
    public void setVolume(float newVolume)
    {
        volume = Math.max(0f, Math.min(1f, newVolume));
        if (clip != null)
        {
            try
            {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                // Convert linear volume to decibel scale
                float gain = (float) (20.0 * Math.log10(volume));
                gain = Math.max(-80.0f, Math.min(6.0f, gain));
                gainControl.setValue(gain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts or resumes music playback.
     * Ensures looping is set if enabled.
     */
    public void play() {
        if (clip != null && !clip.isRunning())
        {
            clip.setFramePosition(0);
            if (isLooping)
            {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        }
    }

    /**
     * Stops music playback and resets to beginning.
     */
    public void stop()
    {
        if (clip != null)
        {
            clip.stop();
            clip.setFramePosition(0);
            clip.flush();  // Clear any buffered audio
        }
    }

    /**
     * Pauses music playback.
     */
    public void pause()
    {
        if (clip != null)
        {
            clip.stop();
        }
    }

    /**
     * Resumes music playback from current position.
     */
    public void resume()
    {
        if (clip != null)
        {
            clip.start();
        }
    }

    /**
     * Checks if music is currently playing.
     * @return true if music is playing, false otherwise
     */
    public boolean isPlaying()
    {
        return clip != null && clip.isRunning();
    }

    /**
     * Resets playback position to the start of the track.
     */
    public void resetToBeginning()
    {
        if (clip != null) {
            clip.setFramePosition(0);
        }
    }

    /**
     * Changes the current music track.
     * @param newMusicFilePath Resource path to the new music file
     * @param loop Whether the new track should loop continuously
     */
    public void changeTrack(String newMusicFilePath, boolean loop)
    {
        if (clip != null)
        {
            clip.stop();
            clip.close();
        }
        isLooping = loop;
        loadMusic(newMusicFilePath);
        play();
    }

    /**
     * Retrieves the current music volume.
     * @return Current volume level
     */
    public float getVolume()
    {
        return volume;
    }
}