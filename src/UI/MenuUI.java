package UI;

import Sound.SoundManager;
import utils.Constants.menuUICons;
import utils.Constants.menuButtons;
import utils.Constants.ImagePaths;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents the UI for the game's main menu.
 * Handles loading and rendering images, as well as managing button states.
 */
public class MenuUI
{
    private BufferedImage backgroundImage, titleImage; // Background and title images
    private BufferedImage[] buttonImages;             // Images for default button states
    private BufferedImage[] buttonPressedImages;      // Images for pressed button states
    private boolean[] isButtonPressed;               // Tracks whether each button is pressed
    private boolean[] isButtonHovered;               // Tracks whether each button is hovere

    /**
     * Constructor to initialize the menu UI.
     * Loads the button states, images, and sets the menu music.
     */
    public MenuUI()
    {
        initButtons();
        loadImages();
        SoundManager.switchToMenuMusic();
    }

    /**
     * Initializes button states and allocates memory for button-related arrays.
     */
    private void initButtons()
    {
        buttonImages = new BufferedImage[menuButtons.TOTAL_MENU_BUTTONS];
        buttonPressedImages = new BufferedImage[menuButtons.TOTAL_MENU_BUTTONS];
        isButtonPressed = new boolean[menuButtons.TOTAL_MENU_BUTTONS];
        isButtonHovered = new boolean[menuButtons.TOTAL_MENU_BUTTONS];
    }

    /**
     * Loads all required images for the menu UI, including buttons, title, and background.
     */
    private void loadImages()
    {
        try {
            // Load background and title
            backgroundImage = loadImage(ImagePaths.MENU_BACKGROUND);
            titleImage = loadImage(ImagePaths.MENU_TITLE);

            // Load button images
            buttonImages[menuButtons.PLAY] = loadImage(ImagePaths.PLAY_BUTTON);
            buttonImages[menuButtons.SETTINGS] = loadImage(ImagePaths.SETTINGS_BUTTON);
            buttonImages[menuButtons.QUIT] = loadImage(ImagePaths.QUIT_BUTTON);

            // Load pressed button images
            buttonPressedImages[menuButtons.PLAY] = loadImage(ImagePaths.PLAY_CLICKED);
            buttonPressedImages[menuButtons.SETTINGS] = loadImage(ImagePaths.SETTINGS_CLICKED);
            buttonPressedImages[menuButtons.QUIT] = loadImage(ImagePaths.QUIT_CLICKED);

        } catch (IOException e) {
            System.out.println("Error loading menu UI images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Utility method to load an image from a file path.
     *
     * @param path The file path of the image
     * @return The loaded BufferedImage
     * @throws IOException If the image cannot be loaded
     */
    private BufferedImage loadImage(String path) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                System.out.println("Failed to load " + path);
                return null;
            }
            return ImageIO.read(is);
        }
    }

    /**
     * Updates the menu UI. Placeholder for future animations or logic.
     */
    public void update()
    {
        // Add any animation updates here
    }

    /**
     * Draws the menu UI, including the background, title, and buttons.
     *
     * @param g      Graphics object for rendering
     * @param width  Width of the rendering area
     * @param height Height of the rendering area
     */
    public void draw(Graphics g, int width, int height) {
        drawBackground(g, width, height);
        drawTitle(g, width);
        drawButtons(g, width);

        // Draw Text
        g.setColor(Color.WHITE);  // Choose a color that stands out against the background
        g.setFont(new Font("Arial", Font.BOLD, 15));  // Set font, style, and size
        g.drawString("Bato Bato Pik Game v1.0", 20, 400);
        g.drawString("By VI-BYTES", 20, 425);
        g.drawString("Controls:", 20, 480);
        g.drawString("Q - Bato", 20, 500);
        g.drawString("W - Papel", 20, 517);
        g.drawString("E - Gunting", 20, 535);
    }

    /**
     * Draws the background image.
     */
    private void drawBackground(Graphics g, int width, int height)
    {
        if (backgroundImage != null)
        {
            g.drawImage(backgroundImage, 0, 0,
                    width + menuUICons.BACKGROUND_PADDING,
                    height + menuUICons.BACKGROUND_PADDING, null);
        }
    }

    /**
     * Draws the title image.
     */
    private void drawTitle(Graphics g, int width)
    {
        if (titleImage != null)
        {
            int titleX = (width - menuUICons.TITLE_WIDTH) / 2;
            g.drawImage(titleImage, titleX, 0,
                    menuUICons.TITLE_WIDTH,
                    menuUICons.TITLE_HEIGHT, null);
        }
    }

    /**
     * Draws all menu buttons, considering hover and press states.
     */
    private void drawButtons(Graphics g, int width)
    {
        int buttonX = (width - menuUICons.BUTTON_WIDTH) / 2;

        for (int i = 0; i < menuButtons.TOTAL_MENU_BUTTONS; i++)
        {
            int buttonY = menuUICons.BUTTON_Y +
                    (menuUICons.BUTTON_HEIGHT + menuUICons.BUTTON_SPACING) * i;

            BufferedImage buttonImage = isButtonHovered[i] ?
                    buttonPressedImages[i] :
                    buttonImages[i];

            if (buttonImage != null)
            {
                g.drawImage(buttonImage, buttonX, buttonY,
                        menuUICons.BUTTON_WIDTH,
                        menuUICons.BUTTON_HEIGHT, null);
            }
        }
    }

    /**
     * Determines which button, if any, was clicked.
     *
     * @param mouseX X-coordinate of the mouse click
     * @param mouseY Y-coordinate of the mouse click
     * @param width  Width of the rendering area
     * @return The index of the clicked button, or -1 if no button was clicked
     */
    public int getButtonClicked(int mouseX, int mouseY, int width)
    {
        int buttonX = (width - menuUICons.BUTTON_WIDTH) / 2;

        for (int i = 0; i < menuButtons.TOTAL_MENU_BUTTONS; i++)
        {
            int buttonY = menuUICons.BUTTON_Y +
                    (menuUICons.BUTTON_HEIGHT + menuUICons.BUTTON_SPACING) * i;

            if (isMouseOverButton(mouseX, mouseY, buttonX, buttonY))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the mouse is over a specific button.
     *
     * @param mouseX X-coordinate of the mouse
     * @param mouseY Y-coordinate of the mouse
     * @param buttonX X-coordinate of the button
     * @param buttonY Y-coordinate of the button
     * @return True if the mouse is over the button, false otherwise
     */
    private boolean isMouseOverButton(int mouseX, int mouseY, int buttonX, int buttonY)
    {
        return mouseX >= buttonX &&
                mouseX <= buttonX + menuUICons.BUTTON_WIDTH &&
                mouseY >= buttonY &&
                mouseY <= buttonY + menuUICons.BUTTON_HEIGHT;
    }

    /**
     * Sets the pressed state for a specific button.
     *
     * @param buttonIndex Index of the button
     * @param pressed     True if pressed, false otherwise
     */
    public void setButtonPressed(int buttonIndex, boolean pressed)
    {
        if (buttonIndex >= 0 && buttonIndex < menuButtons.TOTAL_MENU_BUTTONS)
        {

            isButtonPressed[buttonIndex] = pressed;
        }
    }

    /**
     * Sets the hover state for a specific button.
     *
     * @param buttonIndex Index of the button
     * @param hovered     True if hovered, false otherwise
     */
    public void setButtonHovered(int buttonIndex, boolean hovered) {
        if (buttonIndex >= 0 && buttonIndex < menuButtons.TOTAL_MENU_BUTTONS) {
            isButtonHovered[buttonIndex] = hovered;
            System.out.println("Button " + buttonIndex + " hover state: " + hovered); // Debug
        }
    }
}