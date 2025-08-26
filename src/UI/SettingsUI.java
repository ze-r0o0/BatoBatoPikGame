package UI;

import Sound.SoundManager;
import utils.Constants.SettingsUICons;
import utils.Constants.SettingsImagePaths;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * SettingsUI manages the audio settings interface for the application.
 * This class handles rendering and interaction with sound volume controls
 * for both SFX (sound effects) and music volumes.
 */
public class SettingsUI {
    // Image resources for buttons and background
    private BufferedImage backgroundImage;
    private BufferedImage[] sfxButtons;          // SFX button images
    private BufferedImage[] sfxButtonsHovered;   // SFX button hover state images
    private BufferedImage[] musicButtons;        // Music button images
    private BufferedImage[] musicButtonsHovered; // Music button hover state images

    // Tracking button states and interactions
    private boolean[] isSfxButtonHovered;      // Tracks which SFX buttons are being hovered
    private boolean[] isMusicButtonHovered;    // Tracks which music buttons are being hovered
    private boolean[] isSfxButtonPressed;      // Tracks which SFX buttons are pressed
    private boolean[] isMusicButtonPressed;    // Tracks which music buttons are pressed

    // Current volume states for SFX and music
    private int currentSfxState = SettingsUICons.UNMUTE_STATE;     // Default to unmuted SFX
    private int currentMusicState = SettingsUICons.FULL_VOLUME_STATE; // Default to full music volume

    // Flag to track if settings have been modified
    private boolean settingsChanged = false;

    /**
     * Constructor initializes the settings UI:
     * - Sets up image and button arrays
     * - Loads button images
     * - Initializes sound system
     */
    public SettingsUI()
    {
        initializeArrays();      // Allocate memory for button arrays
        loadImages();             // Load button and background images
        SoundManager.initializeSounds(); // Initialize sound system
    }


    /**
     * Initialize arrays to track button states and store images
     * Uses constants from SettingsUICons to define array sizes
     */
    private void initializeArrays() {
        sfxButtons = new BufferedImage[SettingsUICons.SFX_BUTTONS];
        sfxButtonsHovered = new BufferedImage[SettingsUICons.SFX_BUTTONS];
        musicButtons = new BufferedImage[SettingsUICons.MUSIC_BUTTONS];
        musicButtonsHovered = new BufferedImage[SettingsUICons.MUSIC_BUTTONS];
        isSfxButtonHovered = new boolean[SettingsUICons.SFX_BUTTONS];
        isMusicButtonHovered = new boolean[SettingsUICons.MUSIC_BUTTONS];
        isSfxButtonPressed = new boolean[SettingsUICons.SFX_BUTTONS];
        isMusicButtonPressed = new boolean[SettingsUICons.MUSIC_BUTTONS];
    }

    /**
     * Load all necessary images for the settings UI
     * Loads background and buttons for SFX and music controls
     * Handles potential image loading errors
     */
    private void loadImages() {
        try {
            // Load background
            backgroundImage = loadImage(SettingsImagePaths.SETTINGS_BACKGROUND);

            // Load SFX buttons
            sfxButtons[0] = loadImage(SettingsImagePaths.SFX_MUTE);
            sfxButtons[1] = loadImage(SettingsImagePaths.SFX_UNMUTE);
            sfxButtonsHovered[0] = loadImage(SettingsImagePaths.SFX_MUTE_HOVER);
            sfxButtonsHovered[1] = loadImage(SettingsImagePaths.SFX_UNMUTE_HOVER);

            // Load Music buttons
            musicButtons[0] = loadImage(SettingsImagePaths.MUSIC_MUTE);
            musicButtons[1] = loadImage(SettingsImagePaths.MUSIC_HALF);
            musicButtons[2] = loadImage(SettingsImagePaths.MUSIC_FULL);
            musicButtonsHovered[0] = loadImage(SettingsImagePaths.MUSIC_MUTE_HOVER);
            musicButtonsHovered[1] = loadImage(SettingsImagePaths.MUSIC_HALF_HOVER);
            musicButtonsHovered[2] = loadImage(SettingsImagePaths.MUSIC_FULL_HOVER);

        } catch (IOException e) {
            System.err.println("Error loading settings UI images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Utility method to load image resources safely
     * @param path Resource path of the image
     * @return BufferedImage loaded from the resource
     * @throws IOException If image cannot be loaded
     */
    private BufferedImage loadImage(String path) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Cannot find resource: " + path);
            }
            return ImageIO.read(is);
        }
    }

    public void handleClick(int mouseX, int mouseY, int width) {
        // Handle SFX buttons
        int startX = (width - (SettingsUICons.SFX_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.SFX_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            if (isInButton(mouseX, mouseY, x, SettingsUICons.SFX_Y_POSITION)) {
                if (currentSfxState != i) {  // Only update if the state is actually changing
                    currentSfxState = i;
                    updateSfxVolume(i);
                    settingsChanged = true;
                    playClickSound();  // Play sound after updating volume
                }
                return;
            }
        }

        // Handle Music buttons
        startX = (width - (SettingsUICons.MUSIC_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.MUSIC_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            if (isInButton(mouseX, mouseY, x, SettingsUICons.MUSIC_Y_POSITION)) {
                if (currentMusicState != i) {  // Only update if the state is actually changing
                    currentMusicState = i;
                    updateMusicVolume(i);
                    settingsChanged = true;
                    playClickSound();  // Play sound after updating volume
                }
                return;
            }
        }
    }

    private void playClickSound() {
        if (SoundManager.getClickSound() != null && currentSfxState != SettingsUICons.MUTE_STATE) {
            try {
                SoundManager.getClickSound().play();
            } catch (Exception e) {
                System.err.println("Error playing click sound: " + e.getMessage());
            }
        }
    }

    public void handlePress(int mouseX, int mouseY, int width) {
        // Handle SFX button presses
        int startX = (width - (SettingsUICons.SFX_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.SFX_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            if (isInButton(mouseX, mouseY, x, SettingsUICons.SFX_Y_POSITION)) {
                isSfxButtonPressed[i] = true;
                return;
            }
        }

        // Handle Music button presses
        startX = (width - (SettingsUICons.MUSIC_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.MUSIC_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            if (isInButton(mouseX, mouseY, x, SettingsUICons.MUSIC_Y_POSITION)) {
                isMusicButtonPressed[i] = true;
                return;
            }
        }
    }

    public void handleRelease(int mouseX, int mouseY, int width) {
        // Reset all pressed states
        for (int i = 0; i < SettingsUICons.SFX_BUTTONS; i++) {
            isSfxButtonPressed[i] = false;
        }
        for (int i = 0; i < SettingsUICons.MUSIC_BUTTONS; i++) {
            isMusicButtonPressed[i] = false;
        }

        // Handle the actual click if the mouse is still over a button
        handleClick(mouseX, mouseY, width);
    }

    private boolean isInButton(int mouseX, int mouseY, int buttonX, int buttonY) {
        return mouseX >= buttonX && mouseX <= buttonX + SettingsUICons.BUTTON_WIDTH &&
                mouseY >= buttonY && mouseY <= buttonY + SettingsUICons.BUTTON_HEIGHT;
    }

    private void updateSfxVolume(int state) {
        if (state != -1) {
            switch (state) {
                case SettingsUICons.MUTE_STATE:
                    SoundManager.setSFXVolume(SettingsUICons.SFX_MUTE_VOLUME);
                    break;
                case SettingsUICons.UNMUTE_STATE:
                    SoundManager.setSFXVolume(SettingsUICons.SFX_FULL_VOLUME);
                    break;
            }
        }
    }

    private void updateMusicVolume(int state) {
        if (state != -1) {
            switch (state) {
                case SettingsUICons.MUTE_STATE:
                    SoundManager.setMusicVolume(SettingsUICons.MUSIC_MUTE_VOLUME);
                    break;
                case SettingsUICons.HALF_VOLUME_STATE:
                    SoundManager.setMusicVolume(SettingsUICons.MUSIC_HALF_VOLUME);
                    break;
                case SettingsUICons.FULL_VOLUME_STATE:
                    SoundManager.setMusicVolume(SettingsUICons.MUSIC_FULL_VOLUME);
                    break;
            }
        }
    }

    public void updateHoverState(int mouseX, int mouseY, int width) {
        // Update SFX button hover states
        int startX = (width - (SettingsUICons.SFX_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.SFX_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            isSfxButtonHovered[i] = isInButton(mouseX, mouseY, x, SettingsUICons.SFX_Y_POSITION);
        }

        // Update Music button hover states
        startX = (width - (SettingsUICons.MUSIC_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.MUSIC_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            isMusicButtonHovered[i] = isInButton(mouseX, mouseY, x, SettingsUICons.MUSIC_Y_POSITION);
        }
    }

    public void clearHoverStates() {
        for (int i = 0; i < SettingsUICons.SFX_BUTTONS; i++) {
            isSfxButtonHovered[i] = false;
        }
        for (int i = 0; i < SettingsUICons.MUSIC_BUTTONS; i++) {
            isMusicButtonHovered[i] = false;
        }
    }

    public void draw(Graphics g, int width, int height)
    {
        // Draw background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        }

        // Draw SFX buttons
        int startX = (width - (SettingsUICons.SFX_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.SFX_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            BufferedImage buttonToDraw;
            if (i == currentSfxState) {
                buttonToDraw = isSfxButtonHovered[i] ? sfxButtonsHovered[i] : sfxButtons[i];
            } else {
                buttonToDraw = sfxButtons[i];
            }
            if (buttonToDraw != null) {
                g.drawImage(buttonToDraw, x, SettingsUICons.SFX_Y_POSITION,
                        SettingsUICons.BUTTON_WIDTH, SettingsUICons.BUTTON_HEIGHT, null);
            }
        }

        // Draw Music buttons
        startX = (width - (SettingsUICons.MUSIC_BUTTONS * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING))) / 2;
        for (int i = 0; i < SettingsUICons.MUSIC_BUTTONS; i++) {
            int x = startX + (i * (SettingsUICons.BUTTON_WIDTH + SettingsUICons.BUTTON_SPACING));
            BufferedImage buttonToDraw;
            if (i == currentMusicState) {
                buttonToDraw = isMusicButtonHovered[i] ? musicButtonsHovered[i] : musicButtons[i];
            } else {
                buttonToDraw = musicButtons[i];
            }
            if (buttonToDraw != null) {
                g.drawImage(buttonToDraw, x, SettingsUICons.MUSIC_Y_POSITION,
                        SettingsUICons.BUTTON_WIDTH, SettingsUICons.BUTTON_HEIGHT, null);
            }
        }
        // Draw "Press ESC to go back" text
        g.setColor(Color.WHITE);  // Choose a color that stands out against the background
        g.setFont(new Font("Arial", Font.BOLD, 20));  // Set font, style, and size
        g.drawString("Press ESC to go back.", 20, 40);  // Positioned 20 pixels from left, 40 pixels from top

        g.drawString("Credits::", 20, 480);
        g.drawString("VI - BYTES", 20, 500);
        g.drawString("Caluza, Nash Francis M", 20, 517);
        g.drawString("Mapanao, Jan Emmerson R.", 20, 535);
        g.drawString("Sanez, Gian Cristopher M.", 20, 553);
        g.drawString("Ferrer, Alex A.", 20, 572);
        g.drawString("Ureta, Donato Jr. G", 20, 590);
        g.drawString("Escanilla, Mark Fermin A.", 20, 605);
    }

    public boolean hasSettingsChanged() {
        return settingsChanged;
    }

    public void resetSettingsChanged() {
        settingsChanged = false;
    }
}