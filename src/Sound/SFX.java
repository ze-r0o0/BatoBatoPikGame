package Sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Manages individual sound effect playback.
 * Handles loading, volume control, and playing short audio clips.
 */
public class SFX
{
    // Audio clip for the sound effect
    private Clip clip;

    // Current volume of the sound effect
    private float volume = SoundManager.DEFAULT_SFX_VOLUME;

    // Path to the sound file
    private final String soundPath;

    /**
     * Constructs a sound effect and loads its audio file.
     * @param soundFilePath Resource path to the sound file
     */
    public SFX(String soundFilePath)
    {
        this.soundPath = soundFilePath;
        loadSound();
    }

    /**
     * Loads the sound file into a playable audio clip.
     * Handles resource loading and initial volume setup.
     */
    private void loadSound()
    {
        try {
            // Load audio resource
            InputStream audioSrc = SFX.class.getResourceAsStream(soundPath);
            if (audioSrc == null)
            {
                System.err.println("Sound file not found: " + soundPath);
                return;
            }

            // Buffer the input stream for efficient reading
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            // Create audio clip
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Set initial volume
            setVolume(volume);

        } catch (Exception e) {
            System.err.println("Error loading sound file: " + soundPath);
            e.printStackTrace();
        }
    }

    /**
     * Plays the sound effect.
     * Creates a new clip if the current one is already playing.
     */
    public void play()
    {
        if (clip != null) {
            // Handle simultaneous sound effect playback
            if (clip.isRunning()) {
                try {
                    // Create and play a new clip
                    Clip newClip = AudioSystem.getClip();
                    InputStream audioSrc = SFX.class.getResourceAsStream(soundPath);
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                    newClip.open(audioStream);
                    setVolumeForClip(newClip, volume);
                    newClip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Reset and play existing clip
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }

    /**
     * Stops the sound effect playback.
     */
    public void stop()
    {
        if (clip != null && clip.isRunning())
        {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    /**
     * Checks if the sound effect is currently playing.
     * @return true if sound is playing, false otherwise
     */
    public boolean isPlaying()
    {
        return clip != null && clip.isRunning();
    }

    /**
     * Sets the volume of the sound effect.
     * @param newVolume Volume level between 0.0 and 1.0
     */
    public void setVolume(float newVolume)
    {
        volume = Math.max(0f, Math.min(1f, newVolume));
        setVolumeForClip(clip, volume);
    }

    /**
     * Adjusts the volume of a specific audio clip.
     * Converts linear volume to decibel scale.
     * @param clipToAdjust Audio clip to modify
     * @param clipVolume Volume level to set
     */
    private void setVolumeForClip(Clip clipToAdjust, float clipVolume)
    {
        if (clipToAdjust != null) {
            FloatControl gainControl = (FloatControl) clipToAdjust.getControl(FloatControl.Type.MASTER_GAIN);
            // Convert linear volume to decibel scale
            float gain = (float) (20.0 * Math.log10(clipVolume));
            // Ensure gain is within valid range
            gain = Math.max(-80.0f, Math.min(6.0f, gain));
            gainControl.setValue(gain);
        }
    }

    /**
     * Retrieves the current volume of the sound effect.
     * @return Current volume level
     */
    public float getVolume()
    {
        return volume;
    }
}