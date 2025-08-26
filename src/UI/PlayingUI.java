package UI;

import Sound.SoundManager;
import utils.Constants.playingUICons;
import utils.Constants.ImagePaths;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * The PlayingUI class handles the rendering and management of the game's user interface
 * during the gameplay state, including buttons, background, hands, lives, and pause menu.
 */
public class PlayingUI
{
    // Declare instance variables for images and game state
    private BufferedImage playingBG;
    private BufferedImage[] buttonImages; // Regular button images
    private BufferedImage[] buttonClickedImages; // Hovered/clicked button images
    private BufferedImage[] handImages; // Player hand images
    private BufferedImage[] compHandImages; // Computer hand images
    private BufferedImage heartImage, emptyHeartImage; // Heart indicators for lives
    private boolean[] isButtonHovered; // Tracks hover state of buttons
    private int currentHand, computerHand; // Current choices of player and computer
    private boolean hasPlayerMadeChoice; // Tracks if player has made a choice
    private String resultText; // Result message for each round
    private Random random; // RNG for computer's choice
    private long resultDisplayStartTime; // Time when the result is displayed

    // Game state variables
    private int playerLives, computerLives; // Lives for player and computer
    private boolean gameOver; // Tracks if the game has ended

    // Pause menu images and state
    private BufferedImage pauseButton;
    private BufferedImage pauseButtonHovered;
    private BufferedImage pauseOverlay;
    private BufferedImage resumeButton;
    private BufferedImage resumeButtonHovered;
    private BufferedImage retryButton;
    private BufferedImage retryButtonHovered;
    private BufferedImage menuButton;
    private BufferedImage menuButtonHovered;
    private boolean isPaused;
    private boolean isPauseButtonHovered;
    private boolean isResumeButtonHovered;
    private boolean isRetryButtonHovered;
    private boolean isMenuButtonHovered;

    /**
     * Constructor initializes the UI components and game state.
     */
    public PlayingUI()
    {
        initButtons(); // Initialize button-related arrays
        loadImages(); // Load required images
        currentHand = playingUICons.ROCK; // Default hand choice
        computerHand = playingUICons.ROCK; // Default computer hand choice
        random = new Random(); // RNG for computer's hand
        hasPlayerMadeChoice = false;
        resultText = "";
        playerLives = playingUICons.TOTAL_HEARTS;
        computerLives = playingUICons.TOTAL_HEARTS;
        gameOver = false;
        isPaused = false;
    }

    /**
     * Initializes the arrays for storing button and hand images.
     */
    private void initButtons()
    {
        buttonImages = new BufferedImage[playingUICons.TOTAL_RPS_BUTTONS];
        buttonClickedImages = new BufferedImage[playingUICons.TOTAL_RPS_BUTTONS];
        handImages = new BufferedImage[playingUICons.TOTAL_RPS_BUTTONS];
        compHandImages = new BufferedImage[playingUICons.TOTAL_RPS_BUTTONS];
        isButtonHovered = new boolean[playingUICons.TOTAL_RPS_BUTTONS];
    }

    /**
     * Loads images from file paths defined in the constants.
     */
    private void loadImages() {
        try {
            // Load background
            playingBG = loadImage(ImagePaths.PLAYING_BG);

            // Load heart images
            heartImage = loadImage(ImagePaths.HEART);
            emptyHeartImage = loadImage(ImagePaths.EMPTY_HEART);

            // Load RPS button images
            buttonImages[playingUICons.ROCK] = loadImage(ImagePaths.ROCK_BUTTON);
            buttonImages[playingUICons.PAPER] = loadImage(ImagePaths.PAPER_BUTTON);
            buttonImages[playingUICons.SCISSORS] = loadImage(ImagePaths.SCISSORS_BUTTON);

            // Load clicked versions
            buttonClickedImages[playingUICons.ROCK] = loadImage(ImagePaths.ROCK_CLICKED);
            buttonClickedImages[playingUICons.PAPER] = loadImage(ImagePaths.PAPER_CLICKED);
            buttonClickedImages[playingUICons.SCISSORS] = loadImage(ImagePaths.SCISSORS_CLICKED);

            // Load player hand images
            handImages[playingUICons.ROCK] = loadImage(ImagePaths.ROCK_HAND);
            handImages[playingUICons.PAPER] = loadImage(ImagePaths.PAPER_HAND);
            handImages[playingUICons.SCISSORS] = loadImage(ImagePaths.SCISSORS_HAND);

            // Load computer hand images
            compHandImages[playingUICons.ROCK] = loadImage(ImagePaths.ROCK_COMP);
            compHandImages[playingUICons.PAPER] = loadImage(ImagePaths.PAPER_COMP);
            compHandImages[playingUICons.SCISSORS] = loadImage(ImagePaths.SCISSORS_COMP);

            // Load pause-related images
            pauseButton = loadImage(ImagePaths.PAUSE_BUTTON);
            pauseButtonHovered = loadImage(ImagePaths.PAUSE_BUTTON_HOVER);
            pauseOverlay = loadImage(ImagePaths.PAUSE_OVERLAY);
            resumeButton = loadImage(ImagePaths.RESUME_BUTTON);
            resumeButtonHovered = loadImage(ImagePaths.RESUME_BUTTON_HOVER);
            retryButton = loadImage(ImagePaths.RETRY_BUTTON);
            retryButtonHovered = loadImage(ImagePaths.RETRY_BUTTON_HOVER);
            menuButton = loadImage(ImagePaths.MENU_BUTTON);
            menuButtonHovered = loadImage(ImagePaths.MENU_BUTTON_HOVER);


        } catch (IOException e) {
            System.out.println("Error loading playing UI images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads an image from the specified file path.
     *
     * @param path The file path of the image.
     * @return The loaded BufferedImage.
     * @throws IOException If the image cannot be loaded.
     */
    private BufferedImage loadImage(String path) throws IOException
    {
        try (InputStream is = getClass().getResourceAsStream(path))
        {
            if (is == null)
            {
                System.out.println("Failed to load " + path);
                return null;
            }
            return ImageIO.read(is);
        }
    }

    /**
     * Draws the entire user interface on the screen.
     */
    public void draw(Graphics g, int width, int height)
    {
        drawBackground(g, width, height); // Draw the background
        drawHearts(g, width); // Draw the hearts representing lives
        drawHand(g); // Draw the player's hand
        drawComputerHand(g, width); // Draw the computer's hand
        drawButtons(g, width, height); // Draw the buttons for Rock, Paper, Scissors
        drawResult(g, width); // Draw the result of the current round
        drawPauseButton(g); // Draw the pause button

        // If the game is paused, display the pause overlay and menu buttons
        if (isPaused)
        {
            drawPauseOverlay(g, width, height);
        }
        // Draw Text
        g.setColor(Color.WHITE);  // Choose a color that stands out against the background
        g.setFont(new Font("Arial", Font.BOLD, 15));  // Set font, style, and size
        g.drawString("Controls:", 20, 380);
        g.drawString("Q - Bato", 20, 400);
        g.drawString("W - Papel", 20, 417);
        g.drawString("E - Gunting", 20, 435);
    }
    //Playing
    /**
     * Draws the heart images that represent the player's and computer's lives.
     */
    private void drawHearts(Graphics g, int width) {
        // Draw player hearts (left side) - moved right to accommodate pause button
        for (int i = 0; i < playingUICons.TOTAL_HEARTS; i++)
        {
            BufferedImage img = (i < playerLives) ? heartImage : emptyHeartImage;
            if (img != null)
            {
                g.drawImage(img, playingUICons.PAUSE_BUTTON_SIZE + playingUICons.HEART_SPACING * 2 +
                                (i * (playingUICons.HEART_SIZE + playingUICons.HEART_SPACING)),
                                playingUICons.HEART_Y_POSITION,
                                playingUICons.HEART_SIZE,
                                playingUICons.HEART_SIZE,
                        null);
            }
        }

        // Draw hearts for the computer's lives (right side of the screen)
        for (int i = 0; i < playingUICons.TOTAL_HEARTS; i++)
        {
            BufferedImage img = (i < computerLives) ? heartImage : emptyHeartImage;
            if (img != null)
            {
                g.drawImage(img,
                        width - ((playingUICons.TOTAL_HEARTS - i) * (playingUICons.HEART_SIZE + playingUICons.HEART_SPACING)),
                        playingUICons.HEART_Y_POSITION,
                        playingUICons.HEART_SIZE,
                        playingUICons.HEART_SIZE,
                        null);
            }
        }
    }


    /**
     * Draws the Background for The Playing State.
     */
    private void drawBackground(Graphics g, int width, int height)
    {
        if (playingBG != null) {
            g.drawImage(playingBG, 0, 0, width, height, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, width, height);
        }
    }

    /**
     * Draws the player's hand on the screen.
     */
    private void drawHand(Graphics g) {
        BufferedImage handImage = handImages[currentHand];
        if (handImage != null)
        {

            g.drawImage(handImage,
                    playingUICons.HAND_X,
                    playingUICons.HAND_Y,
                    playingUICons.HAND_WIDTH,
                    playingUICons.HAND_HEIGHT,
                    null);
        }
    }

    /**
     * Draws the computer's hand on the screen.
     */
    private void drawComputerHand(Graphics g, int width)
    {
        BufferedImage compHandImage = compHandImages[computerHand];
        if (compHandImage != null)
        {
            g.drawImage(compHandImage,
                    width - playingUICons.HAND_WIDTH - playingUICons.HAND_X,
                    playingUICons.HAND_Y,
                    playingUICons.HAND_WIDTH,
                    playingUICons.HAND_HEIGHT,
                    null);
        }
    }

    /**
     * Draws the buttons for selecting Rock, Paper, or Scissors.
     */
    private void drawButtons(Graphics g, int width, int height)
    {
        // Calculate the center position for the middle (paper) button
        int centerX = width / 2 - playingUICons.BUTTON_WIDTH / 2;
        int buttonY = height - playingUICons.BUTTON_Y_FROM_BOTTOM - playingUICons.BUTTON_HEIGHT;

        // Draw rock (left of paper)
        drawButton(g, playingUICons.ROCK,
                centerX - playingUICons.BUTTON_WIDTH - playingUICons.BUTTON_SPACING,
                buttonY);

        // Draw paper (center)
        drawButton(g, playingUICons.PAPER,
                centerX,
                buttonY);

        // Draw scissors (right of paper)
        drawButton(g, playingUICons.SCISSORS,
                centerX + playingUICons.BUTTON_WIDTH + playingUICons.BUTTON_SPACING,
                buttonY);
    }
    /**
     * Draws the Hovered buttons for selecting Rock, Paper, or Scissors.
     */
    private void drawButton(Graphics g, int buttonIndex, int x, int y)
    {
        BufferedImage buttonImage = isButtonHovered[buttonIndex] ?
                buttonClickedImages[buttonIndex] :
                buttonImages[buttonIndex];

        if (buttonImage != null) {
            g.drawImage(buttonImage, x, y,
                    playingUICons.BUTTON_WIDTH,
                    playingUICons.BUTTON_HEIGHT, null);
        }
    }

    /**
     * Draws the result of the current round (Win, Lose, Tie).
     */
    private void drawResult(Graphics g, int width) {
        if (hasPlayerMadeChoice && !resultText.isEmpty())
        {
            // Draw the result text
            g.setFont(new Font("Arial", Font.BOLD, playingUICons.RESULT_TEXT_SIZE));
            g.setColor(Color.WHITE);
            FontMetrics metrics = g.getFontMetrics();
            int x = (width - metrics.stringWidth(resultText)) / 2;
            g.drawString(resultText, x, playingUICons.RESULT_TEXT_Y_POSITION);

            // Draw continuation message if not game over
            long currentTime = System.currentTimeMillis();
            if (currentTime - resultDisplayStartTime > playingUICons.RESULT_DISPLAY_DURATION)
            {
                g.setFont(new Font("Arial", Font.PLAIN, playingUICons.CONTINUE_TEXT_SIZE));
                String continueText = gameOver ? "Press ESC to return to pause or Press Enter / Click anywhere to continue" : "Press Enter / Click anywhere to continue";
                metrics = g.getFontMetrics();
                x = (width - metrics.stringWidth(continueText)) / 2;
                g.drawString(continueText, x, playingUICons.CONTINUE_TEXT_Y_POSITION);
            }
        }
    }
    /**
     * Determines the winner of the game based on the player's and computer's choices. Updates the result and lives.
     */
    private void determineWinner()
    {
        if (currentHand == computerHand)
        {
            resultText = "It's a Tie!";
        } else if ((currentHand == playingUICons.ROCK && computerHand == playingUICons.SCISSORS) ||
                (currentHand == playingUICons.PAPER && computerHand == playingUICons.ROCK) ||
                (currentHand == playingUICons.SCISSORS && computerHand == playingUICons.PAPER))
        {
            resultText = "You Win This Round!";
            computerLives--;
            if (computerLives <= 0)
            {
                resultText = "Game Over - You Win!";
                SoundManager.getWinSound().play();
                gameOver = true;
            }
        } else {
            resultText = "Computer Wins This Round!";
            playerLives--;
            if (playerLives <= 0)
            {
                resultText = "Game Over - Computer Wins!";
                SoundManager.getLoseSound().play();
                gameOver = true;
            }
        }
    }

    /**
     * Determines which button (Rock, Paper, or Scissors) the user clicked based on mouse position.
     *
     * @param mouseX The x-coordinate of the mouse click.
     * @param mouseY The y-coordinate of the mouse click.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @return The index of the clicked button (Rock, Paper, Scissors).
     */
    public int getButtonClicked(int mouseX, int mouseY, int width, int height)
        {
        int centerX = width / 2 - playingUICons.BUTTON_WIDTH / 2;
        int buttonY = height - playingUICons.BUTTON_Y_FROM_BOTTOM - playingUICons.BUTTON_HEIGHT;

        // Check rock button
        if (isMouseOverButton(mouseX, mouseY, centerX - playingUICons.BUTTON_WIDTH - playingUICons.BUTTON_SPACING, buttonY))
        {
            return playingUICons.ROCK;
        }

        // Check paper button
        if (isMouseOverButton(mouseX, mouseY, centerX, buttonY))
        {
            return playingUICons.PAPER;
        }

        // Check scissors button
        if (isMouseOverButton(mouseX, mouseY, centerX + playingUICons.BUTTON_WIDTH + playingUICons.BUTTON_SPACING, buttonY))
        {
            return playingUICons.SCISSORS;
        }

        return -1;
    }

    /**
     * Determines if the mouse is over a specific button based on the mouse position and the button's coordinates.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param buttonX The x-coordinate of the top-left corner of the button.
     * @param buttonY The y-coordinate of the top-left corner of the button.
     * @return true if the mouse is over the button, false otherwise.
     */
    private boolean isMouseOverButton(int mouseX, int mouseY, int buttonX, int buttonY) {
        return mouseX >= buttonX &&
                mouseX <= buttonX + playingUICons.BUTTON_WIDTH &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.BUTTON_HEIGHT;
    }

    /**
     * Updates the hover state for a specific button (Rock, Paper, or Scissors).
     *
     * @param buttonIndex The index of the button (Rock, Paper, or Scissors).
     * @param hovered A boolean indicating whether the button is hovered over (true) or not (false).
     */
    public void setButtonHovered(int buttonIndex, boolean hovered) {
        if (buttonIndex >= 0 && buttonIndex < playingUICons.TOTAL_RPS_BUTTONS) {
            isButtonHovered[buttonIndex] = hovered;
        }

    }

    /**
     * Checks if the game can be reset, which happens if the player hasn't made a choice yet or if the result display time has expired.
     *
     * @return true if the game can be reset, false otherwise.
     */
    public boolean canResetGame()
    {
        return !hasPlayerMadeChoice ||
                System.currentTimeMillis() - resultDisplayStartTime > playingUICons.RESULT_DISPLAY_DURATION;
    }

    /**
     * Sets the player's current hand choice (Rock, Paper, or Scissors) if they haven't already made a choice.
     *
     * @param handIndex The index of the selected hand (Rock, Paper, or Scissors).
     */
    public void setCurrentHand(int handIndex) {
        if (!hasPlayerMadeChoice && handIndex >= 0 && handIndex < playingUICons.TOTAL_RPS_BUTTONS) {
            currentHand = handIndex;
            makeChoice(handIndex);
        }
    }

    /**
     * Processes the player's choice and determines the computer's choice. Then, it triggers the result display.
     *
     * @param playerChoice The index of the player's selected hand (Rock, Paper, or Scissors).
     */
    private void makeChoice(int playerChoice)
    {
        currentHand = playerChoice;
        computerHand = random.nextInt(playingUICons.TOTAL_RPS_BUTTONS);
        hasPlayerMadeChoice = true;
        resultDisplayStartTime = System.currentTimeMillis();
        determineWinner();
    }

    //Paused
    /**
     * Draws the pause button.
     */
    private void drawPauseButton(Graphics g)
    {
        BufferedImage buttonImg = isPauseButtonHovered ? pauseButtonHovered : pauseButton;
        if (buttonImg != null) {
            g.drawImage(buttonImg,
                    playingUICons.PAUSE_BUTTON_X,
                    playingUICons.PAUSE_BUTTON_Y,
                    playingUICons.PAUSE_BUTTON_SIZE,
                    playingUICons.PAUSE_BUTTON_SIZE,
                    null);
        }
    }
    /**
     * Draws the pause overlay and menu buttons (Resume, Retry, Menu).
     */
    private void drawPauseOverlay(Graphics g, int width, int height) {
        // Draw semi-transparent overlay
        if (pauseOverlay != null) {
            Graphics2D g2d = (Graphics2D) g;
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f)); // Make overlay more transparent
            g.drawImage(pauseOverlay, 0, 0, width, height, null);
            g2d.setComposite(originalComposite);
        } else {
            g.setColor(new Color(0, 0, 0, 180)); // More transparent fallback color
            g.fillRect(0, 0, width, height);
        }

        // Calculate center positions for buttons (similar to RPS buttons)
        int centerX = width / 2 - playingUICons.PAUSE_MENU_BUTTON_WIDTH / 2;
        int buttonY = height / 2 - playingUICons.PAUSE_MENU_BUTTON_HEIGHT / 2;

        // Draw retry button (left)
        BufferedImage retryImg = isRetryButtonHovered ? retryButtonHovered : retryButton;
        if (retryImg != null)
        {
            g.drawImage(retryImg,
                    centerX - playingUICons.PAUSE_MENU_BUTTON_WIDTH - playingUICons.PAUSE_MENU_SPACING,
                    buttonY,
                    playingUICons.PAUSE_MENU_BUTTON_WIDTH,
                    playingUICons.PAUSE_MENU_BUTTON_HEIGHT,
                    null);
        }

        // Draw menu button (center)
        BufferedImage menuImg = isMenuButtonHovered ? menuButtonHovered : menuButton;
        if (menuImg != null)
        {
            g.drawImage(menuImg,
                    centerX,
                    buttonY,
                    playingUICons.PAUSE_MENU_BUTTON_WIDTH,
                    playingUICons.PAUSE_MENU_BUTTON_HEIGHT,
                    null);
        }

        // Draw resume button (right)
        BufferedImage resumeImg = isResumeButtonHovered ? resumeButtonHovered : resumeButton;
        if (resumeImg != null)
        {
            g.drawImage(resumeImg,
                    centerX + playingUICons.PAUSE_MENU_BUTTON_WIDTH + playingUICons.PAUSE_MENU_SPACING,
                    buttonY,
                    playingUICons.PAUSE_MENU_BUTTON_WIDTH,
                    playingUICons.PAUSE_MENU_BUTTON_HEIGHT,
                    null);
        }
    }

    /**
     * Checks if the mouse is hovering over the pause button.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     */
    public void checkPauseButtonHover(int mouseX, int mouseY)
    {
        isPauseButtonHovered = mouseX >= playingUICons.PAUSE_BUTTON_X &&
                mouseX <= playingUICons.PAUSE_BUTTON_X + playingUICons.PAUSE_BUTTON_SIZE &&
                mouseY >= playingUICons.PAUSE_BUTTON_Y &&
                mouseY <= playingUICons.PAUSE_BUTTON_Y + playingUICons.PAUSE_BUTTON_SIZE;
    }

    /**
     * Checks if the mouse is hovering over any of the buttons in the pause menu (retry, menu, resume).
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param width The width of the game window.
     * @param height The height of the game window.
     */
    public void checkPauseMenuHover(int mouseX, int mouseY, int width, int height)
    {
        if (!isPaused) return;

        int centerX = width / 2 - playingUICons.PAUSE_MENU_BUTTON_WIDTH / 2;
        int buttonY = height / 2 - playingUICons.PAUSE_MENU_BUTTON_HEIGHT / 2;

        // Check retry button (left)
        isRetryButtonHovered = mouseX >= centerX - playingUICons.PAUSE_MENU_BUTTON_WIDTH - playingUICons.PAUSE_MENU_SPACING &&
                mouseX <= centerX - playingUICons.PAUSE_MENU_SPACING &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.PAUSE_MENU_BUTTON_HEIGHT;

        // Check menu button (center)
        isMenuButtonHovered = mouseX >= centerX &&
                mouseX <= centerX + playingUICons.PAUSE_MENU_BUTTON_WIDTH &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.PAUSE_MENU_BUTTON_HEIGHT;

        // Check resume button (right)
        isResumeButtonHovered = mouseX >= centerX + playingUICons.PAUSE_MENU_BUTTON_WIDTH + playingUICons.PAUSE_MENU_SPACING &&
                mouseX <= centerX + (playingUICons.PAUSE_MENU_BUTTON_WIDTH * 2) + playingUICons.PAUSE_MENU_SPACING &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.PAUSE_MENU_BUTTON_HEIGHT;
    }

    /**
     * Determines which pause menu button was clicked based on the mouse position.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param width The width of the game window.
     * @param height The height of the game window.
     * @return The index of the clicked button (retry, menu, resume) or -1 if no button was clicked.
     */
    public int getPauseMenuButtonClicked(int mouseX, int mouseY, int width, int height)
    {
        if (!isPaused) return -1;

        int centerX = width / 2 - playingUICons.PAUSE_MENU_BUTTON_WIDTH / 2;
        int buttonY = height / 2 - playingUICons.PAUSE_MENU_BUTTON_HEIGHT / 2;

        // Check retry button (left)
        if (mouseX >= centerX - playingUICons.PAUSE_MENU_BUTTON_WIDTH - playingUICons.PAUSE_MENU_SPACING &&
                mouseX <= centerX - playingUICons.PAUSE_MENU_SPACING &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.PAUSE_MENU_BUTTON_HEIGHT)
        {
            return playingUICons.RETRY_BUTTON;
        }

        // Check menu button (center)
        if (mouseX >= centerX &&
                mouseX <= centerX + playingUICons.PAUSE_MENU_BUTTON_WIDTH &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.PAUSE_MENU_BUTTON_HEIGHT)
        {
            return playingUICons.MENU_BUTTON;
        }

        // Check resume button (right)
        if (mouseX >= centerX + playingUICons.PAUSE_MENU_BUTTON_WIDTH + playingUICons.PAUSE_MENU_SPACING &&
                mouseX <= centerX + (playingUICons.PAUSE_MENU_BUTTON_WIDTH * 2) + playingUICons.PAUSE_MENU_SPACING &&
                mouseY >= buttonY &&
                mouseY <= buttonY + playingUICons.PAUSE_MENU_BUTTON_HEIGHT)
        {
            return playingUICons.RESUME_BUTTON;
        }

        return -1;
    }

    /**
     * Checks if the pause button was clicked based on the mouse position.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @return true if the pause button was clicked, false otherwise.
     */
    public boolean isPauseButtonClicked(int mouseX, int mouseY)
    {
        return mouseX >= playingUICons.PAUSE_BUTTON_X &&
                mouseX <= playingUICons.PAUSE_BUTTON_X + playingUICons.PAUSE_BUTTON_SIZE &&
                mouseY >= playingUICons.PAUSE_BUTTON_Y &&
                mouseY <= playingUICons.PAUSE_BUTTON_Y + playingUICons.PAUSE_BUTTON_SIZE;
    }

    /**
     * Toggles the pause state of the game.
     * If the game is currently paused, it unpauses it. If it is unpaused, it pauses the game.
     */
    public void togglePause()
    {
        isPaused = !isPaused;
    }

    /**
     * Returns whether the game is currently paused.
     *
     * @return true if the game is paused, false otherwise.
     */
    public boolean isPaused()
    {
        return isPaused;
    }


    /**
     * Resets the game to its initial state. Used when the game is over or when the player chooses to restart.
     */
    public void resetGame()
    {
        if (gameOver)
        {
            playerLives = playingUICons.TOTAL_HEARTS;
            computerLives = playingUICons.TOTAL_HEARTS;
            gameOver = false;
        }
        hasPlayerMadeChoice = false;
        resultText = "";
        currentHand = playingUICons.ROCK;
        computerHand = playingUICons.ROCK;
    }

    /**
     * Resets the game state and ensures that all game variables are set for a new game.
     */
    public void playAgain()
    {
        // Reset all game state variables
        playerLives = playingUICons.TOTAL_HEARTS;
        computerLives = playingUICons.TOTAL_HEARTS;
        gameOver = false;
        hasPlayerMadeChoice = false;
        resultText = "";
        currentHand = playingUICons.ROCK;
        computerHand = playingUICons.ROCK;
        isPaused = false;  // Make sure to unpause when retrying
    }

    /**
     * Handles cleanup when returning to the menu. Resets all game variables and ensures the game is unpaused.
     */
    public void cleanupForMenu()
    {
        playAgain();  // Reset all game state
        isPaused = false;  // Ensure game is unpaused
    }
    public boolean isGameOver()
    {
        return gameOver;
    }


}